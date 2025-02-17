package com.telus.demo.modal;


import jakarta.persistence.*;

import java.time.Instant;
@Entity
public class Note {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String description;

    private Instant timestampCreated;
    private Instant timestampUpdated;

    public Note() {
    }

    public Note(Long noteId, String subject, String description, Instant timestampCreated, Instant timestampUpdated) {
        this.noteId = noteId;
        this.subject = subject;
        this.description = description;
        this.timestampCreated = timestampCreated;
        this.timestampUpdated = timestampUpdated;
    }

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

    public Instant getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Instant timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public Instant getTimestampUpdated() {
        return timestampUpdated;
    }

    public void setTimestampUpdated(Instant timestampUpdated) {
        this.timestampUpdated = timestampUpdated;
    }

    @PrePersist
    protected void onCreate() {
        this.timestampCreated = Instant.now();
        this.timestampUpdated = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.timestampUpdated = Instant.now();
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", timestampCreated=" + timestampCreated +
                ", timestampUpdated=" + timestampUpdated +
                '}';
    }
}