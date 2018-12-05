package tr1nks.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "domains")
public class DomainsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String emailDomen;

    @Basic
    @Column(name = "imagine", nullable = false, length = 50)
    private String imagineDomen;

    @Basic
    @Column(name = "office", nullable = false, length = 50)
    private String officeDomen;

    public DomainsEntity(String gmail, String imagine, String office) {
        this.emailDomen = gmail;
        this.imagineDomen = imagine;
        this.officeDomen = office;
    }

    public DomainsEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
