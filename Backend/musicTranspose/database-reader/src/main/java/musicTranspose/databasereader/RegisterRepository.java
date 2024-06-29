package musicTranspose.databasereader;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepository extends CrudRepository<Register, Integer> {
    // You can add custom query methods here if needed
}

