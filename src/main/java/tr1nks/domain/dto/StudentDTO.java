package tr1nks.domain.dto;

public class StudentDTO extends PersonDTO {
    private GroupDTO groupDTO;
    private boolean budget;

    public StudentDTO(long id, String surname, String name, String patronymic, String code, String login,
                      String initPassword, boolean imagine, boolean office, boolean checked, int errorFieldIndex,
                      String errorMessage, GroupDTO groupDTO, boolean budget) {

        super(id, surname, name, patronymic, code, login, initPassword,
                imagine, office, checked, errorFieldIndex, errorMessage);

        this.groupDTO = groupDTO;
        this.budget = budget;
    }

    public StudentDTO(String surname, String name, String patronymic, String code, String login,
                      String initPassword, boolean imagine, boolean office, boolean checked, int errorFieldIndex,
                      String errorMessage, GroupDTO groupDTO, boolean budget) {

        super(surname, name, patronymic, code, login, initPassword,
                imagine, office, checked, errorFieldIndex, errorMessage);

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
