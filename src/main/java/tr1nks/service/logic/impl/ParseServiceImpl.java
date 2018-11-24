package tr1nks.service.logic.impl;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tr1nks.constants.FileColumn;
import tr1nks.controller.person.PersonController;
import tr1nks.domain.dto.*;
import tr1nks.domain.repository.GroupRepository;
import tr1nks.service.domain.StudentService;
import tr1nks.service.logic.CredentialGenerationService;
import tr1nks.service.logic.ParseService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ParseServiceImpl implements ParseService {
    private static final Logger logger = Logger.getLogger(ParseServiceImpl.class);
    private static final int LENGTH_OF_PASSWORD = 8;

    @Resource
    private StudentService studentService;

    @Resource
    private CredentialGenerationService credentialGenerationService;

    @Resource
    private GroupRepository groupRepository;

    @Override
    public boolean parseStudents(char delimiter, Charset charset, MultipartFile file, HttpSession httpSession) {
        List<StudentDTO> studentDtos = new ArrayList<>();
        List<StudentDTO> errorStudentDtos = new ArrayList<>();

        try (Reader reader = new InputStreamReader(file.getInputStream(), charset);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.newFormat(delimiter).withFirstRecordAsHeader().withTrim())) {
            for (CSVRecord csvRecord : csvParser) {
                StudentDTO studentDTO = parseStudentDTO(csvRecord);
                if (studentService.testStudent(studentDTO)) studentDtos.add(studentDTO);
                else errorStudentDtos.add(studentDTO);
            }
        } catch (IOException e) {
            logger.error("Error while converting paths to file models", e);
            e.printStackTrace();
        }

        httpSession.setAttribute(PersonController.STUDENT_SESSION_NAME, studentDtos);
        httpSession.setAttribute(PersonController.ERROR_STUDENT_SESSION_NAME, errorStudentDtos);

        return errorStudentDtos.isEmpty();
    }

    private StudentDTO parseStudentDTO(CSVRecord csvRecord) {
        int[] groupCipherArray = Arrays.stream(csvRecord.get(FileColumn.GROUP)
                .split("\\.")).mapToInt(Integer::parseInt).toArray();
        GroupDTO groupDTO = parseGroupDto(groupCipherArray);
        String login = credentialGenerationService.createLogin(csvRecord.get(FileColumn.SURNAME), csvRecord.get(FileColumn.NAME));
        String password = credentialGenerationService.generatePassword(LENGTH_OF_PASSWORD);
        return new StudentDTO(csvRecord.get(FileColumn.SURNAME), csvRecord.get(FileColumn.NAME),
                csvRecord.get(FileColumn.PATRONYMIC), csvRecord.get(FileColumn.CODE),
                login, password, groupDTO, parseTrueFlag(FileColumn.BUDGET));
    }

    private GroupDTO parseGroupDto(int[] groupCipherArray) {
        return new GroupDTO(
                new StudyLevelDTO(groupCipherArray[0]),
                new FacultyDTO(groupCipherArray[1]),
                new SpecializationDTO(groupCipherArray[3], new SpecialityDTO(groupCipherArray[2])),
                groupCipherArray[4],
                groupCipherArray[5]
        );
    }

    private boolean parseTrueFlag(String s) {
        return s.equals("true") || s.equals("да") || s.equals("+") || s.equals("yes") || s.equals("1");
    }

    @Override
    public boolean checkStudents(List<StudentDTO> studentDTOS, HttpSession httpSession) {
        List<StudentDTO> studentDtos = (List<StudentDTO>) httpSession.getAttribute(PersonController.STUDENT_SESSION_NAME);
        List<StudentDTO> errorStudentDtos = new ArrayList<>();

        studentDTOS.forEach(studentDTO -> {
            if(studentService.testStudent(studentDTO)) studentDTOS.add(studentDTO);
            else errorStudentDtos.add(studentDTO);
        });

        httpSession.setAttribute(PersonController.STUDENT_SESSION_NAME, studentDtos);
        httpSession.setAttribute(PersonController.ERROR_STUDENT_SESSION_NAME, errorStudentDtos);

        return errorStudentDtos.isEmpty();
    }
}