package tr1nks.component.converter.impl;

import org.springframework.stereotype.Component;
import tr1nks.component.converter.EntityDTOConverter;
import tr1nks.domain.dto.SpecialityDTO;
import tr1nks.domain.entity.SpecialityEntity;

@Component
public class SpecialityEntityDTOConverter implements EntityDTOConverter<SpecialityDTO, SpecialityEntity> {

    @Override
    public SpecialityDTO toDTO(SpecialityEntity entity) {
        return new SpecialityDTO(entity.getId(), entity.getSpecialityId(), entity.getName());
    }

    @Override
    public SpecialityEntity toEntity(SpecialityDTO dto) {
        return new SpecialityEntity(dto.getId(), dto.getSpecialityId(), dto.getName());
    }
}
