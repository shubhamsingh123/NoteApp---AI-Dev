package com.telus.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telus.demo.modal.Note;
import com.telus.demo.service.NotesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class NotesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotesService notesService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new NotesController(notesService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddNote() throws Exception {
        // Given
        Note note = Note.builder()
                .noteId(1L)
                .subject("Test Subject")
                .description("Test Description")
                .build();

        // When
        when(notesService.addNote(any(Note.class))).thenReturn(note);

        // Then
        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(note)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.subject").value("Test Subject"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void testGetNoteById() throws Exception {
        // Given
        Note note = Note.builder()
                .noteId(1L)
                .subject("Test Subject")
                .description("Test Description")
                .build();

        // When
        when(notesService.getNoteById(any(Long.class))).thenReturn(note);

        // Then
        mockMvc.perform(get("/api/notes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value("Test Subject"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void testGetAllNotes() throws Exception {
        // Given
        List<Note> notes = new ArrayList<>();
        notes.add(Note.builder()
                .noteId(1L)
                .subject("Test Subject 1")
                .description("Test Description 1")
                .build());
        notes.add(Note.builder()
                .noteId(2L)
                .subject("Test Subject 2")
                .description("Test Description 2")
                .build());

        // When
        when(notesService.getAllNotes()).thenReturn(notes);

        // Then
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testUpdateNote() throws Exception {
        // Given
        Note note = Note.builder()
                .noteId(1L)
                .subject("Test Subject")
                .description("Test Description")
                .build();

        // When
        when(notesService.modifyNote(anyObject(), any(Note.class))).thenReturn(note);

        // Then
        mockMvc.perform(put("/api/notes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(note)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value("Test Subject"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void testDeleteNote() throws Exception {
        // When
        doNothing().when(notesService).deleteNote(any(Long.class));

        // Then
        mockMvc.perform(delete("/api/notes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testSearchNotesBySubject() throws Exception {
        Note note = Note.builder()
                .noteId(1L)
                .subject("Test Subject")
                .description("Test Description")
                .build();

        when(notesService.searchNotesBySubject(anyString())).thenReturn(List.of(note));

        mockMvc.perform(get("/api/notes/search?subject=Test")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testCountTotalNotes() throws Exception {
        List<Note> listNotes = new ArrayList<>();

        listNotes.add(Note.builder().build());
        listNotes.add(Note.builder().build());
        listNotes.add(Note.builder().build());

        when(notesService.countTotalNotes()).thenReturn((long) (listNotes.size()));

        mockMvc.perform(get("/api/notes/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3L));
    }

    @Test
    void testGetWordCount() throws Exception {
        Note note = Note.builder()
                .noteId(1L)
                .subject("Test Subject")
                .description("Description")
                .build();

        when(notesService.getWordCount(anyLong())).thenReturn(note.getDescription().length());

        mockMvc.perform(get("/api/notes/word-count/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(11));
    }

    private String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}