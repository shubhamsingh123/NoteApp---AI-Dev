package com.telus.demo.controller;

import com.telus.demo.modal.Note;
import com.telus.demo.service.NotesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody Note note) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notesService.addNote(note));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> modifyNote(@PathVariable Long id, @RequestBody Note noteDetails) {
        return ResponseEntity.ok(notesService.modifyNote(id, noteDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteNote(@PathVariable Long id) {
        notesService.deleteNote(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Note>> searchNotesBySubject(@RequestParam String subject) {
        return ResponseEntity.ok(notesService.searchNotesBySubject(subject));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(notesService.getNoteById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countTotalNotes() {
        return ResponseEntity.ok(notesService.countTotalNotes());
    }

    @GetMapping("/word-count/{id}")
    public ResponseEntity<Integer> getWordCount(@PathVariable Long id) {
        return ResponseEntity.ok(notesService.getWordCount(id));
    }

    @GetMapping("/average-length")
    public ResponseEntity<Double> getAverageNoteLength() {
        return ResponseEntity.ok(notesService.getAverageNoteLength());
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Note> likeNote(@PathVariable Long id) {
        return ResponseEntity.ok(notesService.likeNote(id));
    }

    @DeleteMapping("/{id}/unlike")
    public ResponseEntity<Note> unlikeNote(@PathVariable Long id) {
        return ResponseEntity.ok(notesService.unlikeNote(id));
    }

    @GetMapping("/liked")
    public ResponseEntity<List<Note>> getLikedNotes() {
        return ResponseEntity.ok(notesService.getLikedNotes());
    }
}