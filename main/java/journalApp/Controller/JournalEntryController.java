package journalApp.Controller;

import jakarta.validation.Valid;

import journalApp.Entities.JournalEntries;
import journalApp.Entities.User;

import journalApp.services.JournalService;
import journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getJournalEntriesByUserName(Authentication authentication){

        String userName = authentication.getName();


        User byUserName = userService.findByUserName(userName); //Finds User
        List<JournalEntries> all = byUserName.getJournalEntriesList(); //Make a List of Journal Entries of that User found
        if (all != null &&  !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public  ResponseEntity<JournalEntries> CreateEntries(@Valid @RequestBody JournalEntries my_entry,
                                                         Authentication authentication){

        try{
            String userName = authentication.getName();

           journalService.saveEntry(my_entry, userName); //while saving the entry User will also be passed
           return new ResponseEntity<>(my_entry,HttpStatus.CREATED);

       } catch(Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

    }

//    @GetMapping("/id/{myId}") //To get  ID by ObjectID
//    public ResponseEntity<JournalEntries> GetJournalEntryById(@PathVariable ObjectId myId ){
//        Optional<JournalEntries> journalEntries = journalService.JournalEntries_byID(myId);
//        if(journalEntries.isPresent()){
//            return new ResponseEntity<>(journalEntries.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable String myId, Authentication authentication) {
        String userName = authentication.getName();
        journalService.delete_byID(myId, userName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@PathVariable String id,
                                    @Valid @RequestBody JournalEntries newEntry,
                                    Authentication authentication) {

        String username = authentication.getName();
        User user = userService.findByUserName(username);

        Optional<JournalEntries> entryOpt = journalService.JournalEntries_byID(id);

        if (entryOpt.isPresent() && user.getJournalEntriesList().contains(entryOpt.get())) {

            JournalEntries entry = entryOpt.get();

            if (newEntry.getTitle() != null && !newEntry.getTitle().isBlank()) {
                entry.setTitle(newEntry.getTitle());
            }

            if (newEntry.getContent() != null && !newEntry.getContent().isBlank()) {
                entry.setContent(newEntry.getContent());
            }

            JournalEntries updated = journalService.updateEntry(entry);

            return ResponseEntity.ok(entry);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


}
