package com.telus.demo.modal;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;

    private String subject;

    private String description;

    private int likes = 0;

    private LocalDateTime timestampCreated;

    private LocalDateTime timestampUpdated;

    // Getters and Setters
    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDateTime getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(LocalDateTime timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public LocalDateTime getTimestampUpdated() {
        return timestampUpdated;
    }

    public void setTimestampUpdated(LocalDateTime timestampUpdated) {
        this.timestampUpdated = timestampUpdated;
    }
}