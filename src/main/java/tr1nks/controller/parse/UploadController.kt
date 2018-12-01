package tr1nks.controller.parse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.ModelAndView
import tr1nks.controller.parse.ParseController.ERROR_VIEW_NAME
import tr1nks.controller.parse.ParseController.UPLOAD_URL
import tr1nks.controller.person.PersonController.STUDENT_SESSION_NAME
import tr1nks.controller.person.PersonController.STUDENT_VIEW_NAME
import tr1nks.domain.dto.StudentDTO
import tr1nks.service.domain.StudentService
import tr1nks.service.logic.ParseService
import java.nio.charset.Charset
import javax.annotation.Resource
import javax.servlet.http.HttpSession

@Controller
@RequestMapping(UPLOAD_URL)
class UploadController @Autowired constructor(
        @Resource private val parseService: ParseService,
        @Resource private val studentService: StudentService
) : ParseController {

    @GetMapping
    fun get() = ParseController.UPLOAD_VIEW_NAME

    @ResponseBody
    @PostMapping("file")
    fun postFile(@RequestParam("delimiter") delimiter: Char, @RequestParam("charset") charset: Charset,
                 @RequestParam("file") file: MultipartFile, httpSession: HttpSession) =
            if (parseService.parseStudents(delimiter, charset, file, httpSession)) {
                studentService.save(httpSession.getAttribute(STUDENT_SESSION_NAME) as List<StudentDTO>, httpSession)
                ModelAndView("redirect:/$STUDENT_VIEW_NAME")
            } else ModelAndView("redirect:/$ERROR_VIEW_NAME")
}
