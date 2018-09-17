package tr1nks.service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import tr1nks.domain.entity.StudentEntity;

public interface StudentService {
    Page<StudentEntity> getPage(int page, Specification<StudentEntity> specification);
}
