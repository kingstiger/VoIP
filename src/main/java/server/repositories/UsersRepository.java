package server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import server.data.DAOs.UserDAO;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<UserDAO, String> {

    Optional<UserDAO> findByUserID(String ID);

    Optional<UserDAO> findByUsername(String username);

}
