package br.com.joandersong.inkwithin.controller;

import br.com.joandersong.inkwithin.dto.PostRequestDTO;
import br.com.joandersong.inkwithin.model.Post;
import br.com.joandersong.inkwithin.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Test
    void shouldReturnTotalPosts() throws Exception {
        Mockito.when(postService.getTotal()).thenReturn(5L);

        mockMvc.perform(get("/posts/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void shouldReturnAllPosts() throws Exception {
        Post post1 = new Post(1L, "Title1", "Content1");
        Post post2 = new Post(2L, "Title2", "Content2");

        Mockito.when(postService.getAll()).thenReturn(List.of(post1, post2));

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Title1"))
                .andExpect(jsonPath("$[0].content").value("Content1"))
                .andExpect(jsonPath("$[1].title").value("Title2"))
                .andExpect(jsonPath("$[1].content").value("Content2"));
    }

    @Test
    void shouldCreatePost() throws Exception {
        PostRequestDTO requestDTO = new PostRequestDTO("New Title", "New Content");
        Post createdPost = new Post(1L, "New Title", "New Content");

        Mockito.when(postService.create(requestDTO)).thenReturn(createdPost);

        mockMvc.perform(post("/posts/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"New Title\",\"content\":\"New Content\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.content").value("New Content"));
    }

    @Test
    void shouldUpdatePost() throws Exception {
        PostRequestDTO requestDTO = new PostRequestDTO("Updated Title", "Updated Content");
        Post updatedPost = new Post(1L, "Updated Title", "Updated Content");

        Mockito.when(postService.updateById(Mockito.eq(1L), Mockito.eq(requestDTO))).thenReturn(updatedPost);

        mockMvc.perform(patch("/posts/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Title\",\"content\":\"Updated Content\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.content").value("Updated Content"));
    }

    @Test
    void shouldDeletePost() throws Exception {
        Mockito.doNothing().when(postService).deleteById(1L);

        mockMvc.perform(delete("/posts/delete/1"))
                .andExpect(status().isNoContent());
    }
}
