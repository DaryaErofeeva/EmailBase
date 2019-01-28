package tr1nks.controller.person

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.util.ResourceUtils
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
    @PostMapping(ZIP_PAGE_URL, produces = ["application/zip"])
    fun getArchive(@RequestBody studentsDTO: List<StudentDTO>): ByteArray? = studentService.getArchive(studentsDTO)

    @ResponseBody
    @PostMapping(EMAIL_PAGE_URL, produces = ["text/csv"])
    fun getEmailCSV(@RequestBody studentsDTO: List<StudentDTO>): ByteArray? = studentService.getEmailCsv(studentsDTO)

    @ResponseBody
    @PostMapping(OFFICE_PAGE_URL, produces = ["text/csv"])
    fun getOfficeCSV(@RequestBody studentsDTO: List<StudentDTO>): ByteArray? = studentService.getOfficeCsv(studentsDTO)

    @ResponseBody
    @PostMapping(IMAGINE_PAGE_URL, produces = ["text/csv"])
    fun getImagineCSV(@RequestBody studentsDTO: List<StudentDTO>): ByteArray? = studentService.getImagineCsv(studentsDTO)

    @ResponseBody
    @GetMapping(TEMPLATE_PAGE_URL, produces = ["text/csv"])
    fun getTemplate(): ByteArray? = ResourceUtils.getFile("classpath:samples/StudentSample.csv").readBytes()
}
