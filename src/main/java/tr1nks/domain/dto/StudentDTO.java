package tr1nks.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import tr1nks.constants.StudentField;

public class StudentDTO extends PersonDTO {
    private GroupDTO groupDTO;
    private boolean budget;

    public StudentDTO(long id, String surname, String name, String patronymic, String code, String login,
                      String initPassword, boolean imagine, boolean office, StudentField errorField,
                      String errorMessage, GroupDTO groupDTO, boolean budget) {

        super(id, surname, name, patronymic, code, login, initPassword,
                imagine, office, errorField, errorMessage);

        this.groupDTO = groupDTO;
        this.budget = budget;
    }

    @JsonCreator
    public StudentDTO(@JsonProperty("id") long id, @JsonProperty("surname") String surname, @JsonProperty("name") String name,
                      @JsonProperty("patronymic") String patronymic, @JsonProperty("code") String code,
                      @JsonProperty("login") String login, @JsonProperty("initPassword") String initPassword,
                      @JsonProperty("imagine") boolean imagine, @JsonProperty("office") boolean office,
                      @JsonProperty("errorField") String errorField, @JsonProperty("errorMessage") String errorMessage,
                      @JsonProperty("groupDTO") GroupDTO groupDTO, @JsonProperty("budget") boolean budget) {

        super(id, surname, name, patronymic, code, login, initPassword,
                imagine, office, errorField, errorMessage);

        this.groupDTO = groupDTO;
        this.budget = budget;
    }

    public StudentDTO(String surname, String name, String patronymic, String code, String login,
                      String initPassword, boolean imagine, boolean office, StudentField errorField,
                      String errorMessage, GroupDTO groupDTO, boolean budget) {

        super(surname, name, patronymic, code, login, initPassword,
                imagine, office, errorField, errorMessage);

        this.groupDTO = groupDTO;
        this.budget = budget;
    }

    public StudentDTO(String surname, String name, String patronymic, String code, String login,
                      String initPassword, GroupDTO groupDTO, boolean budget) {

        super(surname, name, patronymic, code, login, initPassword,
                false, false);

        this.groupDTO = groupDTO;
        this.budget = budget;
    }
    public StudentDTO(long id,String surname, String name, String patronymic, String code, String login,
                      String initPassword, boolean imagine, boolean office,GroupDTO groupDTO, boolean budget) {

        super(id, surname, name, patronymic, code, login, initPassword,
                imagine, office);

        this.groupDTO = groupDTO;
        this.budget = budget;
    }

    public StudentDTO() {
    }

    public GroupDTO getGroupDTO() {
        return groupDTO;
    }

    public void setGroupDTO(GroupDTO groupDTO) {
        this.groupDTO = groupDTO;
    }

    public boolean isBudget() {
        return budget;
    }

    public void setBudget(boolean budget) {
        this.budget = budget;
    }
}
