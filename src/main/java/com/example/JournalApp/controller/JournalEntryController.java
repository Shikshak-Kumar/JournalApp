package com.example.JournalApp.controller;

import com.example.JournalApp.entity.JournalEntry;
import com.example.JournalApp.entity.User;
import com.example.JournalApp.service.JournalEntryService;
import com.example.JournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(Authentication authentication){
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return ResponseEntity.ok(Map.of(
                "message","Currently no journal entries  for " + userName,
                "status","success"
        ));
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(Authentication authentication, @PathVariable ObjectId myId) {

        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        Optional<JournalEntry> entry = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(myId))
                .findFirst();

        if(entry.isPresent()){
            return ResponseEntity.ok(entry.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(Authentication authentication,@RequestBody JournalEntry myEntry){
        try{
            String userName = authentication.getName();
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);

        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId,
                                                    Authentication authentication){

        String userName = authentication.getName();
        boolean removed = journalEntryService.deleteById(myId,userName);
        if(removed){
            return new ResponseEntity<>(
                    Map.of(
                            "message","Journal entry deleted successfully",
                            "status","success"
                    ),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                Map.of(
                        "message","Journal entry not found",
                        "status","error"
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry,
            Authentication authentication){
            String userName = authentication.getName();
            Optional <JournalEntry> entries = journalEntryService.findById(myId);

            if(entries.isEmpty()){
                return new ResponseEntity<>(
                    Map.of(
                            "status","error",
                            "message","Journal entry not found"
                    ),
                    HttpStatus.NOT_FOUND
                );
            }

            JournalEntry old = entries.get();

            User user = userService.findByUserName(userName);

            boolean belongsToUser = user.getJournalEntries()
                    .stream()
                    .anyMatch(x->x.getId().equals(myId));

            if(!belongsToUser){
                return new ResponseEntity<>(
                        Map.of(
                                "status","error",
                                "message","Unauthorized to update this entry"
                        )
                        ,HttpStatus.FORBIDDEN
                );
            }

            if(newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty() ){
                old.setTitle(newEntry.getTitle());
            }

            if(newEntry.getContent() != null && !newEntry.getContent().isEmpty()){
                old.setContent(newEntry.getContent());
            }

            journalEntryService.saveEntry(old);

            return new ResponseEntity<>(
                    Map.of(
                            "status","success",
                            "message","Journal entry updated successfully",
                            "data",old
                    ),
                    HttpStatus.OK
            );
    }
}
