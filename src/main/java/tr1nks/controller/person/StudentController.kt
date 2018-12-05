package tr1nks.controller.person

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import tr1nks.controller.person.PersonController.*
import tr1nks.domain.dto.StudentDTO
import tr1nks.service.domain.StudentService

@Controller
class StudentController @Autowired constructor(
        private val studentService: StudentService
) : PersonController {

    @GetMapping(STUDENT_VIEW_NAME)
    fun get() = STUDENT_VIEW_NAME

    @ResponseBody
    @GetMapping(STUDENT_PAGE_URL)
    fun getStudents(
            @RequestParam(name = "f", required = false) facultyName: String?,
            @RequestParam(name = "g", required = false) group: String?,
            @RequestParam(name = "y", required = false) year: Int?
    ) = studentService.getStudents(facultyName, group, year)

    @ResponseBody
    @PostMapping(STUDENT_PAGE_URL)
    fun updateStudents(@RequestBody studentsDTO: List<StudentDTO>) = studentService.updateStudents(studentsDTO)

    @ResponseBody
    @GetMapping(ZIP_PAGE_URL, produces = ["application/zip"])
    fun getArchive() = studentService.archive
}
