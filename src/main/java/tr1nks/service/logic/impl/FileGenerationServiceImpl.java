package tr1nks.service.logic.impl;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import tr1nks.domain.entity.PersonEntity;
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
    public byte[] createPDFArchiveBytes(List<PersonEntity> persons) {
        byte[] arr = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            StringBuilder builder = new StringBuilder();
            for (PersonEntity person : persons) {
                if (person instanceof StudentEntity) {
                    builder.append(((StudentEntity) person).getGroupEntity().getFacultyEntity().getAbbr())
                            .append(SLH).append(((StudentEntity) person).getGroupEntity().getCipher().replace(".", "_"))
                            .append(SLH);
                }
                builder.append(person.getSurname()).append("_").append(person.getName()).append(".pdf");
                zipOutputStream.putNextEntry(new ZipEntry(builder.toString()));
                zipOutputStream.write(createPdfBytes(person));
                zipOutputStream.closeEntry();
                builder.replace(0, builder.length(), "");
            }
            writeCsvToArchive(persons, zipOutputStream);
            zipOutputStream.flush();
            zipOutputStream.close();
            arr = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return arr;
    }

    private void writeCsvToArchive(List<PersonEntity> persons, ZipOutputStream zipOutputStream) {
        HashMap<String, StringBuilder> map = new HashMap<>();
        for (PersonEntity person : persons) {
            String name = null;
            if (person instanceof StudentEntity) {
                StudentEntity s = (StudentEntity) person;
                name = s.getGroupEntity().getFacultyEntity().getAbbr() + SLH + s.getGroupEntity().getCipher().replace(".", "_") + ".csv";
            }//todo
            if (null != name && map.containsKey(name)) {
                map.get(name).append(createEmailCsvString(person));
            } else {
                map.put(name, new StringBuilder(createEmailCsvString(person)));
            }
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

    private String createEmailCsvString(PersonEntity person) {
        return person.getName() + ',' + person.getSurname() + ',' + person.getLogin() + '@' + domainsService.getEmailDomen() + ',' + person.getInitPassword() + EMAIL_CSV_TAIL + "\n";
    }

@Override
    public byte[] createPdfBytes(PersonEntity person) {
        HashMap<Pattern, String> replaceMap = new HashMap<>();
        replaceMap.put(PDQ_EMAIL_ADDRESS_PATTERN, person.getLogin() + '@' + domainsService.getEmailDomen());
        replaceMap.put(PDF_EMAIL_PASSWORD_PATTERN, person.getInitPassword());
        if (person.isImagine() && person.isOffice()) {
            if (null == pdfDataFull) {
                pdfDataFull = pdfFromHtmlCreationService.loadHtmlCssData("pdfSample_Full.html");
            }
            return pdfFromHtmlCreationService.create(pdfDataFull, replaceMap);
        } else {
            if (person.isImagine()) {
                if (null == pdfDataImagine) {
                    pdfDataImagine = pdfFromHtmlCreationService.loadHtmlCssData("pdfSample_Imagine.html");
                }
                return pdfFromHtmlCreationService.create(pdfDataImagine, replaceMap);
            } else if (person.isOffice()) {
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

    public byte[][] createFullPersonsCsvs(List<PersonEntity> persons) {
        byte[][] arr = new byte[3][];
        StringBuilder builderEmail = new StringBuilder();
        StringBuilder builderImagine = new StringBuilder();
        StringBuilder builderOffice = new StringBuilder();
        for (PersonEntity person : persons) {
            builderEmail.append(createEmailCsvString(person));
            if (person.isImagine()) {
                builderImagine.append(createImagineCsvString(person));
            }
            if (person.isOffice()) {
                builderOffice.append(createOfficeCsvString(person));
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

    private String createImagineCsvString(PersonEntity person) {
        return person.getLogin() + '@' + domainsService.getImagineDomen() + "\n";
    }

    private String createOfficeCsvString(PersonEntity person) {
        return person.getLogin() + '@' + domainsService.getImagineDomen() + ',' + person.getName() + ',' + person.getSurname() + ',' + person.getName() + ',' + person.getSurname() + OFFICE_CSV_TAIL + "\n";
    }
}
