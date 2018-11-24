package tr1nks.controller.parse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tr1nks.component.converter.impl.plural.StudentEntitiesDtosConverter;
import tr1nks.controller.person.PersonController;
import tr1nks.domain.dto.StudentDTO;
import tr1nks.service.domain.StudentService;
import tr1nks.service.logic.ParseService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static tr1nks.controller.parse.ParseController.PARSE_URL;
import static tr1nks.controller.person.PersonController.ERROR_STUDENT_SESSION_NAME;
import static tr1nks.controller.person.PersonController.STUDENT_SESSION_NAME;
import static tr1nks.controller.person.PersonController.STUDENT_VIEW_NAME;

@Controller
@RequestMapping(PARSE_URL)
public class FileParseController implements ParseController {

    @Resource
    private ParseService parseService;

    @Resource
    private StudentService studentService;

    @Resource
    private StudentEntitiesDtosConverter studentEntitiesDtosConverter;

    @GetMapping(PARSE_ERROR)
    public String get() {
        return ERROR_VIEW_NAME;
    }

    @ResponseBody
    @GetMapping("parse/error")
    public List<StudentDTO> error(HttpSession httpSession) {
        return (List<StudentDTO>) httpSession.getAttribute(PersonController.ERROR_STUDENT_SESSION_NAME);
    }

    @ResponseBody
    @PostMapping("parse/error")
    public ModelAndView postCorrectedData(HttpSession httpSession, @RequestBody List<StudentDTO> studentDTOS) {
        if (parseService.checkStudents(studentDTOS, httpSession)) {
            studentService.save(studentEntitiesDtosConverter
                    .toEntity((List<StudentDTO>) httpSession.getAttribute(STUDENT_SESSION_NAME)));
            httpSession.removeAttribute(STUDENT_SESSION_NAME);
            httpSession.removeAttribute(ERROR_STUDENT_SESSION_NAME);
            return new ModelAndView("redirect:/" + STUDENT_VIEW_NAME);
        } else return new ModelAndView("redirect:/" + ERROR_VIEW_NAME);
    }
}
