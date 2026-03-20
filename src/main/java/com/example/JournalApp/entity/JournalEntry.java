package com.example.JournalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Data // : Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
// Using Lombok plugin "Data" : Compile time pe apne aap code generate ho jata hai
@Document(collection = "journal_entries")
@NoArgsConstructor
public class JournalEntry {

    @Id // map as primary key
    private ObjectId id; // ObjectId is a data type of mongoDb id

    @NonNull
    private String title;
    private String content;
    private Date date;


}
