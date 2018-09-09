package tr1nks.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static tr1nks.controller.common.CommonController.MAIN_PAGE_URL;

@Controller
@RequestMapping({MAIN_PAGE_URL})
public class MainController implements CommonController {
    @GetMapping
    public String get() {
        return MAIN_VIEW_NAME;
    }
}
