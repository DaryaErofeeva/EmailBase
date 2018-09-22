package tr1nks.domain.dto;

public abstract class MyDTO {
    private long id;

    public MyDTO(long id) {
        this.id = id;
    }

    public MyDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
