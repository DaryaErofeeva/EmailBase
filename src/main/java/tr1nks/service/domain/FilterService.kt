package tr1nks.service.domain

import tr1nks.domain.dto.FacultyDTO
import tr1nks.domain.dto.GroupDTO

interface FilterService {

    fun getAllGroups(): List<GroupDTO>

    fun getAllFaculties(): List<FacultyDTO>

    fun getAllYears(): Set<Int>
}
