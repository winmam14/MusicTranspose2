package musicTranspose.databasewriter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends CrudRepository<Music, Integer> {
    // You can add custom query methods here if needed
}
