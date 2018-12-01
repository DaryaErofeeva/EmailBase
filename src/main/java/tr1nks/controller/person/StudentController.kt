package tr1nks.controller.person

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import tr1nks.controller.person.PersonController.STUDENT_PAGE_URL
import tr1nks.controller.person.PersonController.STUDENT_VIEW_NAME
import tr1nks.service.domain.StudentService
import javax.annotation.Resource

@Controller
class StudentController(
        @Resource private val studentService: StudentService
) : PersonController {

    @GetMapping(STUDENT_VIEW_NAME)
    fun get() = STUDENT_VIEW_NAME

    @GetMapping(STUDENT_PAGE_URL)
    fun getStudents() = studentService.getAll()
}
