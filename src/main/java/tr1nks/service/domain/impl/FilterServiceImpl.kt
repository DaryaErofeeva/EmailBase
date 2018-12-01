package tr1nks.service.domain.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tr1nks.component.converter.impl.FacultyEntityDTOConverter
import tr1nks.component.converter.impl.GroupEntityDTOConverter
import tr1nks.domain.dto.FacultyDTO
import tr1nks.domain.repository.FacultyRepository
import tr1nks.domain.repository.GroupRepository
import tr1nks.domain.repository.StudentRepository
import tr1nks.service.domain.FilterService

@Service
class FilterServiceImpl @Autowired constructor(
        private val facultyRepository: FacultyRepository,
        private val groupRepository: GroupRepository,
        private val facultyEntityDTOConverter: FacultyEntityDTOConverter,
        private val groupEntityDTOConverter: GroupEntityDTOConverter
) : FilterService {

    override fun getAllFaculties() = facultyRepository.findAll().map { facultyEntityDTOConverter.toDTO(it) }

    override fun getAllGroups() = groupRepository.findAll().map { groupEntityDTOConverter.toDTO(it) }

    override fun getAllYears() = groupRepository.findAll().map { it.year }.toSet()
}
