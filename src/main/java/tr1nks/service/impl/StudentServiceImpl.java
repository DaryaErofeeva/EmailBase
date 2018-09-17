package tr1nks.service.impl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tr1nks.domain.dto.StudentDTO;
import tr1nks.domain.entity.*;
import tr1nks.domain.repository.StudentRepository;
import tr1nks.model.person.student.StudentPageModel;
import tr1nks.service.StudentService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private static final int PAGE_SIZE = 50;
    @Resource
    private StudentRepository studentRepository;

    //    @Override
//    public StudentPageModel getPageModel(int page, Specification<StudentEntity> specification) {
//        List<StudentDTO> studentDTOS = studentRepository.findAll(specification, PageRequest.of(page - 1, PAGE_SIZE))
//                .getContent().stream().map(StudentDTO::new).collect(Collectors.toList());
//        StudentPageModel model= new StudentPageModel(studentDTOS,page);
//        return model;
//    }
    @Override
    public StudentPageModel getPageModel(int page, Specification<StudentEntity> specification) {
        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            StudentDTO dto = new StudentDTO();
            dto.setId(i);
            dto.setName("name-" + i);
            dto.setSurname("surname-" + i);
            dto.setPatronymic("patronymic-" + i);
            dto.setCode("code-" + i);
            dto.setLogin("login-" + i);
            dto.setInitPassword("initPassword-" + i);
            dto.setImagine(i % 2 == 0);
            dto.setOffice(i % 2 == 0);
            dto.setGroupEntity(
                    new GroupEntity(
                            new StudyLevelEntity(i, "level-" + i),
                            new FacultyEntity(i, "faculty-" + i, "faculty-" + i),
                            new SpecializationEntity(i, "specialization-" + i, new SpecialityEntity(i, "speciality-" + i)),
                            2015 + i,
                            i + 1
                    )
            );
            dto.setBudget(i % 2 == 0);

            dto.setChecked(i % 2 == 0);
            studentDTOS.add(dto);
        }

        StudentPageModel model = new StudentPageModel(studentDTOS, page);
        return model;
    }
}
