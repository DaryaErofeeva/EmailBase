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
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
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
        HashMap<String, StringBuilder> map = getContentForCSV(studentEntities);
        for (String name : map.keySet()) {
            try {
                zipOutputStream.putNextEntry(new ZipEntry(name));
                zipOutputStream.write(map.get(name).toString().getBytes());
                zipOutputStream.closeEntry();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private HashMap<String, StringBuilder> getContentForCSV(List<StudentEntity> studentEntities) {
        HashMap<String, StringBuilder> map = new HashMap<>();
        for (StudentEntity student : studentEntities) {
            String name = student.getGroupEntity().getFacultyEntity().getAbbr() + SLH + student.getGroupEntity()
                    .getCipher().replace(".", "_") + ".csv";
            map.put(name, new StringBuilder(createEmailCsvStringWithoutPassword(student)));
        }
        return map;
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

    @Override
    public byte[] createEmailsCsv(List<StudentEntity> studentEntities) {
        StringBuilder builderEmail = new StringBuilder();
        studentEntities.forEach(student -> builderEmail.append(createEmailCsvString(student)));
        return createCsv(builderEmail);
    }

    @Override
    public byte[] createImagineCsv(List<StudentEntity> studentEntities) {
        StringBuilder builderImagine = new StringBuilder();
        studentEntities.forEach(student -> {
            if (student.isImagine()) builderImagine.append(createImagineCsvString(student));
        });
        return createCsv(builderImagine);
    }

    @Override
    public byte[] createOfficeCsv(List<StudentEntity> studentEntities) {
        StringBuilder builderOffice = new StringBuilder();
        studentEntities.forEach(student -> {
            if (student.isOffice()) builderOffice.append(createOfficeCsvString(student));
        });
        return createCsv(builderOffice);
    }

    private byte[] createCsv(StringBuilder studentsBuilder) {
        byte[] arr = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            if (studentsBuilder.length() > 0)
                byteArrayOutputStream.write(studentsBuilder.toString().getBytes());
            arr = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arr;
    }

    private String createEmailCsvString(StudentEntity studentEntity) {
        return studentEntity.getName() + ',' + studentEntity.getSurname() + ',' + studentEntity.getLogin() + '@' +
                domainsService.getEmailDomain() + ',' + studentEntity.getInitPassword() + EMAIL_CSV_TAIL + "\n";
    }

    private String createEmailCsvStringWithoutPassword(StudentEntity studentEntity) {
        return studentEntity.getName() + ',' + studentEntity.getSurname() + ',' + studentEntity.getLogin() + '@' +
                domainsService.getEmailDomain() + EMAIL_CSV_TAIL + "\n";
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
