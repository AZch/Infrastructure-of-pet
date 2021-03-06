package com.wcreators.todo_api.controller_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcreators.jwt_starter.services.jwt.JwtFilter;
import com.wcreators.jwt_starter.services.jwt.JwtProvider;
import com.wcreators.todo_api.services.security.details.CustomUserDetailsServiceByRepository;
import com.wcreators.todo_api.constants.Roles;
import com.wcreators.todo_api.constants.Routes;
import com.wcreators.todo_api.controllers.assemblers.CollectionAssembler;
import com.wcreators.todo_api.controllers.assemblers.ModelAssembler;
import com.wcreators.todo_api.controllers.NoteController;
import com.wcreators.todo_api.controllers.mappers.NoteMapper;
import com.wcreators.todo_api.dto.NoteRequestDto;
import com.wcreators.todo_api.entities.Note;
import com.wcreators.todo_api.entities.Role;
import com.wcreators.todo_api.entities.User;
import com.wcreators.todo_api.services.note.NoteService;
import com.wcreators.todo_api.services.user.UserService;
import com.wcreators.todo_api.services.user.UserServiceByRepository;
import org.junit.Test;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.wcreators.jwt_starter.services.jwt.JwtFilter.AUTHORIZATION;
import static com.wcreators.jwt_starter.services.jwt.JwtFilter.TOKEN_START_WITH;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
@Import({
        ModelAssembler.class,
        CollectionAssembler.class,
        NoteMapper.class,
        JwtFilter.class,
        JwtProvider.class,
        CustomUserDetailsServiceByRepository.class,
        UserServiceByRepository.class
})
public class NoteTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @MockBean
    private UserService userService;

    @Nested
    class GetAll {
        @Test
        public void shouldFindNoNotesIfRepositoryIsEmpty() throws Exception {
            prepareUserToTesting();
            when(noteService.allForUser(1L)).thenReturn(new ArrayList<>());

            mockMvc
                    .perform(get(Routes.Notes.BASE).header(AUTHORIZATION, String.format("%s%s", TOKEN_START_WITH, "")))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._embedded").doesNotExist());
        }

        @Test
        public void shouldReturnTwoNote() throws Exception {
            prepareUserToTesting();
            List<Note> noteList = Arrays.asList(
                    Note.builder().id(1L).title("1T").content("1C").build(),
                    Note.builder().id(2L).title("2T").content("2C").build()
            );
            when(noteService.allForUser(1L)).thenReturn(noteList);

            mockMvc
                    .perform(get(Routes.Notes.BASE).header(AUTHORIZATION, String.format("%s%s", TOKEN_START_WITH, "")))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._embedded.notes[*]").isArray())
                    .andExpect(jsonPath("$._embedded.notes[*]", hasSize(2)))
                    .andExpect(jsonPath("$._embedded.notes[0].id").value(noteList.get(0).getId()))
                    .andExpect(jsonPath("$._embedded.notes[0].title").value(noteList.get(0).getTitle()))
                    .andExpect(jsonPath("$._embedded.notes[0].content").value(noteList.get(0).getContent()))
                    .andExpect(jsonPath("$._embedded.notes[1].id").value(noteList.get(1).getId()))
                    .andExpect(jsonPath("$._embedded.notes[1].title").value(noteList.get(1).getTitle()))
                    .andExpect(jsonPath("$._embedded.notes[1].content").value(noteList.get(1).getContent()));
        }
    }

    @Nested
    class GetOne {
        @Test
        public void shouldReturnNote() throws Exception {
            prepareUserToTesting();
            Note note = Note.builder()
                    .id(1L)
                    .title("title")
                    .content("content")
                    .build();
            when(noteService.getByIdForUser(note.getId(), 1L)).thenReturn(Optional.of(note));

            mockMvc
                    .perform(get(String.format("%s/1", Routes.Notes.BASE)).header(AUTHORIZATION, String.format("%s%s", TOKEN_START_WITH, "")))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(note.getId()))
                    .andExpect(jsonPath("$.title").value(note.getTitle()))
                    .andExpect(jsonPath("$.content").value(note.getContent()));
        }
    }

    @Nested
    class Create {
        @Test
        public void shouldCreateNote() throws Exception {
            prepareUserToTesting();
            NoteRequestDto actualNote = NoteRequestDto.builder()
                    .title("title")
                    .content("content")
                    .build();

            Note expectedNote = Note.builder()
                    .id(1L)
                    .title(actualNote.getTitle())
                    .content(actualNote.getContent())
                    .build();

            when(noteService.save(
                    Note.builder()
                            .title(actualNote.getTitle())
                            .content(actualNote.getContent())
                            .build())
            ).thenReturn(expectedNote);

            mockMvc
                    .perform(
                            post(Routes.Notes.BASE)
                                    .header(AUTHORIZATION, String.format("%s%s", TOKEN_START_WITH, ""))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(new ObjectMapper().writeValueAsString(actualNote))
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(expectedNote.getId()))
                    .andExpect(jsonPath("$.title").value(expectedNote.getTitle()))
                    .andExpect(jsonPath("$.content").value(expectedNote.getContent()));
        }
    }

    @Nested
    class Edit {
        @Test
        public void shouldEditNote() throws Exception {
            prepareUserToTesting();
            Note note = Note.builder()
                    .id(1L)
                    .title("title")
                    .content("content")
                    .build();
            NoteRequestDto noteDto = NoteRequestDto.builder()
                    .title("new title")
                    .content(note.getContent())
                    .build();

            Note expectedNote = Note.builder()
                    .id(note.getId())
                    .title(noteDto.getTitle())
                    .content(note.getContent())
                    .build();

            when(noteService.getByIdForUser(note.getId(), 1L)).thenReturn(Optional.of(note));
            when(noteService.save(note.withTitle("new title"))).thenReturn(expectedNote);

            mockMvc
                    .perform(
                            put(String.format("%s/1", Routes.Notes.BASE))
                                    .header(AUTHORIZATION, String.format("%s%s", TOKEN_START_WITH, ""))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(new ObjectMapper().writeValueAsString(noteDto))
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(expectedNote.getId()))
                    .andExpect(jsonPath("$.title").value(expectedNote.getTitle()))
                    .andExpect(jsonPath("$.content").value(expectedNote.getContent()));
        }
    }

    @Nested
    class Delete {
        @Test
        public void shouldDeleteNote() throws Exception {
            prepareUserToTesting();
            Note note = Note.builder().id(1L).title("").content("").build();

            when(noteService.getByIdForUser(note.getId(), 1L)).thenReturn(Optional.of(note));
            Note deletedNote = note.withDeleted(true);
            when(noteService.save(deletedNote)).thenReturn(deletedNote);

            mockMvc
                    .perform(delete(String.format("%s/1", Routes.Notes.BASE)).header(AUTHORIZATION, String.format("%s%s", TOKEN_START_WITH, "")))
                    .andExpect(status().isNoContent());
        }
    }

    private void prepareUserToTesting() {
        when(userService.getByIdAndRole(1L, "USER")).thenReturn(
                Optional.of(
                        User.builder()
                                .id(1L)
                                .role(
                                        Role.builder()
                                                .id(1L)
                                                .name(Roles.USER.getName())
                                                .build()
                                )
                                .build()
                )
        );
    }
}

