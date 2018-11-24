package tr1nks.constants;

public enum UserRole {
    ADMIN,
    USER;

    public String getRoleWithPrefix() {
        return "ROLE_" + this.name();
    }

    public String getRole() {
        return this.name();
    }
}
