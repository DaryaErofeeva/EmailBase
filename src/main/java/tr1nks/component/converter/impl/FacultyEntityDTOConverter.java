package tr1nks.component.converter.impl;

import org.springframework.stereotype.Component;
import tr1nks.component.converter.EntityDTOConverter;
import tr1nks.domain.dto.FacultyDTO;
import tr1nks.domain.entity.FacultyEntity;

@Component
public class FacultyEntityDTOConverter implements EntityDTOConverter<FacultyDTO, FacultyEntity> {
    @Override
    public FacultyDTO toDTO(FacultyEntity entity) {
        return new FacultyDTO(entity.getId(), entity.getFacultyId(), entity.getName(), entity.getAbbr());
    }

    @Override
    public FacultyEntity toEntity(FacultyDTO dto) {
        return new FacultyEntity(dto.getFacultyId(), dto.getName(), dto.getAbbr());
    }
}
