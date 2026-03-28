package com.example.JournalApp.service;

import com.example.JournalApp.entity.JournalEntry;
import com.example.JournalApp.entity.User;
import com.example.JournalApp.repository.JournalEntryRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional // but ise use karne ke lie main file me add enable trans
    public void saveEntry(JournalEntry journalEntry, String userName){
        User user = userService.findByUserName(userName);
        if (journalEntry.getDate() == null) {
            journalEntry.setDate(new Date());
        }
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        // give a thought yaha pe agar code fat gaya to kya karoge : as inconsistency aa jayegi
        //  agar ise transaction bana dia to no pro fir
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }

    public void saveEntry(JournalEntry journalEntry){
        if (journalEntry.getDate() == null) {
            journalEntry.setDate(new Date());
        }
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }



    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false;
        try{

            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x-> x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e){
            log.error("Error in deleting user by id: {} : ",id,e);
            throw new RuntimeException("An error occured while deleting the entry.",e);
        }
        return removed;
    }

    /**
     * Temporary migration logic: 
     * Sets the current Date for all entries that have 'null' in the DB.
     */
    @PostConstruct
    public void fixNullDates() {
        List<JournalEntry> allEntries = journalEntryRepository.findAll();
        long count = 0;
        for (JournalEntry entry : allEntries) {
            if (entry.getDate() == null) {
                entry.setDate(new Date());
                journalEntryRepository.save(entry);
                count++;
            }
        }
        if (count > 0) {
            log.info("Migration successful: Added date to {} journal entries.", count);
        }
    }
}
