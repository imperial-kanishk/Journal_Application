package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entities.JournalEntries;
import net.engineeringdigest.journalApp.Entities.User;
import net.engineeringdigest.journalApp.services.JournalService;
import net.engineeringdigest.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalService journal_service;

    @Autowired
    private UserService userservice;

    @GetMapping
    public ResponseEntity<?> getJournalEntriesByUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();


        User byUserName = userservice.findByUserName(userName); //Finds User
        List<JournalEntries> all = byUserName.getJournalEntriesList(); //Make a List of Journal Entries of that User found
        if (all != null &&  !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public  ResponseEntity<JournalEntries> CreateEntries(@RequestBody JournalEntries my_entry){

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

           journal_service.SaveEntry(my_entry, userName); //while saving the entry User will also be passed
           return new ResponseEntity<>(my_entry,HttpStatus.CREATED);

       } catch(Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

    }

    @GetMapping("/id/{myId}") //To get  ID by ObjectID
    public ResponseEntity<JournalEntries> GetJournalEntryById(@PathVariable ObjectId myId ){
        Optional<JournalEntries> journalEntries = journal_service.JournalEntries_byID(myId);
        if(journalEntries.isPresent()){
            return new ResponseEntity<>(journalEntries.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> delete(@PathVariable ObjectId myId, @PathVariable String userName){
        journal_service.delete_byID(myId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{userName}/{myId}") //To Update
    public ResponseEntity<JournalEntries> updateEntryById(@PathVariable ObjectId myId,
                                                          @PathVariable String userName,
                                                          @RequestBody JournalEntries newEntry){

        JournalEntries OldEntry = journal_service.JournalEntries_byID(myId).orElse(null);
        if (OldEntry != null){
            OldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("")? newEntry.getTitle() : OldEntry.getTitle());
            OldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : OldEntry.getContent());

            journal_service.UpdateEntry(OldEntry);
            return new ResponseEntity<>(OldEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}
