package com.example.JournalApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data // : Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
// Using Lombok plugin "Data" : Compile time pe apne aap code generate ho jata hai 
@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id // map as primary key
    private ObjectId id; // ObjectId is a data type of mongoDb id

    @Indexed(unique = true) // manually karna padta hai else
    // write : spring.data.mongodb.auto-index-creation=true in application.properties
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @Indexed(unique = true, sparse = true)
    @NonNull
    private String email;
    private boolean sentimentAnalysis;

    @DBRef // need to add for reference
    private List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> roles;

}
