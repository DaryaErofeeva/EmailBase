package tr1nks.service.domain.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import tr1nks.component.converter.impl.GroupEntityDTOConverter;
import tr1nks.component.converter.impl.plural.StudentEntitiesDtosConverter;
import tr1nks.constants.StudentField;
import tr1nks.constants.TableColumnErrorMessages;
import tr1nks.domain.dto.StudentDTO;
import tr1nks.domain.entity.*;
import tr1nks.domain.repository.*;
import tr1nks.service.domain.StudentService;
import tr1nks.service.logic.CredentialGenerationService;
import tr1nks.service.logic.FileGenerationService;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private FileGenerationService fileGenerationService;

    @Resource
    private StudentEntitiesDtosConverter studentEntitiesDtosConverter;

    @Resource
    private GroupEntityDTOConverter groupEntityDTOConverter;

    @Resource
    private CredentialGenerationService credentialGenerationService;

    @Resource
    private EntityManager entityManager;

    @Override
    public void save(StudentEntity student) {
        entityManager.clear();
        student.setGroupEntity(getOrSaveGroup(student.getGroupEntity()));
        if (!testEmail(student.getLogin()))
            student.setLogin(credentialGenerationService.createLogin(student.getSurname(), student.getName()));
        studentRepository.saveAndFlush(student);
    }

    @Override
    public void save(List<StudentDTO> students, HttpSession httpSession) {
        studentEntitiesDtosConverter.toEntity(students).forEach(this::save);
        httpSession.removeAttribute(STUDENT_SESSION_NAME);
        httpSession.removeAttribute(ERROR_STUDENT_SESSION_NAME);
    }

    @Override
    public byte[] getArchive(List<StudentDTO> students) {
        return fileGenerationService.createPDFArchiveBytes(studentEntitiesDtosConverter.toEntity(students));
    }

    @Override
    public byte[] getEmailCsv(List<StudentDTO> students) {
        return fileGenerationService.createEmailsCsv(studentEntitiesDtosConverter.toEntity(students));
    }

    @Override
    public byte[] getImagineCsv(List<StudentDTO> students) {
        return fileGenerationService.createImagineCsv(studentEntitiesDtosConverter.toEntity(students));
    }

    @Override
    public byte[] getOfficeCsv(List<StudentDTO> students) {
        return fileGenerationService.createOfficeCsv(studentEntitiesDtosConverter.toEntity(students));
    }

    @NotNull
    @Override
    public List<StudentDTO> getStudents(String facultyName, String group, Integer year) {
        List<StudentEntity> studentEntities = new ArrayList<>();
        if (facultyName != null && !facultyName.isEmpty()) {
            if (group != null && !group.isEmpty()) {
                if (year != null) {
                    studentEntities.addAll(studentRepository
                            .findAllByGroupEntityFacultyEntityNameAndGroupEntityYearAndGroupEntity(facultyName, year, parseGroupEntity(group)));
                } else {
                    studentEntities.addAll(studentRepository
                            .findAllByGroupEntityFacultyEntityNameAndGroupEntity(facultyName, parseGroupEntity(group)));
                }
            } else {
                if (year != null) {
                    studentEntities.addAll(studentRepository.findAllByGroupEntityFacultyEntityNameAndGroupEntityYear(facultyName, year));
                } else {
                    studentEntities.addAll(studentRepository.findAllByGroupEntityFacultyEntityName(facultyName));
                }
            }
        } else {
            if (group != null && !group.isEmpty()) {
                if (year != null) {
                    studentEntities.addAll(studentRepository.findAllByGroupEntityYearAndGroupEntity(year, parseGroupEntity(group)));
                } else {
                    studentEntities.addAll(studentRepository.findAllByGroupEntity(parseGroupEntity(group)));
                }
            } else {
                studentEntities.addAll(studentRepository.findAllByGroupEntityYear(year));
            }
        }
        return studentEntitiesDtosConverter.toDTO(studentEntities);
    }

    private GroupEntity parseGroupEntity(String group) {
        List<Integer> groupCipherList = Stream.of(group.split("\\.")).map(Integer::valueOf).collect(Collectors.toList());
        return groupRepository.getTopByStudyLevelEntityLevelIdAndFacultyEntityFacultyIdAndSpecializationEntitySpecialityEntitySpecialityIdAndSpecializationEntitySpecializationIdAndYearAndNum(
                groupCipherList.get(0), groupCipherList.get(1), groupCipherList.get(3), groupCipherList.get(2), groupCipherList.get(4), groupCipherList.get(5));
    }

    @NotNull
    @Override
    public List<StudentDTO> updateStudents(@NotNull List<StudentDTO> studentsDTO) {
        return studentEntitiesDtosConverter
                .toDTO(studentEntitiesDtosConverter.toEntity(studentsDTO).stream()
                        .map(studentEntity -> studentRepository.saveAndFlush(studentEntity)).collect(Collectors.toList()));
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
                specialityRepository.saveAndFlush(specialityEntity);
    }

    private SpecializationEntity getOrSaveSpecialization(SpecializationEntity specializationEntity) {
        specializationEntity.setSpecialityEntity(getOrSaveSpeciality(specializationEntity.getSpecialityEntity()));

        SpecializationEntity studentSpecializationEntity =
                specializationRepository.getTopBySpecializationIdAndSpecialityEntityId(
                        specializationEntity.getSpecializationId(),
                        specializationEntity.getSpecialityEntity().getSpecialityId());

        return Optional.ofNullable(studentSpecializationEntity).isPresent() ? studentSpecializationEntity :
                specializationRepository.saveAndFlush(specializationEntity);
    }

    private FacultyEntity getOrSaveFaculty(FacultyEntity facultyEntity) {
        FacultyEntity studentFacultyEntity = facultyRepository.getTopByFacultyId(facultyEntity.getFacultyId());

        return Optional.ofNullable(studentFacultyEntity).isPresent() ? studentFacultyEntity :
                facultyRepository.saveAndFlush(facultyEntity);
    }

    private StudyLevelEntity getOrSaveStudyLevel(StudyLevelEntity studyLevelEntity) {
        StudyLevelEntity studentStudyLevelEntity = studyLevelRepository.getTopByLevelId(studyLevelEntity.getLevelId());

        return Optional.ofNullable(studentStudyLevelEntity).isPresent() ? studentStudyLevelEntity :
                studyLevelRepository.saveAndFlush(studyLevelEntity);
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
                groupRepository.saveAndFlush(groupEntity);
    }
}
