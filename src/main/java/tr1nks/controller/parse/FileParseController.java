package tr1nks.controller.parse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tr1nks.controller.person.PersonController;
import tr1nks.model.parse.ParseModel;
import tr1nks.service.logic.ParseService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static tr1nks.controller.parse.ParseController.PARSE_URL;

@org.springframework.stereotype.Controller
@RequestMapping(PARSE_URL)
public class FileParseController implements ParseController {
    @Resource
    private ParseService parseService;

    @PostMapping(PARSE_TEST)
    public ModelAndView test(@ModelAttribute(PARSE_MODEL_NAME) ParseModel parseModel) {
        parseService.test(parseModel);
        return new ModelAndView(PARSE_URL, UPLOAD_FILE_MODEL_NAME, parseModel);
    }

    @PostMapping(PARSE_PARSE)
    public ModelAndView parse(@ModelAttribute(PARSE_MODEL_NAME) ParseModel parseModel, HttpSession session, Model model) {
//        PageModel model = parseService.parse(parseModel, session);
        return new ModelAndView(PersonController.STUDENT_VIEW_NAME, PersonController.STUDENT_PAGE_MODEL_NAME, model);
    }
}
