package tr1nks.component.converter;

public interface EntityDTOConverter<D, E> {

    D toDTO(E entity);

    E toEntity(D dto);
}
