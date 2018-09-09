package tr1nks.domain.entity.enums;

public enum SiteRolesEnum {
    ADMIN,
    USER;

    public String getRoleWithPrefix() {
        return "ROLE_" + this.name();
    }

    public String getRole() {
        return this.name();
    }
}
