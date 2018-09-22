package tr1nks.service.logic;

import org.springframework.data.jpa.domain.Specification;
import tr1nks.domain.dto.StudentDTO;
import tr1nks.domain.entity.StudentEntity;
import tr1nks.model.PageModel;
import tr1nks.model.parse.ParseModel;
import tr1nks.service.logic.impl.ParseServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ParseService {
    void test(ParseModel parseModel);

    PageModel parse(ParseModel parseModel, HttpSession session);

    ParseServiceImpl.Pair<List<StudentDTO>, Specification<StudentEntity>> parseStudents(ParseModel parseModel);
}
