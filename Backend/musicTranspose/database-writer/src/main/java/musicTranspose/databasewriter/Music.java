package musicTranspose.databasewriter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int m_nr;
    private String m_name;

    // Getters and setters
    public int getM_nr() {
        return m_nr;
    }

    public void setM_nr(int m_nr) {
        this.m_nr = m_nr;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }
}

