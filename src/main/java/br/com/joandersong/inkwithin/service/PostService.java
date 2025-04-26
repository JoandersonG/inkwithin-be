package br.com.joandersong.inkwithin.service;

import br.com.joandersong.inkwithin.dto.PostRequestDTO;
import br.com.joandersong.inkwithin.model.Post;
import br.com.joandersong.inkwithin.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public long getTotal() {
        return postRepository.count();
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post create(@RequestBody PostRequestDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        return postRepository.save(post);
    }

    public Post updateById(long id, PostRequestDTO postRequestDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No post found with id: " + id));

        post.setTitle(postRequestDTO.getTitle());
        post.setContent(postRequestDTO.getContent());

        return postRepository.save(post);
    }

    public void deleteById(long id) {
        postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No post found with id: " + id));

        postRepository.deleteById(id);
    }
}
