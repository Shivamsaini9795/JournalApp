package com.example.journalApp.Repository;


import com.example.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    
    User findByUserName(String userName);

    void deleteByUserName(String username);
}


