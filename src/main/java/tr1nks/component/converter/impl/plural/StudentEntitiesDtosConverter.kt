package tr1nks.component.converter.impl.plural

import org.springframework.stereotype.Component
import tr1nks.component.converter.EntityDTOConverter
import tr1nks.component.converter.impl.StudentEntityDTOConverter
import tr1nks.domain.dto.StudentDTO
import tr1nks.domain.entity.StudentEntity
import javax.annotation.Resource

@Component
class StudentEntitiesDtosConverter(
        @Resource private val studentEntityDTOConverter: StudentEntityDTOConverter
) : EntityDTOConverter<List<StudentDTO>, List<StudentEntity>> {

    override fun toDTO(entities: List<StudentEntity>) = entities.map { studentEntityDTOConverter.toDTO(it) }

    override fun toEntity(dtos: List<StudentDTO>) = dtos.map { studentEntityDTOConverter.toEntity(it) }
}