package com.example.JournalApp.repository;

import com.example.JournalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){ // SA: sentiment analysis
        Query query = new Query();

        // by default ye AND operator me chalega
        // query.addCriteria(Criteria.where("email").exists(true));
        // query.addCriteria(Criteria.where("sentimentAnalysis").exists(true));

        // but you can also make a object for Criteria
//        Criteria criteria = new Criteria();

//        query.addCriteria(criteria.orOperator(
//                Criteria.where("email").exists(true),
//                // chaining
//                Criteria.where("email").ne(null).ne(""), // ne: not equal to
//                Criteria.where("sentimentAnalysis").exists(true)
//
//                // btw instead of this you can do that : email should match it's regular expression;
//
//
//        ));

//        Criteria.where("email").regex(
//                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$",
//                "i"
//        );
//        Criteria.where("sentimentAnalysis").exists(true);
        query.addCriteria(Criteria.where("roles").in("USER","ADMIN"));
        List<User> users = mongoTemplate.find(query,User.class);
        return users;

    }

}
