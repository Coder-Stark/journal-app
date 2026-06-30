package org.rajbhar.journalapp.controller;


import org.bson.types.ObjectId;
import org.rajbhar.journalapp.entity.JournalEntry;
import org.rajbhar.journalapp.entity.User;
import org.rajbhar.journalapp.service.JournalEntryService;
import org.rajbhar.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")                 //entry point url -> /journal/xxxx
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    //localhost:8080/journal/userName
    @GetMapping("/{userName}")                           //(<empty>) -> '/'
    public ResponseEntity<?>getAllJournalEntriesOfUser(@PathVariable String userName){          //method  //it should always be public
        User user = userService.findByUserName(userName);
        List<JournalEntry> all =  user.getJournalEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //localhost:8080/journal POST
    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){    //body,type, any name
        try{
            journalEntryService.saveEntry( myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        //return journalEntryService.findById(myId).orElse(null);
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String  userName){
        JournalEntry journalEntry = journalEntryService.findById(myId).orElse(null);
        if(journalEntry != null){
            journalEntryService.deleteById(myId, userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntryById(              //myId = old, myEntry = new
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry,
            @PathVariable String userName
    ){
        JournalEntry oldEntry = journalEntryService.findById(myId).orElse(null);
        if(oldEntry != null){
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() :  oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
