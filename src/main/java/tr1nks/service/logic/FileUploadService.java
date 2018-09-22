package tr1nks.service.logic;

import org.springframework.web.multipart.MultipartFile;
import tr1nks.model.parse.UploadFileModel;

public interface FileUploadService {
    UploadFileModel uploadFile(MultipartFile file);
}
