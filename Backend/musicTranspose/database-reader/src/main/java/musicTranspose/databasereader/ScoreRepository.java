package musicTranspose.databasereader;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
interface ScoreWithoutData {
    int getS_nr();
    Music getMusic();
    Register getRegister();
    Date getS_date_added();
    String getS_part();
    String getS_description();
}
@Repository
public interface ScoreRepository extends CrudRepository<Score, Integer> {
    @Query("SELECT s.s_nr as s_nr, s.music as music, s.register as register, s.s_date_added as s_date_added, s.s_part as s_part, s.s_description as s_description FROM Score s WHERE s.music.m_nr = :m_nr")
    List<ScoreWithoutData> findByMusicIdWithoutData(Integer m_nr);
}