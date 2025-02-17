package com.telus.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.telus.demo.dao.NoteRepository;
import com.telus.demo.modal.Note;

@Service
public class NoteService {

    private NoteRepository noteRepository;

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    public List<Note> searchNotesBySubject(String subject) {
        return noteRepository.findBySubjectContainingIgnoreCase(subject);
    }

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public Note updateNote(Long id, Note updatedNote) {
        return noteRepository.findById(id).map(note -> {
            note.setSubject(updatedNote.getSubject());
            note.setDescription(updatedNote.getDescription());
            return noteRepository.save(note);
        }).orElseThrow(() -> new RuntimeException("Note not found"));
    }

    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }
}