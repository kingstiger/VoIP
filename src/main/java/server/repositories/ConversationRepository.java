package server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import server.data.DAOs.ConversationDAO;

@Repository
public interface ConversationRepository extends MongoRepository<ConversationDAO, String> {
}
