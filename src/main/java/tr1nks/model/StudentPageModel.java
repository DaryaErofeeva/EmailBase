package tr1nks.model;

import tr1nks.domain.entity.StudentEntity;

import java.util.List;

public class StudentPageModel implements MyModel {
    private int page = 1;
    private int pageSize = 50;
    private List<StudentEntity> students;

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

    public List<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }
}
