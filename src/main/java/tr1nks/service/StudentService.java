package tr1nks.service;

import org.springframework.data.jpa.domain.Specification;
import tr1nks.domain.entity.StudentEntity;
import tr1nks.model.person.student.StudentPageModel;

public interface StudentService {
    StudentPageModel getPageModel(int page, Specification<StudentEntity> specification);
}
