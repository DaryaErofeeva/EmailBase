package tr1nks.domain.dto;

public class FacultyDTO extends MyDTO {
    public static final String NAME_STUB = "FACULTY_LEVEL_NAME_STUB";
    public static final String ABBR_STUB = "STUB";
    private int facultyId;
    private String name;
    private String abbr;

    public FacultyDTO(long id, int facultyId, String name, String abbr) {
        super(id);
        this.facultyId = facultyId;
        this.name = name;
        this.abbr = abbr;
    }

    public FacultyDTO(int facultyId, String name, String abbr) {
        this.facultyId = facultyId;
        this.name = name;
        this.abbr = abbr;
    }

    public FacultyDTO(int facultyId) {
        this(facultyId, NAME_STUB, ABBR_STUB + facultyId);
    }

    public FacultyDTO() {
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
}
