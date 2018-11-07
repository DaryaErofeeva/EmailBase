package tr1nks.controller.parse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import tr1nks.service.logic.ParseService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;

import static tr1nks.controller.parse.ParseController.UPLOAD_URL;

@Controller
@RequestMapping(UPLOAD_URL)
public class UploadController implements ParseController {

    @Resource
    private ParseService parseService;

    @GetMapping
    public String get() {
        return UPLOAD_VIEW_NAME;
    }

    @ResponseBody
    @PostMapping("file")
    public ModelAndView postFile(@RequestParam("delimiter") char delimiter, @RequestParam("charset") Charset charset,
                                 @RequestParam("file") MultipartFile file, HttpSession httpSession) {
        parseService.parseStudents(delimiter, charset, file, httpSession);
        return new ModelAndView("redirect:/" + ERROR_VIEW_NAME);
    }
}
