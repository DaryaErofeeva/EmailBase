package tr1nks.controller.parse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tr1nks.controller.person.PersonController;
import tr1nks.domain.dto.StudentDTO;
import tr1nks.service.logic.ParseService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

import static tr1nks.controller.parse.ParseController.PARSE_URL;

@Controller
@RequestMapping(PARSE_URL)
public class FileParseController implements ParseController {

    @Resource
    private ParseService parseService;

    @GetMapping(PARSE_ERROR)
    public String get() {
        return ERROR_VIEW_NAME;
    }

    @ResponseBody
    @GetMapping("parse/error")
    public List<StudentDTO> error(HttpSession httpSession) {
        return (List<StudentDTO>) httpSession.getAttribute(PersonController.ERROR_STUDENT_SESSION_NAME);
    }
}
