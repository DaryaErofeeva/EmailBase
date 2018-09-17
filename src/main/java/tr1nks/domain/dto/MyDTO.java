package tr1nks.domain.dto;

import tr1nks.domain.entity.MyEntity;

public abstract class MyDTO {
    private long id;

    public MyDTO(MyEntity entity) {
        this.id = entity.getId();
    }

    public MyDTO() {
    }

    public abstract MyEntity toEntity();

    void fillEntity(MyEntity entity) {
        entity.setId(this.id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
