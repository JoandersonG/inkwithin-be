package br.com.joandersong.inkwithin.repo;

import br.com.joandersong.inkwithin.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PostRepository extends JpaRepository<Post, Long> {
}
