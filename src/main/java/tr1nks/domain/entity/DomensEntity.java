package tr1nks.domain.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "domens")
public class DomensEntity extends MyEntity {
    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String emailDomen;
    @Basic
    @Column(name = "imagine", nullable = false, length = 50)
    private String imagineDomen;
    @Basic
    @Column(name = "office", nullable = false, length = 50)
    private String officeDomen;

    public DomensEntity(String gmail, String imagine, String office) {
        this.emailDomen = gmail;
        this.imagineDomen = imagine;
        this.officeDomen = office;
    }

    public DomensEntity() {
    }


    public String getEmailDomen() {
        return emailDomen;
    }

    public void setEmailDomen(String emailDomen) {
        this.emailDomen = emailDomen;
    }

    public String getImagineDomen() {
        return imagineDomen;
    }

    public void setImagineDomen(String imagineDomen) {
        this.imagineDomen = imagineDomen;
    }

    public String getOfficeDomen() {
        return officeDomen;
    }

    public void setOfficeDomen(String officeDomen) {
        this.officeDomen = officeDomen;
    }
}
