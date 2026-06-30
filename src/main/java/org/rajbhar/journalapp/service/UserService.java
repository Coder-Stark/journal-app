package org.rajbhar.journalapp.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.rajbhar.journalapp.entity.JournalEntry;
import org.rajbhar.journalapp.entity.User;
import org.rajbhar.journalapp.repository.JournalEntryRepository;
import org.rajbhar.journalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;    //AccessModifier  DataType/ClassName   VariableName;

    //get all service
    public List<User> getAll(){
        return  userRepository.findAll();        //extended mongodb have this findAll property
    }

    //create service
    public void saveEntry(User user){
        userRepository.save(user);
    }

    //get by id service
    public Optional<User> findById(ObjectId id){   //optional -> box (may or may not have data)
        return userRepository.findById(id);
    }

    //delete by id service
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    //find by username service
    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}


