package tr1nks.controller.person;

import tr1nks.controller.Controller;

public interface PersonController extends Controller {
    String URL_BASE = "/person/";
    String VIEW_BASE = "/";

    String STUDENT_PAGE_URL = URL_BASE + "student";
    String STUDENT_VIEW_NAME = VIEW_BASE + "student";

    String ERROR_STUDENT_SESSION_NAME = "errorStudentList";
    String STUDENT_SESSION_NAME = "studentList";
}
