package tr1nks.controller.person;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tr1nks.model.TestModel;
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
    public StudentPageModel getPage(@RequestBody(required = false) StudentPageModel pageModel, HttpSession session) {
//        studentService.processPage(pageModel, session);
        return studentService.getPageModel(1, session);//todo page num
    }

    @PostMapping("test")
    @ResponseBody
    public TestModel jsonTest(@RequestBody TestModel testModel, HttpSession session) {
        System.out.println(testModel.getTextVal());
        testModel.setTextVal("Hello from spring");
        return testModel;
    }
}
