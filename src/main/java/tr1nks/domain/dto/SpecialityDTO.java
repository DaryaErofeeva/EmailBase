package tr1nks.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SpecialityDTO extends MyDTO {
    private static final String NAME_STUB = "SPECIALITY_NAME_STUB";
    private int specialityId;
    private String name;

    @JsonCreator
    public SpecialityDTO(@JsonProperty("id") long id, @JsonProperty("specialityId") int specialityId,
                         @JsonProperty("name") String name) {
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
