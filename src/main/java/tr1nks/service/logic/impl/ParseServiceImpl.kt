package tr1nks.service.logic.impl


import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import tr1nks.constants.FileColumn
import tr1nks.controller.person.PersonController
import tr1nks.domain.dto.*
import tr1nks.service.domain.StudentService
import tr1nks.service.logic.CredentialGenerationService
import tr1nks.service.logic.ParseService
import java.io.InputStreamReader
import java.nio.charset.Charset
import javax.servlet.http.HttpSession

@Service
class ParseServiceImpl @Autowired constructor(
        private val studentService: StudentService,
        private val credentialGenerationService: CredentialGenerationService
) : ParseService {

    override fun parseStudents(delimiter: Char, charset: Charset, file: MultipartFile, httpSession: HttpSession) =
            checkStudents(
                    studentDTOS = InputStreamReader(file.inputStream, charset).use {
                        CSVParser(it, CSVFormat.newFormat(delimiter).withFirstRecordAsHeader().withTrim())
                                .use { it.map { parseStudentDTO(csvRecord = it) } }
                    }, httpSession = httpSession)

    private fun parseStudentDTO(csvRecord: CSVRecord) = StudentDTO(
            csvRecord.get(FileColumn.SURNAME),
            csvRecord.get(FileColumn.NAME),
            csvRecord.get(FileColumn.PATRONYMIC),
            csvRecord.get(FileColumn.CODE),
            credentialGenerationService.createLogin(csvRecord.get(FileColumn.SURNAME), csvRecord.get(FileColumn.NAME)),
            credentialGenerationService.generatePassword(LENGTH_OF_PASSWORD),
            parseGroupDto(csvRecord.get(FileColumn.GROUP).split(""".""").map { it.toInt() }),
            parseTrueFlag(FileColumn.BUDGET)
    )

    private fun parseGroupDto(groupCipherArray: List<Int>) = GroupDTO(
            StudyLevelDTO(groupCipherArray[0]),
            FacultyDTO(groupCipherArray[1]),
            SpecializationDTO(groupCipherArray[3], SpecialityDTO(groupCipherArray[2])),
            groupCipherArray[4],
            groupCipherArray[5]
    )

    private fun parseTrueFlag(s: String): Boolean {
        return s == "true" || s == "да" || s == "+" || s == "yes" || s == "1"
    }

    override fun checkStudents(studentDTOS: List<StudentDTO>, httpSession: HttpSession): Boolean {
        val validStudentDtos = httpSession.getAttribute(PersonController.STUDENT_SESSION_NAME) as? MutableList<StudentDTO>
                ?: mutableListOf()
        val errorStudentDtos = mutableListOf<StudentDTO>()

        validStudentDtos.addAll(studentDTOS.filter { studentService.testStudent(it) })
        errorStudentDtos.addAll(studentDTOS.filter { it !in validStudentDtos })

        httpSession.setAttribute(PersonController.STUDENT_SESSION_NAME, validStudentDtos)
        httpSession.setAttribute(PersonController.ERROR_STUDENT_SESSION_NAME, errorStudentDtos)

        return errorStudentDtos.isEmpty()
    }

    companion object {
        const val LENGTH_OF_PASSWORD = 8
    }
}