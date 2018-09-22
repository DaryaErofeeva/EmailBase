package tr1nks.controller.parse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import tr1nks.model.parse.ParseModel;
import tr1nks.model.parse.UploadFileModel;
import tr1nks.service.logic.FileUploadService;
import tr1nks.service.logic.ParseService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static tr1nks.controller.parse.ParseController.UPLOAD_URL;

@Controller
@RequestMapping(UPLOAD_URL)
public class UploadController implements ParseController {
    private static final Logger logger = Logger.getLogger(UploadController.class);
    private static final String STUDENT_SAMPLE_STR = "Фамилия;Имя;Отчество;Код;Группа;Бюджет\nИванов;Иван;Иванович;co32432de;6.04.51.1.17.1;true";
    @Resource
    private FileUploadService fileUploadService;
    @Resource
    private ParseService parseService;

    @GetMapping
    public String get() {
        return UPLOAD_VIEW_NAME;
    }

    @PostMapping(path = "file")
    public ModelAndView postFile(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            UploadFileModel uploadModel = fileUploadService.uploadFile(file);
            ParseModel parseModel = new ParseModel(uploadModel);
            parseService.test(parseModel);
            return new ModelAndView(PARSE_VIEW_NAME, PARSE_MODEL_NAME, parseModel);
        } else {
            return new ModelAndView(REDIRECT + UPLOAD_URL + "?fileIsEmpty");
        }
    }

    @GetMapping(path = "sample/{person}Sample.csv")
    public void getSample(@PathVariable("person") String person, HttpServletResponse response) {
        try (OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream())) {
            response.setContentType("text/csv");
            if ("student".equalsIgnoreCase(person)) {
                writer.write(STUDENT_SAMPLE_STR);
            }
            writer.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
