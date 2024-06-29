package musicTranspose.databasereader;

import jakarta.persistence.*;

import java.sql.Blob;

@Entity
@Table(name = "picture")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PNr")
    private Integer pnr = null;
    @Lob
    @Column(name = "pitem") // Specify the column name explicitly
    private byte[] pItem;
    @Column(name = "pname")
    private String pName;

    public Integer getPnr() {
        return pnr;
    }

    public void setPnr(Integer pnr) {
        this.pnr = pnr;
    }

    public byte[] getpItem() {
        return pItem;
    }

    public void setpItem(byte[] pItem) {
        this.pItem = pItem;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}
