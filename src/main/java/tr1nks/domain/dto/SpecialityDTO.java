package tr1nks.domain.dto;

public class SpecialityDTO extends MyDTO {
    private static final String NAME_STUB = "SPECIALITY_NAME_STUB";
    private int specialityId;
    private String name;

    public SpecialityDTO(long id, int specialityId, String name) {
        super(id);
        this.specialityId = specialityId;
        this.name = name;
    }

    public SpecialityDTO(int specialityId, String name) {
        this.specialityId = specialityId;
        this.name = name;
    }
    public SpecialityDTO(int specialityId) {
        this(specialityId ,NAME_STUB);
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
