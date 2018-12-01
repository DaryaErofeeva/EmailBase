package tr1nks.controller.parse;

import tr1nks.controller.Controller;

public interface ParseController extends Controller {
    String VIEW_BASE = "parse/";
    String URL_BASE = "/parse/";

    String PARSE_URL = "parse";

    String PARSE_ERROR = "error";
    String ERROR_VIEW_NAME = VIEW_BASE + "error";

    String UPLOAD_URL = URL_BASE + "upload";
    String UPLOAD_VIEW_NAME = VIEW_BASE + "upload";
}
