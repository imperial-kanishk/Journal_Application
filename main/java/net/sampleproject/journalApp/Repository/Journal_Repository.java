package net.sampleproject.journalApp.Repository;

import net.sampleproject.journalApp.Entities.JournalEntries;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface Journal_Repository extends MongoRepository<JournalEntries, ObjectId> //TypeOfData,ID format
{
}
//Inheritance through extend keyword