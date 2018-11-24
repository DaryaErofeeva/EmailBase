package tr1nks.service.domain.impl;

import jdk.nashorn.internal.runtime.Specialization;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tr1nks.component.converter.EntityDTOConverter;
import tr1nks.controller.person.PersonController;
import tr1nks.domain.dto.StudentDTO;
import tr1nks.domain.entity.*;
import tr1nks.domain.repository.*;
import tr1nks.constants.StudentField;
import tr1nks.constants.TableColumnErrorMessages;
import tr1nks.model.person.student.StudentPageModel;
import tr1nks.service.domain.StudentService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private static final int PAGE_SIZE = 50;

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private GroupRepository groupRepository;

    @Resource
    private FacultyRepository facultyRepository;

    @Resource
    private SpecializationRepository specializationRepository;

    @Resource
    private StudyLevelRepository studyLevelRepository;

    @Resource
    private SpecialityRepository specialityRepository;

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
//            if (dto.isChecked()) {
//                ids.add(dto.getId());
//            } else {
//                ids.remove(dto.getId());
//            }
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
//                dtos.stream().filter(dto -> checkedIds.contains(dto.getId())).forEach(dto -> dto.setChecked(true));
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
    public void save(StudentEntity student) {
        student.setGroupEntity(getOrSaveGroup(student.getGroupEntity()));
        studentRepository.save(student);
    }

    private SpecialityEntity getOrSaveSpeciality(SpecialityEntity specialityEntity) {
        SpecialityEntity studentSpecialityEntity = specialityRepository
                .getTopBySpecialityId(specialityEntity.getSpecialityId());

        return Optional.ofNullable(studentSpecialityEntity).isPresent() ? studentSpecialityEntity :
                specialityRepository.save(specialityEntity);
    }

    private SpecializationEntity getOrSaveSpecialization(SpecializationEntity specializationEntity) {
        specializationEntity.setSpecialityEntity(getOrSaveSpeciality(specializationEntity.getSpecialityEntity()));

        SpecializationEntity studentSpecializationEntity =
                specializationRepository.getTopBySpecializationIdAndSpecialityEntityId(
                        specializationEntity.getSpecializationId(),
                        specializationEntity.getSpecialityEntity().getSpecialityId());

        return Optional.ofNullable(studentSpecializationEntity).isPresent() ? studentSpecializationEntity :
                specializationRepository.save(specializationEntity);
    }

    private FacultyEntity getOrSaveFaculty(FacultyEntity facultyEntity) {
        FacultyEntity studentFacultyEntity = facultyRepository.getTopByFacultyId(facultyEntity.getFacultyId());

        return Optional.ofNullable(studentFacultyEntity).isPresent() ? studentFacultyEntity :
                facultyRepository.save(facultyEntity);
    }

    private StudyLevelEntity getOrSaveStudyLevel(StudyLevelEntity studyLevelEntity) {
        StudyLevelEntity studentStudyLevelEntity = studyLevelRepository.getTopByLevelId(studyLevelEntity.getLevelId());

        return Optional.ofNullable(studentStudyLevelEntity).isPresent() ? studentStudyLevelEntity :
                studyLevelRepository.save(studyLevelEntity);
    }

    private GroupEntity getOrSaveGroup(GroupEntity groupEntity) {
        groupEntity.setFacultyEntity(getOrSaveFaculty(groupEntity.getFacultyEntity()));
        groupEntity.setSpecializationEntity(getOrSaveSpecialization(groupEntity.getSpecializationEntity()));
        groupEntity.setStudyLevelEntity(getOrSaveStudyLevel(groupEntity.getStudyLevelEntity()));

        GroupEntity studentGroupEntity = groupRepository
                .getTopByFacultyEntityIdAndSpecializationEntityIdAndStudyLevelEntityIdAndNumAndYear(
                        groupEntity.getFacultyEntity().getId(),
                        groupEntity.getSpecializationEntity().getId(),
                        groupEntity.getStudyLevelEntity().getId(),
                        groupEntity.getNum(), groupEntity.getYear());

        return Optional.ofNullable(studentGroupEntity).isPresent() ? studentGroupEntity :
                groupRepository.save(groupEntity);
    }

    @Override
    public void save(List<StudentEntity> students) {
        students.forEach(this::save);
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
            student.setErrorField(StudentField.LOGIN);
            student.setErrorMessage(TableColumnErrorMessages.LOGIN.message);
        }
        if (!testCode(student.getCode())) {
            valid = false;
            student.setErrorField(StudentField.CODE);
            student.setErrorMessage(TableColumnErrorMessages.CODE.message);
        }
        return valid;
    }
}
