package tr1nks.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tr1nks.domain.entity.StudentEntity;
import tr1nks.domain.repository.StudentRepository;
import tr1nks.service.StudentService;

import javax.annotation.Resource;

@Service
public class StudentServiceImpl implements StudentService {
    private static final int PAGE_SIZE = 50;
    @Resource
    private StudentRepository studentRepository;

    @Override
    public Page<StudentEntity> getPage(int page, Specification<StudentEntity> specification) {
        return studentRepository.findAll(specification, PageRequest.of(page - 1, PAGE_SIZE));
    }
}
