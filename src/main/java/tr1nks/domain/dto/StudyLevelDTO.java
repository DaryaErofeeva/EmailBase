package tr1nks.domain.dto;

public class StudyLevelDTO extends MyDTO {
    public static final String NAME_STUB = "STUDY_LEVEL_NAME_STUB";
    private int levelId;
    private String name;


    public StudyLevelDTO(long id, int levelId, String name) {
        super(id);
        this.levelId = levelId;
        this.name = name;
    }

    public StudyLevelDTO(int levelId, String name) {
        this.levelId = levelId;
        this.name = name;
    }

    public StudyLevelDTO(int levelId) {
        this(levelId, NAME_STUB);
    }

    public StudyLevelDTO() {
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
