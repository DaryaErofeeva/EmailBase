package tr1nks.service.logic.impl;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import tr1nks.domain.entity.StudentEntity;
import tr1nks.service.domain.DomainsService;
import tr1nks.service.logic.FileGenerationService;
import tr1nks.service.logic.PdfFromHtmlCreationService;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileGenerationServiceImpl implements FileGenerationService {
    private static final Pattern PDQ_EMAIL_ADDRESS_PATTERN = Pattern.compile("@@EMAIL-ADDRESS");
    private static final Pattern PDF_EMAIL_PASSWORD_PATTERN = Pattern.compile("@@EMAIL-PASSWORD");
    private static final String SLH = "/";
    private static final String EMAIL_CSV_TAIL = ",,,,,,,,,,,,";
    private static final String OFFICE_CSV_TAIL = ",,,,,,,,,,";
    private static final Logger logger = Logger.getLogger(FileGenerationServiceImpl.class);
    private static PdfFromHtmlCreationService.HtmlCssForPdfData pdfDataFull;
    private static PdfFromHtmlCreationService.HtmlCssForPdfData pdfDataImagine;
    private static PdfFromHtmlCreationService.HtmlCssForPdfData pdfDataOffice;
    private static PdfFromHtmlCreationService.HtmlCssForPdfData pdfDataEmailOnly;
    @Resource
    private DomainsService domainsService;
    @Resource
    private PdfFromHtmlCreationService pdfFromHtmlCreationService;

    @Override
    public byte[] createPDFArchiveBytes(List<StudentEntity> studentEntities) {
        byte[] arr = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            StringBuilder builder = new StringBuilder();
            for (StudentEntity student : studentEntities) {
                builder.append((student).getGroupEntity().getFacultyEntity().getAbbr())
                        .append(SLH).append((student).getGroupEntity().getCipher().replace(".", "_"))
                        .append(SLH);
                builder.append(student.getSurname()).append("_").append(student.getName()).append(".pdf");
                zipOutputStream.putNextEntry(new ZipEntry(builder.toString()));
                zipOutputStream.write(createPdfBytes(student));
                zipOutputStream.closeEntry();
                builder.replace(0, builder.length(), "");
            }
            writeCsvToArchive(studentEntities, zipOutputStream);
            zipOutputStream.flush();
            zipOutputStream.close();
            arr = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return arr;
    }

    private void writeCsvToArchive(List<StudentEntity> studentEntities, ZipOutputStream zipOutputStream) {
        HashMap<String, StringBuilder> map = new HashMap<>();
        for (StudentEntity student : studentEntities) {
            String name = student.getGroupEntity().getFacultyEntity().getAbbr() + SLH + student.getGroupEntity()
                    .getCipher().replace(".", "_") + ".csv";
            map.put(name, new StringBuilder(createEmailCsvString(student)));
        }
        for (String name : map.keySet()) {
            try {
                zipOutputStream.putNextEntry(new ZipEntry(name));
                zipOutputStream.write(map.get(name).toString().getBytes("cp1251"));
                zipOutputStream.closeEntry();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private String createEmailCsvString(StudentEntity studentEntity) {
        return studentEntity.getName() + ',' + studentEntity.getSurname() + ',' + studentEntity.getLogin() + '@' +
                domainsService.getEmailDomain() + ',' + studentEntity.getInitPassword() + EMAIL_CSV_TAIL + "\n";
    }

    @Override
    public byte[] createPdfBytes(StudentEntity studentEntity) {
        HashMap<Pattern, String> replaceMap = new HashMap<>();
        replaceMap.put(PDQ_EMAIL_ADDRESS_PATTERN, studentEntity.getLogin() + '@' + domainsService.getEmailDomain());
        replaceMap.put(PDF_EMAIL_PASSWORD_PATTERN, studentEntity.getInitPassword());
        if (studentEntity.isImagine() && studentEntity.isOffice()) {
            if (null == pdfDataFull) {
                pdfDataFull = pdfFromHtmlCreationService.loadHtmlCssData("pdfSample_Full.html");
            }
            return pdfFromHtmlCreationService.create(pdfDataFull, replaceMap);
        } else {
            if (studentEntity.isImagine()) {
                if (null == pdfDataImagine) {
                    pdfDataImagine = pdfFromHtmlCreationService.loadHtmlCssData("pdfSample_Imagine.html");
                }
                return pdfFromHtmlCreationService.create(pdfDataImagine, replaceMap);
            } else if (studentEntity.isOffice()) {
                if (null == pdfDataOffice) {
                    pdfDataOffice = pdfFromHtmlCreationService.loadHtmlCssData("pdfSample_Office.html");
                }
                return pdfFromHtmlCreationService.create(pdfDataOffice, replaceMap);
            } else {
                if (null == pdfDataEmailOnly) {
                    pdfDataEmailOnly = pdfFromHtmlCreationService.loadHtmlCssData("pdfSample_EmailOnly.html");
                }
                return pdfFromHtmlCreationService.create(pdfDataEmailOnly, replaceMap);
            }
        }
    }

    public byte[][] createFullPersonsCsvs(List<StudentEntity> studentEntities) {
        byte[][] arr = new byte[3][];
        StringBuilder builderEmail = new StringBuilder();
        StringBuilder builderImagine = new StringBuilder();
        StringBuilder builderOffice = new StringBuilder();
        for (StudentEntity student : studentEntities) {
            builderEmail.append(createEmailCsvString(student));
            if (student.isImagine()) {
                builderImagine.append(createImagineCsvString(student));
            }
            if (student.isOffice()) {
                builderOffice.append(createOfficeCsvString(student));
            }
        }
        try {
            arr[0] = builderEmail.toString().getBytes("cp1251");
            if (builderImagine.length() > 0) {
                arr[1] = builderImagine.toString().getBytes();
            }
            if (builderOffice.length() > 0) {
                arr[2] = builderOffice.toString().getBytes();
            }
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return arr;
    }

    private String createImagineCsvString(StudentEntity studentEntity) {
        return studentEntity.getLogin() + '@' + domainsService.getImagineDomain() + "\n";
    }

    private String createOfficeCsvString(StudentEntity studentEntity) {
        return studentEntity.getLogin() + '@' + domainsService.getImagineDomain() + ',' + studentEntity.getName() + ',' +
                studentEntity.getSurname() + ',' + studentEntity.getName() + ',' + studentEntity.getSurname() +
                OFFICE_CSV_TAIL + "\n";
    }
}
