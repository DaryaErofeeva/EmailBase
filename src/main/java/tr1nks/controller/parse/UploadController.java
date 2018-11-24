package tr1nks.controller.parse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import tr1nks.component.converter.impl.plural.StudentEntitiesDtosConverter;
import tr1nks.domain.dto.StudentDTO;
import tr1nks.service.domain.StudentService;
import tr1nks.service.logic.ParseService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.List;

import static tr1nks.controller.parse.ParseController.UPLOAD_URL;
import static tr1nks.controller.person.PersonController.ERROR_STUDENT_SESSION_NAME;
import static tr1nks.controller.person.PersonController.STUDENT_SESSION_NAME;
import static tr1nks.controller.person.PersonController.STUDENT_VIEW_NAME;

@Controller
@RequestMapping(UPLOAD_URL)
public class UploadController implements ParseController {

    @Resource
    private ParseService parseService;

    @Resource
    private StudentService studentService;

    @Resource
    private StudentEntitiesDtosConverter studentEntitiesDtosConverter;

    @GetMapping
    public String get() {
        return UPLOAD_VIEW_NAME;
    }

    @ResponseBody
    @PostMapping("file")
    public ModelAndView postFile(@RequestParam("delimiter") char delimiter, @RequestParam("charset") Charset charset,
                                 @RequestParam("file") MultipartFile file, HttpSession httpSession) {
        if (parseService.parseStudents(delimiter, charset, file, httpSession)) {
            studentService.save(studentEntitiesDtosConverter
                    .toEntity((List<StudentDTO>) httpSession.getAttribute(STUDENT_SESSION_NAME)));
            httpSession.removeAttribute(STUDENT_SESSION_NAME);
            httpSession.removeAttribute(ERROR_STUDENT_SESSION_NAME);
            return new ModelAndView("redirect:/" + STUDENT_VIEW_NAME);
        } else return new ModelAndView("redirect:/" + ERROR_VIEW_NAME);
    }
}
