package tr1nks.service.domain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tr1nks.domain.dto.StudentDTO;
import tr1nks.domain.entity.StudentEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface StudentService {

    void save(StudentEntity student);

    void save(List<StudentDTO> students, HttpSession httpSession);

    @NotNull
    List<StudentDTO> getStudents(String facultyName, String group, Integer year);

    byte[] getArchive();

    boolean testEmail(String login);

    boolean testCode(String code);

    boolean testStudent(StudentDTO student);

    @NotNull
    List<StudentDTO> updateStudents(@NotNull List<StudentDTO> studentsDTO);
}
