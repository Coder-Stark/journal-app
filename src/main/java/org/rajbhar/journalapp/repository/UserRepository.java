package org.rajbhar.journalapp.repository;

import org.bson.types.ObjectId;
import org.rajbhar.journalapp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {     //<entity type, id>
    User findByUserName(String username);
}
