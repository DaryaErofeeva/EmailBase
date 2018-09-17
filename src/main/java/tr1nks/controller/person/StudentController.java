package tr1nks.controller.person;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tr1nks.domain.entity.GroupEntity;
import tr1nks.domain.entity.StudentEntity;
import tr1nks.model.StudentPageModel;
import tr1nks.service.StudentService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

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

    @PostMapping("/json")
    @ResponseBody
    public List<StudentEntity> jsonTest2(StudentPageModel inp) {
        System.out.println(inp);
        return Arrays.asList(
                new StudentEntity("Surname1", "Name1", "Patronymic1", "code1", new GroupEntity(), "login1", "password1", true),
                new StudentEntity("Surname2", "Name2", "Patronymic2", "code2", new GroupEntity(), "login1", "password2", true),
                new StudentEntity("Surname3", "Name3", "Patronymic3", "code3", new GroupEntity(), "login3", "password3", true)
        );
    }

    @PostMapping("/json/{page}")
    @ResponseBody
    public Page<StudentEntity> jsonTest(@RequestParam(required = false, defaultValue = "1") int page, HttpSession session) {
        //todo process currentPage
        studentService.getPage(page, (Specification<StudentEntity>) session.getAttribute(STUDENT_SPECIFICATION_SESSION_NAME));
//        return Arrays.asList(
//                new StudentEntity("Surname1", "Name1", "Patronymic1", "code1", new GroupEntity(), "login1", "password1", true),
//                new StudentEntity("Surname2", "Name2", "Patronymic2", "code2", new GroupEntity(), "login1", "password2", true),
//                new StudentEntity("Surname3", "Name3", "Patronymic3", "code3", new GroupEntity(), "login3", "password3", true)
//        );
        return null;
    }


}
