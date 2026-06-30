package org.rajbhar.journalapp.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.rajbhar.journalapp.entity.JournalEntry;
import org.rajbhar.journalapp.entity.User;
import org.rajbhar.journalapp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;    //AccessModifier  DataType/ClassName   VariableName;

    @Autowired
    private UserService userService;

    //get service
    public List<JournalEntry> getAll(){
        return  journalEntryRepository.findAll();        //extended mongodb have this findAll property
    }

    //create service
    public void saveEntry(JournalEntry journalEntry, String userName){
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);              //save to journal-entries
        user.getJournalEntries().add(saved);                                         //save to user's journal-entries
        userService.saveEntry(user);                                                 //save entry of user in database
    }

    //save entry in journal
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }


    //get by id service
    public Optional<JournalEntry> findById(ObjectId id){   //optional -> box (may or may not have data)
        return journalEntryRepository.findById(id);
    }

    //delete by id service
    public void deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));   //remove journal from user's list
        userService.saveEntry(user);                                               //save the updated user
        journalEntryRepository.deleteById(id);                                     //delete the journal document(from journal entries)
    }
}


