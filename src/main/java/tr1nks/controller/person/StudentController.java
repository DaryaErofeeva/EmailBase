package tr1nks.controller.person;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tr1nks.domain.entity.GroupEntity;
import tr1nks.domain.entity.StudentEntity;

import java.util.Arrays;
import java.util.List;

import static tr1nks.controller.person.PersonController.STUDENT_PAGE_URL;

@Controller
@RequestMapping({STUDENT_PAGE_URL})
public class StudentController implements PersonController {

    @GetMapping
    public String get(Model model) {
        return STUDENT_VIEW_NAME;
    }

    @PostMapping("/json")
    @ResponseBody
    public List<StudentEntity> jsonTest(String inp) {
        System.out.println(inp);
        return Arrays.asList(
                new StudentEntity( "Surname1","Name1", "Patronymic1", "code1", new GroupEntity(), "login1", "password1", true),
                new StudentEntity( "Surname2","Name2", "Patronymic2", "code2", new GroupEntity(), "login1", "password2", true),
                new StudentEntity( "Surname3","Name3", "Patronymic3", "code3", new GroupEntity(), "login3", "password3", true)
        );
    }
}
