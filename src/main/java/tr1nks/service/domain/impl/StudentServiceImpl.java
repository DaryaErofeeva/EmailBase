package tr1nks.service.domain.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tr1nks.component.converter.EntityDTOConverter;
import tr1nks.controller.person.PersonController;
import tr1nks.domain.dto.StudentDTO;
import tr1nks.domain.entity.StudentEntity;
import tr1nks.domain.repository.StudentRepository;
import tr1nks.enums.TableColumnErrorMessages;
import tr1nks.enums.TableColumnIndex;
import tr1nks.model.person.student.StudentPageModel;
import tr1nks.service.domain.StudentService;
import tr1nks.service.logic.ParseService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private static final int PAGE_SIZE = 50;
    @Resource
    private StudentRepository studentRepository;
    @Resource
    private ParseService parseService;
    @Resource
    private EntityDTOConverter<StudentDTO, StudentEntity> studentEntityDTOConverter;


    @Override
    public StudentPageModel getPageModel(HttpSession session) {
        List<StudentDTO> dtos;
        if (Objects.nonNull(session.getAttribute(PersonController.ERROR_STUDENT_SESSION_NAME))) {
            dtos = getErrorStudents(session);
        } else {
            dtos = getPageDtos(1, session);
        }
        return new StudentPageModel(dtos, 1);
    }

    @Override
    public StudentPageModel getPageModel(int page, HttpSession session) {
        return new StudentPageModel(getPageDtos(page, session), 1);
    }

    private List<StudentDTO> getPageDtos(int page, HttpSession session) {
        Specification<StudentEntity> specification = (Specification<StudentEntity>) session.getAttribute(PersonController.STUDENT_SPECIFICATION_SESSION_NAME);
        if (Objects.nonNull(specification)) {
            return studentRepository.findAll(specification, PageRequest.of(page - 1, PAGE_SIZE))
                    .getContent().stream().map(studentEntityDTOConverter::toDTO)
                    .collect(Collectors.toList());
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private List<StudentDTO> getErrorStudents(HttpSession session) {
        List<StudentDTO> studentDTOS = new ArrayList<>(PAGE_SIZE);
        LinkedList<StudentDTO> studentQueue = new LinkedList<>((List<StudentDTO>) session.getAttribute(PersonController.ERROR_STUDENT_SESSION_NAME));
        for (int i = 0; i < PAGE_SIZE; i++) {
            studentDTOS.add(studentQueue.peek());
        }
        return studentDTOS;
    }

    @Override
    public boolean testEmail(String login) {
        return null == studentRepository.getFirstByLogin(login);
    }

    @Override
    public boolean testCode(String code) {
        return null == studentRepository.getFirstByCode(code);
    }

    @Override
    public StudentEntity save(StudentEntity student) {
        return studentRepository.save(student);
    }

    @Override
    public StudentEntity trySave(StudentEntity student, StudentDTO studentDto) {
        if (!testEmail(student.getLogin())) {
            studentDto.setChecked(true);
            studentDto.setErrorFieldIndex(TableColumnIndex.LOGIN.index);
            studentDto.setErrorMessage(TableColumnErrorMessages.LOGIN.message);
        } else if (!testCode(student.getCode())) {
            studentDto.setChecked(true);
            studentDto.setErrorFieldIndex(TableColumnIndex.CODE.index);
            studentDto.setErrorMessage(TableColumnErrorMessages.CODE.message);
        } else {
            return save(student);
        }
        return student;
    }
}
