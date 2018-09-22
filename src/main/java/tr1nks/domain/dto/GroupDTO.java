package tr1nks.domain.dto;

public class GroupDTO extends MyDTO {
    private StudyLevelDTO studyLevelDTO;
    private FacultyDTO facultyDTO;
    private SpecializationDTO specializationDTO;
    private int year;
    private int num;

    public GroupDTO(long id, StudyLevelDTO studyLevelDTO, FacultyDTO facultyDTO,
                    SpecializationDTO specializationDTO, int year, int num) {
        super(id);
        this.studyLevelDTO = studyLevelDTO;
        this.facultyDTO = facultyDTO;
        this.specializationDTO = specializationDTO;
        this.year = year;
        this.num = num;
    }
    public GroupDTO(StudyLevelDTO studyLevelDTO, FacultyDTO facultyDTO,
                    SpecializationDTO specializationDTO, int year, int num) {
        this.studyLevelDTO = studyLevelDTO;
        this.facultyDTO = facultyDTO;
        this.specializationDTO = specializationDTO;
        this.year = year;
        this.num = num;
    }

    public GroupDTO() {
    }

    public StudyLevelDTO getStudyLevelDTO() {
        return studyLevelDTO;
    }

    public void setStudyLevelDTO(StudyLevelDTO studyLevelDTO) {
        this.studyLevelDTO = studyLevelDTO;
    }

    public FacultyDTO getFacultyDTO() {
        return facultyDTO;
    }

    public void setFacultyDTO(FacultyDTO facultyDTO) {
        this.facultyDTO = facultyDTO;
    }

    public SpecializationDTO getSpecializationDTO() {
        return specializationDTO;
    }

    public void setSpecializationDTO(SpecializationDTO specializationDTO) {
        this.specializationDTO = specializationDTO;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
