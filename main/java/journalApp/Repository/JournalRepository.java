package journalApp.Repository;


import journalApp.Entities.JournalEntries;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface JournalRepository extends MongoRepository<JournalEntries, ObjectId> //TypeOfData,ID format
{
}
//Inheritance through extend keyword