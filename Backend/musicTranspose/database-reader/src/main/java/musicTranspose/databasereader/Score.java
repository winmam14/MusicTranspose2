package musicTranspose.databasereader;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s_nr")
    private int s_nr;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "m_nr")
    private Music music;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "r_nr")
    private Register register;

    @Lob
    @Column(name = "s_data")
    private byte[] s_data;

    @Column(name = "s_date_added")
    private Date s_date_added;

    @Column(name = "s_part")
    private String s_part;

    @Column(name = "s_description")
    private String s_description;

    // Getters and setters
    public int getS_nr() {
        return s_nr;
    }

    public void setS_nr(int s_nr) {
        this.s_nr = s_nr;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public byte[] getS_data() {
        return s_data;
    }

    public void setS_data(byte[] s_data) {
        this.s_data = s_data;
    }

    public Date getS_date_added() {
        return s_date_added;
    }

    public void setS_date_added(Date s_date_added) {
        this.s_date_added = s_date_added;
    }

    public String getS_part() {
        return s_part;
    }

    public void setS_part(String s_part) {
        this.s_part = s_part;
    }

    public String getS_description() {
        return s_description;
    }

    public void setS_description(String s_description) {
        this.s_description = s_description;
    }
}

