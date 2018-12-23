package tr1nks.emailtoout

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tr1nks.emailtoout.EmailToOutController.Companion.URL_BASE
import tr1nks.service.domain.StudentService

@RestController
@RequestMapping(URL_BASE)
class EmailToOutControllerImpl @Autowired constructor(
        private val studentService: StudentService
) : EmailToOutController() {

    @GetMapping
    fun getStudents(
            @RequestParam(name = "name") name: String,
            @RequestParam(name = "surname") surname: String,
            @RequestParam(name = "group") group: String
    ) = studentService.getStudents(name, surname, group)
}