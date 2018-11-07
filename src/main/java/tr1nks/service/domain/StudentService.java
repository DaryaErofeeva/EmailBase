package tr1nks.service.domain;

import tr1nks.domain.dto.StudentDTO;
import tr1nks.domain.entity.StudentEntity;
import tr1nks.model.person.student.StudentPageModel;

import javax.servlet.http.HttpSession;

public interface StudentService {
    void checkAllSelected(HttpSession session);

    void clearSelection(HttpSession session);

    StudentPageModel getPageModel(HttpSession session);

    StudentPageModel getPageModel(int page, HttpSession session);

    StudentEntity save(StudentEntity student);

    void processPage(StudentPageModel pageModel, HttpSession session);

    boolean testEmail(String login);

    boolean testCode(String code);

    boolean testStudent(StudentDTO student);
}
