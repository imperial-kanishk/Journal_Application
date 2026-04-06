package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.Entities.JournalEntries;
import net.engineeringdigest.journalApp.Entities.User;
import net.engineeringdigest.journalApp.Repository.Journal_Repository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalService {

    @Autowired
    private Journal_Repository journalRepo;
    @Autowired
    private UserService userService;

    @Transactional
    public void SaveEntry(JournalEntries journalEntry, String userName)
    {
        try {
            User user = userService.findByUserName(userName);

            journalEntry.setDate(LocalDateTime.now());//To automatically set the date while giving entry
            JournalEntries save = journalRepo.save(journalEntry); //New Saved Journal entry
            user.getJournalEntriesList().add(save); //Save to list of journal entries of the User
            //user.setUsername(null);
            userService.SaveEntry(user); //To Save the updated User

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving ");
        }

    }
    public void UpdateEntry(JournalEntries journalEntry)
    {

        journalEntry.setDate(LocalDateTime.now());//To automatically set the date while giving entry
        JournalEntries save = journalRepo.save(journalEntry); //Save to Journal Entries repo



    }
    public List<JournalEntries> getAll() //To get data through the repository for GET
    {
        return journalRepo.findAll();
    }
    public Optional<JournalEntries> JournalEntries_byID(ObjectId Id){

        return journalRepo.findById(Id);
    }
    public void delete_byID(ObjectId id, String userName){
        User user = userService.findByUserName(userName); //User summoned so the entry can be deleted
        user.getJournalEntriesList().removeIf(x -> x.getId().equals(id));
        journalRepo.deleteById(id);
        userService.SaveEntry(user); //User modified
    }

}
