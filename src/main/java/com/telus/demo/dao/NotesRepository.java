package com.telus.demo.dao;

import com.telus.demo.modal.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotesRepository extends JpaRepository<Note, Long> {

    List<Note> findBySubjectContainingIgnoreCase(String subject);

    List<Note> findByLikesGreaterThan(int likes);
}

//@Repository
//public interface NoteRepository extends JpaRepository<Note, Long> {
//    List<Note> findBySubjectContainingIgnoreCase(String subject);
//}