package net.sampleproject.journalApp.Entities;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "User")
@Data
public class User {

    @Id
    private ObjectId userId;

    @Indexed(unique = true) //To make every username unique
    @NonNull
    private String username;

    @NonNull //The below value cannot be null and it will throw exception
    private String password;

    @DBRef //To link journal entries to users and make it a child of Users
    private List<JournalEntries> journalEntriesList = new ArrayList<>(); //List of Journal Entries for User

    private List<String> roles;
}
