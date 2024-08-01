package com.example.jackit_backend.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class EntryService {

    @Autowired
    private EntryRepository entryRepository;

    public List<Entry> getEntries(){
        return entryRepository.findAll();
    }

    public void deleteEntries(){
        entryRepository.deleteAll();
    }

    public Entry saveEntries(List<Entry> entries){
        // Clear previous session's entries and save current session's
        deleteEntries();
        entryRepository.saveAll(entries);

        // Choose the winning entry at random based on rating win ranges.
        List<Entry> updatedEntries = generateWinRanges(entries);    // ASK LATER IF UPDATEDENTRIES VARIABLE NECESSARY
        return chooseAtRandom(updatedEntries);                      // BECAUSE OF TRANSIENT
    }

    private List<Entry> generateWinRanges(List<Entry> entries){
        int start = 1;
        int end = 0;

        for(Entry entry : entries){
            if(entry.getRating() == 0){
                entry.setMin(-1);
                entry.setMax(-1);
                continue;
            }

            end = start + entry.getRating() - 1;
            entry.setMin(start);
            entry.setMax(end);
            start = end + 1;
        }

        return entries;
    }

    private Entry chooseAtRandom(List<Entry> updatedEntries){
        // Generate number between 1-100 inclusive.
        int roll = new Random().nextInt(100) + 1;
        System.out.println("rolled a " + roll);
        Entry winner = null;

        for(Entry entry : updatedEntries){
            if(entry.getMin() <= roll && roll <= entry.getMax()){
                winner = entry;
                break;
            }
        }

        return winner;
    }
}
