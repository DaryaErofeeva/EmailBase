package tr1nks.controller.parse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import tr1nks.controller.parse.ParseController.ERROR_VIEW_NAME
import tr1nks.controller.parse.ParseController.PARSE_URL
import tr1nks.controller.person.PersonController
import tr1nks.controller.person.PersonController.STUDENT_SESSION_NAME
import tr1nks.controller.person.PersonController.STUDENT_VIEW_NAME
import tr1nks.domain.dto.StudentDTO
import tr1nks.service.domain.StudentService
import tr1nks.service.logic.ParseService
import javax.servlet.http.HttpSession

@Controller
@RequestMapping(PARSE_URL)
class FileParseController @Autowired constructor(
        private val parseService: ParseService,
        private val studentService: StudentService
) : ParseController {

    @GetMapping(ParseController.PARSE_ERROR)
    fun get() = ParseController.ERROR_VIEW_NAME

    @ResponseBody
    @GetMapping(value = ["parse/error"])
    fun error(httpSession: HttpSession) = httpSession.getAttribute(PersonController.ERROR_STUDENT_SESSION_NAME)
            ?: listOf<StudentDTO>()

    @ResponseBody
    @PostMapping(value = ["parse/error"])
    fun postCorrectedData(httpSession: HttpSession, @RequestBody studentDTOS: List<StudentDTO>) =
            if (parseService.checkStudents(studentDTOS, httpSession)) {
                studentService.save(httpSession.getAttribute(STUDENT_SESSION_NAME) as List<StudentDTO>, httpSession)
                ModelAndView("redirect:/$STUDENT_VIEW_NAME")
            } else ModelAndView("redirect:/$ERROR_VIEW_NAME")
}
