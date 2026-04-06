package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.Entities.JournalEntries;
import net.engineeringdigest.journalApp.Entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> //TypeOfData,ID format
{
    User findByUsername(String username);

    void deleteByUserId(String userName);
}