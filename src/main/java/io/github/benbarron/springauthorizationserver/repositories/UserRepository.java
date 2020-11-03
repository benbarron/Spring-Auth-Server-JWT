package io.github.benbarron.springauthorizationserver.repositories;

import io.github.benbarron.springauthorizationserver.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUserByUsername(String username);
}
