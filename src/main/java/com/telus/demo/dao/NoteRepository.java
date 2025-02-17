package com.telus.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telus.demo.modal.Note;


@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findBySubjectContainingIgnoreCase(String subject);
}