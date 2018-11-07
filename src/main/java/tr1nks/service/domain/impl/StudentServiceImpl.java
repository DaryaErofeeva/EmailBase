package tr1nks.service.domain.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tr1nks.component.converter.EntityDTOConverter;
import tr1nks.controller.person.PersonController;
import tr1nks.domain.dto.GroupDTO;
import tr1nks.domain.dto.StudentDTO;
import tr1nks.domain.entity.MyEntity;
import tr1nks.domain.entity.StudentEntity;
import tr1nks.domain.repository.StudentRepository;
import tr1nks.enums.TableColumnErrorMessages;
import tr1nks.enums.TableColumnIndex;
import tr1nks.model.person.student.StudentPageModel;
import tr1nks.service.domain.StudentService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private static final int PAGE_SIZE = 50;
    @Resource
    private StudentRepository studentRepository;
    //    @Resource
//    private ParseService parseService;
    @Resource
    private EntityDTOConverter<StudentDTO, StudentEntity> studentEntityDTOConverter;

    @Override
    public void processPage(StudentPageModel pageModel, HttpSession session) {
        Set<Long> ids = (Set<Long>) session.getAttribute(PersonController.CHECKED_STUDENT_IDS_SESSION_NAME);
        if (Objects.isNull(ids)) {
            ids = new HashSet<>();
            session.setAttribute(PersonController.CHECKED_STUDENT_IDS_SESSION_NAME, ids);
        }
        for (StudentDTO dto : pageModel.getStudents()) {
            if (dto.isChecked()) {
                ids.add(dto.getId());
            } else {
                ids.remove(dto.getId());
            }
        }
    }

    @Override
    public void checkAllSelected(HttpSession session) {
        Set<Long> ids = (Set<Long>) session.getAttribute(PersonController.CHECKED_STUDENT_IDS_SESSION_NAME);
        Specification<StudentEntity> specification = (Specification<StudentEntity>) session.getAttribute(PersonController.STUDENT_SPECIFICATION_SESSION_NAME);
        if (Objects.isNull(ids)) {
            ids = new HashSet<>();
            session.setAttribute(PersonController.CHECKED_STUDENT_IDS_SESSION_NAME, ids);
        }
        if (Objects.nonNull(specification)) {
            ids.addAll(studentRepository.findAll(specification).stream()
                    .map(MyEntity::getId).collect(Collectors.toList()));
        }
    }

    @Override
    public void clearSelection(HttpSession session) {
        session.removeAttribute(PersonController.CHECKED_STUDENT_IDS_SESSION_NAME);
        session.removeAttribute(PersonController.STUDENT_SPECIFICATION_SESSION_NAME);
    }

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
            List<StudentDTO> dtos = studentRepository.findAll(specification, PageRequest.of(page - 1, PAGE_SIZE))
                    .getContent().stream().map(studentEntityDTOConverter::toDTO)
                    .collect(Collectors.toList());
            Set<Long> checkedIds = (Set<Long>) session.getAttribute(PersonController.CHECKED_STUDENT_IDS_SESSION_NAME);
            if (Objects.nonNull(checkedIds)) {
                dtos.stream().filter(dto -> checkedIds.contains(dto.getId())).forEach(dto -> dto.setChecked(true));
            }
            return dtos;
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
    public StudentEntity save(StudentEntity student) {
        return studentRepository.save(student);
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
    public boolean testStudent(StudentDTO student) {
        boolean valid = true;
        if (!testEmail(student.getLogin())) {
            valid = false;
            student.setErrorFieldIndex(TableColumnIndex.LOGIN.index);
            student.setErrorMessage(TableColumnErrorMessages.LOGIN.message);
        }
        if (!testCode(student.getCode())) {
            valid = false;
            student.setErrorFieldIndex(TableColumnIndex.CODE.index);
            student.setErrorMessage(TableColumnErrorMessages.CODE.message);
        }
        return valid;
    }
}
