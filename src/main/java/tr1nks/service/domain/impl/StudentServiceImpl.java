package tr1nks.service.domain.impl;

import org.springframework.stereotype.Service;
import tr1nks.component.converter.impl.plural.StudentEntitiesDtosConverter;
import tr1nks.constants.StudentField;
import tr1nks.constants.TableColumnErrorMessages;
import tr1nks.domain.dto.StudentDTO;
import tr1nks.domain.entity.*;
import tr1nks.domain.repository.*;
import tr1nks.service.domain.StudentService;
import tr1nks.service.logic.CredentialGenerationService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static tr1nks.controller.person.PersonController.ERROR_STUDENT_SESSION_NAME;
import static tr1nks.controller.person.PersonController.STUDENT_SESSION_NAME;

@Service
public class StudentServiceImpl implements StudentService {

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
    private StudentEntitiesDtosConverter studentEntitiesDtosConverter;

    @Resource
    private CredentialGenerationService credentialGenerationService;

    @Override
    public void save(StudentEntity student) {
        student.setGroupEntity(getOrSaveGroup(student.getGroupEntity()));
        if (!testEmail(student.getLogin()))
            student.setLogin(credentialGenerationService.createLogin(student.getSurname(), student.getName()));
        studentRepository.save(student);
    }

    @Override
    public void save(List<StudentDTO> students, HttpSession httpSession) {
        studentEntitiesDtosConverter.toEntity(students).forEach(this::save);
        httpSession.removeAttribute(STUDENT_SESSION_NAME);
        httpSession.removeAttribute(ERROR_STUDENT_SESSION_NAME);
    }

    @Override
    public List<StudentDTO> getAll() {
        return studentEntitiesDtosConverter.toDTO(studentRepository.findAll());
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
}
