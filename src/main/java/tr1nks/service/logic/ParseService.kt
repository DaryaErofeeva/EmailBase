package tr1nks.service.logic

import org.springframework.web.multipart.MultipartFile
import tr1nks.domain.dto.GroupDTO
import tr1nks.domain.dto.StudentDTO
import java.nio.charset.Charset
import javax.servlet.http.HttpSession

interface ParseService {

    fun parseStudents(delimiter: Char, charset: Charset, file: MultipartFile, session: HttpSession): Boolean

    fun checkStudents(studentDTOS: List<StudentDTO>, httpSession: HttpSession): Boolean
}
