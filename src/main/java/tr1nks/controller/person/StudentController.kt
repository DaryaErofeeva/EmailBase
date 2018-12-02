package tr1nks.controller.person

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import tr1nks.controller.person.PersonController.STUDENT_PAGE_URL
import tr1nks.controller.person.PersonController.STUDENT_VIEW_NAME
import tr1nks.domain.dto.StudentDTO
import tr1nks.service.domain.StudentService
import javax.annotation.Resource

@Controller
class StudentController @Autowired constructor(
        private val studentService: StudentService
) : PersonController {

    @GetMapping(STUDENT_VIEW_NAME)
    fun get() = STUDENT_VIEW_NAME

    @ResponseBody
    @GetMapping(STUDENT_PAGE_URL)
    fun getStudents() = studentService.getAllStudents()

    @PostMapping(STUDENT_PAGE_URL)
    fun updateStudents(studentsDTO: List<StudentDTO>) = studentService.updateStudents(studentsDTO)
}
