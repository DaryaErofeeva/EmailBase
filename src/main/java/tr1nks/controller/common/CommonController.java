package tr1nks.controller.common;

import tr1nks.controller.Controller;

public interface CommonController extends Controller {
    String VIEW_BASE = "common/";
    String URL_BASE = "/common/";

    String INDEX_PAGE_URL = URL_BASE + "index";
    String INDEX_VIEW_NAME = VIEW_BASE + "index";

    String MAIN_PAGE_URL = URL_BASE + "main";
    String MAIN_VIEW_NAME = VIEW_BASE + "main";
}
