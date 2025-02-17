package com.telus.demo.service;

import com.telus.demo.dao.NotesRepository;
import com.telus.demo.modal.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class NotesService {

    private final NotesRepository noteRepository;

    public NotesService(NotesRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note addNote(Note note) {
        log.info("Adding a new note with subject: {}", note.getSubject());
        note.setTimestampCreated(LocalDateTime.now());
        note.setTimestampUpdated(LocalDateTime.now());
        Note savedNote = noteRepository.save(note);
        log.info("Note with ID {} added successfully", savedNote.getNoteId());
        return savedNote;
    }

    public Note modifyNote(Long id, Note noteDetails) {
        log.info("Modifying note with ID {}", id);
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));

        note.setSubject(noteDetails.getSubject());
        note.setDescription(noteDetails.getDescription());
        note.setTimestampUpdated(LocalDateTime.now());
        Note updatedNote = noteRepository.save(note);
        log.info("Note with ID {} modified successfully", updatedNote.getNoteId());
        return updatedNote;
    }

    public void deleteNote(Long id) {
        log.info("Deleting note with ID {}", id);
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        noteRepository.delete(note);
        log.info("Note with ID {} deleted successfully", id);
    }

    public List<Note> searchNotesBySubject(String subject) {
        log.info("Searching notes with subject containing: {}", subject);
        List<Note> notes = noteRepository.findBySubjectContainingIgnoreCase(subject);
        log.info("Found {} notes with subject containing: {}", notes.size(), subject);
        return notes;
    }

    public Note getNoteById(Long id) {
        log.info("Fetching note with ID {}", id);
        return noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }

    public Long countTotalNotes() {
        log.info("Counting total number of notes");
        long count = noteRepository.count();
        log.info("Total number of notes: {}", count);
        return count;
    }

    public Integer getWordCount(Long id) {
        log.info("Getting word count for note with ID {}", id);
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        int wordCount = note.getDescription().split("\\s+").length;
        log.info("Note with ID {} has {} words", id, wordCount);
        return wordCount;
    }

    public Double getAverageNoteLength() {
        log.info("Calculating the average note length");
        List<Note> notes = noteRepository.findAll();
        double averageLength = notes.stream()
                .mapToInt(note -> note.getDescription().split("\\s+").length)
                .average()
                .orElse(0.0);
        log.info("Average note length: {} words", averageLength);
        return averageLength;
    }

    public Note likeNote(Long id) {
        log.info("Liking note with ID {}", id);
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        note.setLikes(note.getLikes() + 1);
        note.setTimestampUpdated(LocalDateTime.now());
        Note updatedNote = noteRepository.save(note);
        log.info("Note with ID {} liked successfully. Total likes: {}", id, updatedNote.getLikes());
        return updatedNote;
    }

    public Note unlikeNote(Long id) {
        log.info("Unliking note with ID {}", id);
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        note.setLikes(Math.max(note.getLikes() - 1, 0));
        note.setTimestampUpdated(LocalDateTime.now());
        Note updatedNote = noteRepository.save(note);
        log.info("Note with ID {} unliked successfully. Total likes: {}", id, updatedNote.getLikes());
        return updatedNote;
    }

    public List<Note> getLikedNotes() {
        log.info("Fetching all liked notes");
        List<Note> likedNotes = noteRepository.findByLikesGreaterThan(0);
        log.info("Found {} liked notes", likedNotes.size());
        return likedNotes;
    }
}
