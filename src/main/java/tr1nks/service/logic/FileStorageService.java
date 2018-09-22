package tr1nks.service.logic;

import java.io.File;

public interface FileStorageService {
    File createFile(String filename);

    File findFileByName(String filename);

    File findFileByNameAndDir(String filename, String dirName);
}
