package tr1nks.component.converter.impl;

import org.springframework.stereotype.Component;
import tr1nks.component.converter.EntityDTOConverter;
import tr1nks.domain.dto.GroupDTO;
import tr1nks.domain.dto.StudentDTO;
import tr1nks.domain.entity.GroupEntity;
import tr1nks.domain.entity.StudentEntity;

import javax.annotation.Resource;

@Component
public class StudentEntityDTOConverter implements EntityDTOConverter<StudentDTO, StudentEntity> {
    @Resource
    private EntityDTOConverter<GroupDTO, GroupEntity> groupEntityDTOConverter;

    @Override
    public StudentDTO toDTO(StudentEntity entity) {
        return new StudentDTO(entity.getId(),
                entity.getSurname(),
                entity.getName(),
                entity.getPatronymic(),
                entity.getCode(),
                entity.getLogin(),
                entity.getInitPassword(),
                entity.isImagine(),
                entity.isOffice(),
                groupEntityDTOConverter.toDTO(entity.getGroupEntity()),
                entity.isBudget());
    }

    @Override
    public StudentEntity toEntity(StudentDTO dto) {
        return new StudentEntity(
                dto.getSurname(),
                dto.getName(),
                dto.getPatronymic(),
                dto.getCode(),
                groupEntityDTOConverter.toEntity(dto.getGroupDTO()),
                dto.getLogin(),
                dto.getInitPassword(),
                dto.isImagine(),
                dto.isOffice(),
                dto.isBudget());
    }
}
