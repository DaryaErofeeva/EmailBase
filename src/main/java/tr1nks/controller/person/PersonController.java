package tr1nks.controller.person;

import tr1nks.controller.Controller;

public interface PersonController extends Controller {
    String URL_BASE = "/person/";

    String STUDENT_PAGE_URL = URL_BASE + "student";
    String ZIP_PAGE_URL = URL_BASE + "zip";
    String STUDENT_VIEW_NAME =  "student";

    String ERROR_STUDENT_SESSION_NAME = "errorStudentList";
    String STUDENT_SESSION_NAME = "studentList";
}
