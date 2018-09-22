package tr1nks.service.logic;


import tr1nks.domain.entity.PersonEntity;

import java.util.List;

public interface FileGenerationService {
    String PDF_RESOURCE_LOCATION = "/static/pdf/";

    byte[] createPDFArchiveBytes(List<PersonEntity> persons);

    byte[] createPdfBytes(PersonEntity person);
}
