package musicTranspose.databasereader;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Register {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int r_nr;
    private String r_name;

    // Getters and setters
    public int getR_nr() {
        return r_nr;
    }

    public void setR_nr(int r_nr) {
        this.r_nr = r_nr;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }
}

