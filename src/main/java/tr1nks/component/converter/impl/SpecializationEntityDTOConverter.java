package tr1nks.component.converter.impl;

import org.springframework.stereotype.Component;
import tr1nks.component.converter.EntityDTOConverter;
import tr1nks.domain.dto.SpecialityDTO;
import tr1nks.domain.dto.SpecializationDTO;
import tr1nks.domain.entity.SpecialityEntity;
import tr1nks.domain.entity.SpecializationEntity;

import javax.annotation.Resource;

@Component
public class SpecializationEntityDTOConverter implements EntityDTOConverter<SpecializationDTO, SpecializationEntity> {
    @Resource
    private EntityDTOConverter<SpecialityDTO, SpecialityEntity> specialityEntityDTOConverter;

    @Override
    public SpecializationDTO toDTO(SpecializationEntity entity) {
        return new SpecializationDTO(entity.getId(), entity.getSpecializationId(), entity.getName(),
                specialityEntityDTOConverter.toDTO(entity.getSpecialityEntity()));
    }

    @Override
    public SpecializationEntity toEntity(SpecializationDTO dto) {
        return new SpecializationEntity(dto.getSpecializationId(), dto.getName(),
                specialityEntityDTOConverter.toEntity(dto.getSpecialityDTO()));
    }
}
