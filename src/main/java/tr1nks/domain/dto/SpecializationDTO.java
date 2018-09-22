package tr1nks.domain.dto;

public class SpecializationDTO extends MyDTO {
    public static final String NAME_STUB = "SPECIALIZATION_NAME_STUB";
    private int specializationId;
    private String name;
    private SpecialityDTO specialityDTO;

    public SpecializationDTO(long id, int specializationId, String name, SpecialityDTO specialityDTO) {
        super(id);
        this.specializationId = specializationId;
        this.name = name;
        this.specialityDTO = specialityDTO;
    }
    public SpecializationDTO(int specializationId, String name, SpecialityDTO specialityDTO) {
        this.specializationId = specializationId;
        this.name = name;
        this.specialityDTO = specialityDTO;
    }
    public SpecializationDTO(int specializationId, SpecialityDTO specialityDTO) {
    this(specializationId,NAME_STUB, specialityDTO);
    }

    public SpecializationDTO() {
    }

    public int getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(int specializationId) {
        this.specializationId = specializationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpecialityDTO getSpecialityDTO() {
        return specialityDTO;
    }

    public void setSpecialityDTO(SpecialityDTO specialityDTO) {
        this.specialityDTO = specialityDTO;
    }
}
