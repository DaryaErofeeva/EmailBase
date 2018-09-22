package tr1nks.service.domain;

import tr1nks.domain.dto.StudentDTO;
import tr1nks.domain.entity.StudentEntity;
import tr1nks.model.person.student.StudentPageModel;

import javax.servlet.http.HttpSession;

public interface StudentService {
    StudentPageModel getPageModel(HttpSession session);

    StudentPageModel getPageModel(int page, HttpSession session);

    boolean testEmail(String login);

    boolean testCode(String code);

    StudentEntity save(StudentEntity student);

    StudentEntity trySave(StudentEntity student, StudentDTO studentDto);
}
