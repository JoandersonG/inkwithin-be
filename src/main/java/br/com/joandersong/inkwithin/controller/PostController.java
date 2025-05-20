package br.com.joandersong.inkwithin.controller;

import br.com.joandersong.inkwithin.dto.PostRequestDTO;
import br.com.joandersong.inkwithin.model.Post;
import br.com.joandersong.inkwithin.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/total")
    public long getTotal() {
        return postService.getTotal();
    }

    @GetMapping
    public List<Post> getAll() {
        return postService.getAll();
    }

    @PostMapping("/create")
    public Post create(@RequestBody PostRequestDTO postRequestDTO) {
        return postService.create(postRequestDTO);
    }

    @PatchMapping("/update/{id}")
    public Post update(@PathVariable Long id, @RequestBody PostRequestDTO postRequestDTO) {
        return postService.updateById(id, postRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
