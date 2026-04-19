package journalApp.Repository;


import journalApp.Entities.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface JournalRepository extends MongoRepository<JournalEntry, String> //TypeOfData,ID format
{
}
//Inheritance through extend keyword