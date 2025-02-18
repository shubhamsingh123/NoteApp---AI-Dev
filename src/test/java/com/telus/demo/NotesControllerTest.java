package com.telus.demo;

import com.telus.demo.dao.NotesRepository;
import com.telus.demo.modal.Note;
import com.telus.demo.service.NotesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class NoteServiceTest {

    @Mock
    private NotesRepository noteRepository;

    @InjectMocks
    private NotesService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNote() {
        Note note = Note.builder()
                .noteId(1L)
                .subject("Test")
                .description("description")
                .build();

//                new Note(1L, "Test", "Description", null, null);
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        Note createdNote = noteService.addNote(note);
        assertNotNull(createdNote);
        assertEquals("Test", createdNote.getSubject());
    }

    @Test
    void testGetNoteById() {
        Note note = Note.builder()
                .noteId(1L)
                .subject("test")
                .description("description")
                .build();
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        Optional<Note> foundNote = Optional.ofNullable(noteService.getNoteById(1L));
        assertTrue(foundNote.isPresent());
        assertEquals("test", foundNote.get().getSubject());
    }
}

//@WebMvcTest(NotesController.class)
//public class NotesControllerTest {
//
//    @MockBean
//    private NotesService notesService;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        // Initialize MockMvc and inject the mocked service
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(new NotesController(notesService)).build();
//    }
//
//    @Test
//    void testAddNote() throws Exception {
//        // Given
//        Note note = new Note();
//        note.setSubject("Test Subject");
//        note.setDescription("Test Description");
//
//        when(notesService.addNote(any(Note.class))).thenReturn(note);
//
//        // When & Then
//        mockMvc.perform(post("/api/notes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"subject\":\"Test Subject\",\"description\":\"Test Description\"}"))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.subject").value("Test Subject"))
//                .andExpect(jsonPath("$.description").value("Test Description"));
//    }
//
//    @Test
//    void testModifyNote() throws Exception {
//        // Given
//        Long noteId = 1L;
//        Note existingNote = new Note();
//        existingNote.setNoteId(noteId);
//        existingNote.setSubject("Old Subject");
//        existingNote.setDescription("Old Description");
//
//        Note updatedNote = new Note();
//        updatedNote.setSubject("Updated Subject");
//        updatedNote.setDescription("Updated Description");
//
//        when(notesService.modifyNote(eq(noteId), any(Note.class))).thenReturn(updatedNote);
//
//        // When & Then
//        mockMvc.perform(put("/api/notes/{id}", noteId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"subject\":\"Updated Subject\",\"description\":\"Updated Description\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.subject").value("Updated Subject"))
//                .andExpect(jsonPath("$.description").value("Updated Description"));
//    }
//
//    @Test
//    void testDeleteNote() throws Exception {
//        // Given
//        Long noteId = 1L;
//        doNothing().when(notesService).deleteNote(noteId);
//
//        // When & Then
//        mockMvc.perform(delete("/api/notes/{id}", noteId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.deleted").value(true));
//    }
//
//    @Test
//    void testSearchNotesBySubject() throws Exception {
//        // Given
//        List<Note> notes = Arrays.asList(
//                Note.builder()
//                        .noteId(1L)
//                        .subject("Test subject 1")
//                        .description("Description 1")
//                        .build(),
//                Note.builder()
//                        .noteId(2L)
//                        .subject("Test subject 2")
//                        .description("Description 2")
//                        .build()
//        );
//
//        when(notesService.searchNotesBySubject("Test")).thenReturn(notes);
//
//        // When & Then
//        mockMvc.perform(get("/api/notes/search?subject=Test"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].subject").value("Test Subject 1"))
//                .andExpect(jsonPath("$[1].subject").value("Test Subject 2"));
//    }
//
//    @Test
//    void testGetNoteById() throws Exception {
//        // Given
//        Long noteId = 1L;
//        Note note = new Note();
//        note.setNoteId(noteId);
//        note.setSubject("Test Subject");
//
//        when(notesService.getNoteById(noteId)).thenReturn(note);
//
//        // When & Then
//        mockMvc.perform(get("/api/notes/{id}", noteId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.subject").value("Test Subject"));
//    }
//
//    @Test
//    void testCountTotalNotes() throws Exception {
//        // Given
//        when(notesService.countTotalNotes()).thenReturn(5L);
//
//        // When & Then
//        mockMvc.perform(get("/api/notes/count"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(5));
//    }
//
//    @Test
//    void testGetWordCount() throws Exception {
//        // Given
//        Long noteId = 1L;
//        when(notesService.getWordCount(noteId)).thenReturn(100);
//
//        // When & Then
//        mockMvc.perform(get("/api/notes/word-count/{id}", noteId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(100));
//    }
//
//    @Test
//    void testGetAverageNoteLength() throws Exception {
//        // Given
//        when(notesService.getAverageNoteLength()).thenReturn(50.0);
//
//        // When & Then
//        mockMvc.perform(get("/api/notes/average-length"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(50.0));
//    }
//
//    @Test
//    void testLikeNote() throws Exception {
//        // Given
//        Long noteId = 1L;
//        Note note = new Note();
//        note.setNoteId(noteId);
//        note.setLikes(1);
//
//        when(notesService.likeNote(noteId)).thenReturn(note);
//
//        // When & Then
//        mockMvc.perform(post("/api/notes/{id}/like", noteId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.likes").value(1));
//    }
//
//    @Test
//    void testUnlikeNote() throws Exception {
//        // Given
//        Long noteId = 1L;
//        Note note = new Note();
//        note.setNoteId(noteId);
//        note.setLikes(0);
//
//        when(notesService.unlikeNote(noteId)).thenReturn(note);
//
//        // When & Then
//        mockMvc.perform(delete("/api/notes/{id}/unlike", noteId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.likes").value(0));
//    }
//
//    @Test
//    void testGetLikedNotes() throws Exception {
//        // Given
//        List<Note> likedNotes = List.of(
//                Note.builder()
//                        .noteId(1L)
//                        .subject("Liked Note")
//                        .description("Description")
//                        .likes(5)
//                        .build()
//        );
//        when(notesService.getLikedNotes()).thenReturn(likedNotes);
//
//        // When & Then
//        mockMvc.perform(get("/api/notes/liked"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].likes").value(5));
//    }
//}
