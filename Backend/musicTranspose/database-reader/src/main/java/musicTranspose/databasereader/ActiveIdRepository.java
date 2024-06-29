package musicTranspose.databasereader;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ActiveIdRepository extends CrudRepository<ActiveId, Integer> {
    // Using a custom query to get the latest entry based on createdDate
    @Query("SELECT a FROM ActiveId a ORDER BY a.idnr DESC LIMIT 1")
    Optional<ActiveId> findLatestEntry();

}