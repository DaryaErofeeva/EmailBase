package tr1nks.component.converter.impl;

import org.springframework.stereotype.Component;
import tr1nks.component.converter.EntityDTOConverter;
import tr1nks.domain.dto.FacultyDTO;
import tr1nks.domain.dto.GroupDTO;
import tr1nks.domain.dto.SpecializationDTO;
import tr1nks.domain.dto.StudyLevelDTO;
import tr1nks.domain.entity.FacultyEntity;
import tr1nks.domain.entity.GroupEntity;
import tr1nks.domain.entity.SpecializationEntity;
import tr1nks.domain.entity.StudyLevelEntity;

import javax.annotation.Resource;

@Component
public class GroupEntityDTOConverter implements EntityDTOConverter<GroupDTO, GroupEntity> {
    @Resource
    private EntityDTOConverter<StudyLevelDTO, StudyLevelEntity> studyLevelEntityDTOConverter;
    @Resource
    private EntityDTOConverter<FacultyDTO, FacultyEntity> facultyEntityDTOConverter;
    @Resource
    private EntityDTOConverter<SpecializationDTO, SpecializationEntity> specializationEntityDTOConverter;

    @Override
    public GroupDTO toDTO(GroupEntity entity) {
        return new GroupDTO(entity.getId(),
                studyLevelEntityDTOConverter.toDTO(entity.getStudyLevelEntity()),
                facultyEntityDTOConverter.toDTO(entity.getFacultyEntity()),
                specializationEntityDTOConverter.toDTO(entity.getSpecializationEntity()),
                entity.getYear(), entity.getNum());
    }

    @Override
    public GroupEntity toEntity(GroupDTO dto) {
        return new GroupEntity(
                studyLevelEntityDTOConverter.toEntity(dto.getStudyLevelDTO()),
                facultyEntityDTOConverter.toEntity(dto.getFacultyDTO()),
                specializationEntityDTOConverter.toEntity(dto.getSpecializationDTO()),
                dto.getYear(), dto.getNum());
    }
}
