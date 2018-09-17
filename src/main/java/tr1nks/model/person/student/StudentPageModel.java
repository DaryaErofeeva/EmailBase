package tr1nks.model.person.student;

import tr1nks.domain.dto.StudentDTO;

import java.util.List;

public class StudentPageModel {
    private int page;
    private int pageSize = 50;
    private List<StudentDTO> students;

    public StudentPageModel(List<StudentDTO> studentDTOS, int page) {
        this.students = studentDTOS;
        this.page = page;
    }

    public StudentPageModel() {
        this.page = 1;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }
}
