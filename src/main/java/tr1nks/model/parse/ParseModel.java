package tr1nks.model.parse;


import tr1nks.enums.CodePage;
import tr1nks.enums.Delimiter;
import tr1nks.enums.ParseFileMask;
import tr1nks.enums.ParsePerson;
import tr1nks.model.PageModel;

import java.util.ArrayList;
import java.util.List;

public class ParseModel implements PageModel {
    private String fileName;
    private String dirName;
    private ParsePerson parsePerson;
    private Delimiter delimiter=Delimiter.COMMA;
    private CodePage codePage=CodePage.UTF8;
    private List<String[]> previewRows = new ArrayList<>();
    private ParseFileMask[] mask = new ParseFileMask[]{ParseFileMask.SURNAME,
            ParseFileMask.NAME,
            ParseFileMask.PATRONYMIC,
            ParseFileMask.CODE,
            ParseFileMask.GROUP_OR_CATHEDRA,
            ParseFileMask.BUDGET_OR_RATE};

    public ParseModel() {
    }

    public ParseModel(UploadFileModel uploadModel) {
        this.fileName = uploadModel.getFileName();
        this.dirName = uploadModel.getDirName();
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

    public ParsePerson getParsePerson() {
        return parsePerson;
    }

    public void setParsePerson(ParsePerson parsePerson) {
        this.parsePerson = parsePerson;
    }

    public List<String[]> getPreviewRows() {
        return previewRows;
    }

    public void setPreviewRows(List<String[]> previewRows) {
        this.previewRows = previewRows;
    }

    public Delimiter getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(Delimiter delimiter) {
        this.delimiter = delimiter;
    }

    public CodePage getCodePage() {
        return codePage;
    }

    public void setCodePage(CodePage codePage) {
        this.codePage = codePage;
    }

    public ParseFileMask[] getMask() {
        return mask;
    }

    public void setMask(ParseFileMask[] mask) {
        this.mask = mask;
    }
}
