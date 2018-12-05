package tr1nks.service.logic;


import tr1nks.domain.entity.StudentEntity;

import java.util.List;

public interface FileGenerationService {
    String PDF_RESOURCE_LOCATION = "/static/pdf/";

    byte[] createPDFArchiveBytes(List<StudentEntity> studentEntities);

    byte[] createPdfBytes(StudentEntity studentEntity);
}
