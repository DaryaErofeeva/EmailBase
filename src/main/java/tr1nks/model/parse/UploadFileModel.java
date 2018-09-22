package tr1nks.model.parse;

import tr1nks.model.PageModel;

public class UploadFileModel implements PageModel {
    private String fileName;
    private String dirName;

    public UploadFileModel(String filename, String dirName) {
        this.fileName = filename;
        this.dirName = dirName;
    }

    public UploadFileModel() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }
}
