package tr1nks.service.logic.impl;


import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tr1nks.component.converter.EntityDTOConverter;
import tr1nks.controller.person.PersonController;
import tr1nks.domain.dto.*;
import tr1nks.domain.entity.StudentEntity;
import tr1nks.domain.repository.StudentRepository.StudentSpecifications;
import tr1nks.enums.*;
import tr1nks.model.PageModel;
import tr1nks.model.parse.ParseModel;
import tr1nks.service.domain.GroupService;
import tr1nks.service.domain.StudentService;
import tr1nks.service.logic.CredentialGenerationService;
import tr1nks.service.logic.FileStorageService;
import tr1nks.service.logic.ParseService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ParseServiceImpl implements ParseService {
    private static final Logger logger = Logger.getLogger(ParseServiceImpl.class);
    private static final int PREVIEW_ROWS_COUNT = 2;
    private static final int LENGTH_OF_PASSWORD = 8;
    private static final ParseFileMask[] STUDENT_PARSE_MASK = new ParseFileMask[]{
            ParseFileMask.SURNAME,
            ParseFileMask.NAME,
            ParseFileMask.PATRONYMIC,
            ParseFileMask.CODE,
            ParseFileMask.GROUP_OR_CATHEDRA,
            ParseFileMask.BUDGET_OR_RATE
    };
    @Resource
    private StudentService studentService;
    @Resource
    private FileStorageService fileStorageService;
    @Resource
    private GroupService groupService;
    @Resource
    private CredentialGenerationService credentialGenerationService;
    @Resource
    private EntityDTOConverter<StudentDTO, StudentEntity> studentEntityDTOConverter;

    @Override
    public void test(ParseModel parseModel) {
        File file = fileStorageService.findFileByNameAndDir(parseModel.getFileName(), parseModel.getDirName());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), parseModel.getCodePage().getValue()))) {
            String buf;
            byte i = 0;
            String[] row;
            parseModel.getPreviewRows().clear();
            while (i < PREVIEW_ROWS_COUNT && (buf = reader.readLine()) != null) {
                row = splitString(buf, parseModel.getDelimiter());
                parseModel.getPreviewRows().add(row);
                i++;
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);//todo
        }

    }

    @Override
    public PageModel parse(ParseModel parseModel, HttpSession session) {
        if (parseModel.getParsePerson().equals(ParsePerson.STUDENT)) {
            Pair<List<StudentDTO>, Specification<StudentEntity>> parsePair = parseStudents(parseModel);
            session.setAttribute(PersonController.STUDENT_SPECIFICATION_SESSION_NAME, parsePair.second);
//                if (errorStudents != null && !errorStudents.isEmpty()) {
//                    studentPageModel.setShowHiddenColumns(true);
//                    studentPageModel.setStudentModels(errorStudents);
//                } else {
//                    //studentPageModel.setStudentModels();//todo select first page by ids
//                }
//                return studentPageModel;
//            }
        }
        return null;
    }

    @Override
    public Pair<List<StudentDTO>, Specification<StudentEntity>> parseStudents(ParseModel parseModel) {
        List<StudentDTO> studentDtos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(parseModel.getFileName()), parseModel.getCodePage().getValue()))) {
            studentDtos.addAll(parseStudentDtos(parseModel.getMask(), parseModel.getDelimiter(), reader));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        List<Long> ids = new ArrayList<>();
        List<StudentDTO> errorStudentDtos = new ArrayList<>();

        for (StudentDTO studentDto : studentDtos) {
            StudentEntity student = studentService.trySave(studentEntityDTOConverter.toEntity(studentDto), studentDto);
            if (studentDto.getErrorFieldIndex() < 0) {
                ids.add(student.getId());
            } else {
                errorStudentDtos.add(studentDto);
            }
        }
        return new Pair<>(errorStudentDtos, StudentSpecifications.hasIdIn(ids));
    }


    //    private Pair<Specification<StudentEntity>, Specification<StudentEntity>> parseStudents() {
//        List<Long> ids = new ArrayList<>();
//        List<Long> errodIds = new ArrayList<>();
//
//
//        return new Pair<>(StudentSpecifications.hasIdIn(errodIds), StudentSpecifications.hasIdIn(ids));
//    }

    private List<StudentDTO> parseStudentDtos(ParseFileMask[] mask, Delimiter delimiter, BufferedReader reader) throws IOException {
        List<StudentDTO> students = new ArrayList<>();
        int[] parseOrder = parseMask(mask, STUDENT_PARSE_MASK);
        String buf;
        while ((buf = reader.readLine()) != null) {
            if (buf.toLowerCase().contains("имя") || buf.toLowerCase().contains("name")) {
                continue;
            }
            String[] arr = parseArrByOrder(splitString(buf, delimiter), parseOrder);
            int[] groupCipherArray = Arrays.stream(arr[4].split("\\.")).mapToInt(Integer::parseInt).toArray();
            GroupDTO groupDTO = parseGroupDto(groupCipherArray);
            String login = credentialGenerationService.createLogin(arr[0], arr[1]);
            String password = credentialGenerationService.generatePassword(LENGTH_OF_PASSWORD);
            students.add(new StudentDTO(arr[0], arr[1], arr[2], arr[3], login, password, groupDTO, parseTrueFlag(arr[5])));
        }
        return students;
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

    private String[] parseArrByOrder(String[] inp, int[] order) {
        String[] out = new String[inp.length];
        for (int i = 0; i < out.length; i++) {
            if (inp.length < order[i]) {
                out[i] = inp[order[i]];
            }//todo think if not such column
        }
        return out;
    }

    private int[] parseMask(ParseFileMask[] mask, ParseFileMask[] order) {
        int[] out = new int[mask.length];
        List tempOrder = Arrays.asList(order);
        for (int i = 0; i < mask.length; i++) {
            out[i] = tempOrder.indexOf(mask[i]);
        }
        return out;
    }

    private boolean parseTrueFlag(String s) {
        return s.equals("true") || s.equals("да") || s.equals("+") || s.equals("yes") || s.equals("1");
    }

    private String[] splitString(String rawString, Delimiter delimiter) {
        return rawString.split(delimiter.getValue());
    }

    public static class Pair<K, V> {
        private K first;
        private V second;

        public Pair() {
        }

        public Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }

        public K getFirst() {
            return first;
        }

        public void setFirst(K first) {
            this.first = first;
        }

        public V getSecond() {
            return second;
        }

        public void setSecond(V second) {
            this.second = second;
        }
    }
}