package journalApp.services;


import journalApp.Entities.JournalEntries;
import journalApp.Entities.User;
import journalApp.Repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    @Autowired
    private JournalRepository journalRepo;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntries journalEntry, String userName)
    {
        try {
            User user = userService.findByUserName(userName);

            journalEntry.setDate(LocalDateTime.now());//To automatically set the date while giving entry
            JournalEntries save = journalRepo.save(journalEntry); //New Saved Journal entry
            user.getJournalEntriesList().add(save); //Add to list of journal entries of the User
            //user.setUsername(null);
            userService.saveUser(user); //To Save the updated User

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving ",e);
        }

    }
    public JournalEntries updateEntry(JournalEntries journalEntry)
    {

        journalEntry.setDate(LocalDateTime.now());//To automatically set the date while giving entry
        JournalEntries saved = journalRepo.save(journalEntry); //Save to Journal Entries repo
        return saved;


    }
    public List<JournalEntries> getAll() //To get data through the repository for GET
    {
        return journalRepo.findAll();
    }
    public Optional<JournalEntries> JournalEntries_byID(String Id){

        return journalRepo.findById(Id);
    }
    @Transactional
    public void delete_byID(String id, String userName){
        User user = userService.findByUserName(userName); //User summoned so the entry can be deleted
        user.getJournalEntriesList().removeIf(x -> x.getId().equals(id));
        journalRepo.deleteById(id);
        userService.saveUser(user); //User modified
    }

}
