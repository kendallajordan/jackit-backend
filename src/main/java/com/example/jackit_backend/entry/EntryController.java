package com.example.jackit_backend.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entries")
public class EntryController {

    @Autowired
    private EntryService entryService;

    @GetMapping
    public List<Entry> getEntryList() {
        return entryService.getEntries();
    }

    @DeleteMapping
    public void deleteEntryList(){
        entryService.deleteEntries();
    }

    @PostMapping
    public Entry postEntryList(@RequestBody List<Entry> entries){
        return entryService.saveEntries(entries);
    }
}
