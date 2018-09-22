package tr1nks.controller.person;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tr1nks.model.person.student.StudentPageModel;
import tr1nks.service.domain.StudentService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static tr1nks.controller.person.PersonController.STUDENT_PAGE_URL;

@Controller
@RequestMapping({STUDENT_PAGE_URL})
public class StudentController implements PersonController {
    @Resource
    private StudentService studentService;

    @GetMapping
    public String get(Model model, HttpSession session) {
        model.addAttribute(STUDENT_PAGE_MODEL_NAME, studentService.getPageModel(session));
        return STUDENT_VIEW_NAME;
    }


    @PostMapping("json")
    @ResponseBody
    public StudentPageModel jsonTest(HttpSession session) {
        //todo process currentPage
        return studentService.getPageModel(1,session);//todo page num
    }
}
