package spring.service;

import java.util.List;
import java.util.Optional;

import spring.entity.Post;

public interface PostService {
	
	Post savePost(Post post);
	
	List<Post> getAllPostsList();
	
	Optional<Post> getPost(Long postId);
}
