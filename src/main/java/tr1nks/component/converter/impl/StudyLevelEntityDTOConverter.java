package tr1nks.component.converter.impl;

import org.springframework.stereotype.Component;
import tr1nks.component.converter.EntityDTOConverter;
import tr1nks.domain.dto.StudyLevelDTO;
import tr1nks.domain.entity.StudyLevelEntity;

@Component
public class StudyLevelEntityDTOConverter implements EntityDTOConverter<StudyLevelDTO, StudyLevelEntity> {
    @Override
    public StudyLevelDTO toDTO(StudyLevelEntity entity) {
        return new StudyLevelDTO(entity.getId(), entity.getLevelId(), entity.getName());
    }

    @Override
    public StudyLevelEntity toEntity(StudyLevelDTO dto) {
        return new StudyLevelEntity(dto.getLevelId(), dto.getName());
    }
}
