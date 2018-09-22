package tr1nks.controller.parse;

import tr1nks.controller.Controller;

public interface ParseController extends Controller {
    String VIEW_BASE = "parse/";
    String URL_BASE = "/parse/";

    String PARSE_URL = URL_BASE + "parse";
    String PARSE_TEST = "test";
    String PARSE_PARSE = "parse";
    String PARSE_VIEW_NAME = VIEW_BASE + "parse";
    String PARSE_MODEL_NAME = "parseModel";

    String UPLOAD_URL = URL_BASE + "upload";
    String UPLOAD_VIEW_NAME = VIEW_BASE + "upload";
    String UPLOAD_FILE_MODEL_NAME = "uploadFileModel";;
}
