package com.example.JournalApp.repository;


import com.example.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, ObjectId > {
    User findByUserName(String userName);
    User findByEmail(String email);
    void deleteByUserName(String userName);
}
