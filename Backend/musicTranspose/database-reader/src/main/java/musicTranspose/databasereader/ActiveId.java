package musicTranspose.databasereader;

import jakarta.persistence.*;

@Entity
@Table(name = "activeid")
public class ActiveId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDNr")
    private Integer idnr = null;
    @Column(name = "id")
    private Integer id;

    public Integer getIDNr() {
        return idnr;
    }

    public void setIDNr(Integer idnr) {
        this.idnr = idnr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
