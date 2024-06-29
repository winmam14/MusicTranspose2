package musicTranspose.databasewriter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends CrudRepository<Score, Integer> {
    // You can add custom query methods here if needed
}