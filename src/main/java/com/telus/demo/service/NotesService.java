package com.telus.demo.service;

import com.telus.demo.dao.NotesRepository;
import com.telus.demo.modal.Note;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class NotesService {

    private final NotesRepository noteRepository;

    public NotesService(NotesRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note addNote(Note note) {
        note.setTimestampCreated(LocalDateTime.now());
        note.setTimestampUpdated(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public Note modifyNote(Long id, Note noteDetails) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        note.setSubject(noteDetails.getSubject());
        note.setDescription(noteDetails.getDescription());
        note.setTimestampUpdated(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public void deleteNote(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        noteRepository.delete(note);
    }

    public List<Note> searchNotesBySubject(String subject) {
        return noteRepository.findBySubjectContainingIgnoreCase(subject);
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }

    public Long countTotalNotes() {
        return noteRepository.count();
    }

    public Integer getWordCount(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        return note.getDescription().split("\\s+").length;
    }

    public Double getAverageNoteLength() {
        List<Note> notes = noteRepository.findAll();
        return notes.stream()
                .mapToInt(note -> note.getDescription().split("\\s+").length)
                .average()
                .orElse(0.0);
    }

    public Note likeNote(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        note.setLikes(note.getLikes() + 1);
        note.setTimestampUpdated(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public Note unlikeNote(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
        note.setLikes(Math.max(note.getLikes() - 1, 0));
        note.setTimestampUpdated(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public List<Note> getLikedNotes() {
        return noteRepository.findByLikesGreaterThan(0);
    }
}

//@Service
//public class NoteService {
//
//    private final NoteRepository noteRepository;
//
//
//    public NoteService(NoteRepository noteRepository) {
//        this.noteRepository = noteRepository;
//    }
//
//    public List<Note> getAllNotes() {
//        return noteRepository.findAll();
//    }
//
//    public Optional<Note> getNoteById(Long id) {
//        return noteRepository.findById(id);
//    }
//
//    public List<Note> searchNotesBySubject(String subject) {
//        return noteRepository.findBySubjectContainingIgnoreCase(subject);
//    }
//
//    public Note createNote(Note note) {
//        return noteRepository.save(note);
//    }
//
//    public Note updateNote(Long id, Note updatedNote) {
//        return noteRepository.findById(id).map(note -> {
//            note.setSubject(updatedNote.getSubject());
//            note.setDescription(updatedNote.getDescription());
//            return noteRepository.save(note);
//        }).orElseThrow(() -> new RuntimeException("Note not found"));
//    }
//
//    public void deleteNoteById(Long id) {
//        noteRepository.deleteById(id);
//    }
//}