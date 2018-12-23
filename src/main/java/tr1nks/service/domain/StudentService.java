package tr1nks.service.domain;

import org.jetbrains.annotations.NotNull;
import tr1nks.domain.dto.StudentDTO;
import tr1nks.domain.entity.StudentEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface StudentService {

    void save(StudentEntity student);

    void save(List<StudentDTO> students, HttpSession httpSession);

    byte[] getImagineCsv(List<StudentDTO> students);

    byte[] getOfficeCsv(List<StudentDTO> students);

    @NotNull
    List<StudentDTO> getStudents(String facultyName, String group, Integer year);

    @NotNull
    String getStudents(String name, String surname, String group);

    byte[] getArchive(List<StudentDTO> students);

    byte[] getEmailCsv(List<StudentDTO> students);

    boolean testEmail(String login);

    boolean testCode(String code);

    boolean testStudent(StudentDTO student);

    @NotNull
    List<StudentDTO> updateStudents(@NotNull List<StudentDTO> studentsDTO);
}
