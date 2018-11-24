package tr1nks.service.logic;

import org.springframework.web.multipart.MultipartFile;
import tr1nks.domain.dto.StudentDTO;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.List;

public interface ParseService {

    boolean parseStudents(char delimiter, Charset charset, MultipartFile file, HttpSession session);

    boolean checkStudents(List<StudentDTO> studentDTOS, HttpSession httpSession);
}
