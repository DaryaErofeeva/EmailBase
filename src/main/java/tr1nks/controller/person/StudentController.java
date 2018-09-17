package tr1nks.controller.person;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tr1nks.domain.entity.StudentEntity;
import tr1nks.model.person.student.StudentPageModel;
import tr1nks.service.StudentService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static tr1nks.controller.person.PersonController.STUDENT_PAGE_URL;

@Controller
@RequestMapping({STUDENT_PAGE_URL})
public class StudentController implements PersonController {


    @Resource
    private StudentService studentService;

    @GetMapping
    public String get(Model model) {
        model.addAttribute("studentPageModel", new StudentPageModel());
        return STUDENT_VIEW_NAME;
    }


    @PostMapping("json")
    @ResponseBody
    public StudentPageModel jsonTest( HttpSession session) {
        //todo process currentPage
        Specification<StudentEntity> specification = (Specification<StudentEntity>) session.getAttribute(STUDENT_SPECIFICATION_SESSION_NAME);
        return studentService.getPageModel(1, specification);
    }
}
