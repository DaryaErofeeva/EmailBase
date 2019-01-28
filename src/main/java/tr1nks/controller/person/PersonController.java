package tr1nks.controller.person;

import tr1nks.controller.Controller;

public interface PersonController extends Controller {
    String URL_BASE = "/person/";
    String VIEW_BASE = "/";

    String STUDENT_PAGE_URL = URL_BASE + "student";
    String ZIP_PAGE_URL = URL_BASE + "zip";
    String EMAIL_PAGE_URL = URL_BASE + "email";
    String IMAGINE_PAGE_URL = URL_BASE + "imagine";
    String OFFICE_PAGE_URL = URL_BASE + "office";
    String STUDENT_VIEW_NAME = "student";
    String STUDENT_VIEW_URL = VIEW_BASE + STUDENT_VIEW_NAME;
    String TEMPLATE_PAGE_URL = URL_BASE + "template";

    String ERROR_STUDENT_SESSION_NAME = "errorStudentList";
    String STUDENT_SESSION_NAME = "studentList";
}
