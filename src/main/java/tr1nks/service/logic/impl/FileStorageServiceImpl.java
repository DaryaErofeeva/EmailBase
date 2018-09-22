package tr1nks.service.logic.impl;

import org.springframework.stereotype.Service;
import tr1nks.service.logic.FileStorageService;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final char SLH = '/';
    private static final String FILE_STORAGE_NAME = "fileStorage";
    private static final String FILE_STORAGE_PATH = new File("").getAbsolutePath() + SLH + FILE_STORAGE_NAME;
    private static final String FILENAME_FORMAT = "%s_%s";
    private static final DateFormat directoryDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final DateFormat fileDateFormat = new SimpleDateFormat("HH-mm-ssSSS");//todo think about filename

    @Override
    public File createFile(String filename) {
        Date date = new Date();
        String directoryPath = FILE_STORAGE_PATH + SLH + directoryDateFormat.format(new Date()) + SLH;
        createNonexistentDirs(directoryPath);
        String fileName = String.format(FILENAME_FORMAT, fileDateFormat.format(date), filename);
        return new File(directoryPath + fileName);
    }

    @Override
    public File findFileByName(String filename) {
        return findFile(filename, FILE_STORAGE_PATH);
    }

    @Override
    public File findFileByNameAndDir(String filename, String dirName) {
        return findFile(filename, FILE_STORAGE_PATH + SLH + dirName);
    }

    private File findFile(String filename, String findStartPath) {
        File[] files = new File(findStartPath).listFiles((dir, name) -> name.equals(filename));
        return files != null && files.length > 0 ? files[0] : null;
    }

    private void createNonexistentDirs(String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
