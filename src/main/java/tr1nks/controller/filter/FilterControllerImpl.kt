package tr1nks.controller.filter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tr1nks.controller.filter.FilterController.Companion.URL_BASE
import tr1nks.service.domain.FilterService

@RestController
@RequestMapping(URL_BASE)
class FilterControllerImpl @Autowired constructor(
        private val filterService: FilterService
) : FilterController() {

    @GetMapping("faculty")
    fun getAllFaculties() = filterService.getAllFaculties()

    @GetMapping("group")
    fun getAllGroups() = filterService.getAllGroups()

    @GetMapping("year")
    fun getAllYears() = filterService.getAllYears()
}