package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.Entities.JournalEntries;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;


public interface Journal_Repository extends MongoRepository<JournalEntries, ObjectId> //TypeOfData,ID format
{
}
//Inheritance through extend keyword