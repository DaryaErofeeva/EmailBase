package tr1nks.service.logic.impl;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tr1nks.model.parse.UploadFileModel;
import tr1nks.service.logic.FileStorageService;
import tr1nks.service.logic.FileUploadService;

import javax.annotation.Resource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    private static final Logger logger = Logger.getLogger(FileUploadServiceImpl.class);
    @Resource
    private FileStorageService fileStorageService;

    @Override
    public UploadFileModel uploadFile(MultipartFile file) {
        File storePoint = fileStorageService.createFile(file.getOriginalFilename());
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(storePoint))) {
            stream.write(file.getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return new UploadFileModel(storePoint.getName(),storePoint.getParentFile().getName());
    }
}
