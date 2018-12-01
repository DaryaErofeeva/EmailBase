package tr1nks.service.domain;

import tr1nks.domain.dto.StudentDTO;
import tr1nks.domain.entity.StudentEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface StudentService {

    void save(StudentEntity student);

    void save(List<StudentDTO> students, HttpSession httpSession);

    void getAll();

    boolean testEmail(String login);

    boolean testCode(String code);

    boolean testStudent(StudentDTO student);
}
