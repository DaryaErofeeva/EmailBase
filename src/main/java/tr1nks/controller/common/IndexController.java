package tr1nks.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static tr1nks.controller.Controller.LOGIN_URL;
import static tr1nks.controller.common.CommonController.INDEX_PAGE_URL;

@Controller
@RequestMapping({INDEX_PAGE_URL, LOGIN_URL, "/"})
public class IndexController implements CommonController {
    @GetMapping
    public String get() {
        return INDEX_VIEW_NAME;
    }
}
