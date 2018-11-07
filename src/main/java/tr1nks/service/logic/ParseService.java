package tr1nks.service.logic;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;

public interface ParseService {

    void parseStudents(char delimiter, Charset charset, MultipartFile file, HttpSession session);
}
