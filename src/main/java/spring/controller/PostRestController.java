package spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.entity.Post;
import spring.service.PostService;

@RequestMapping("post")
@RestController
public class PostRestController {

	@Autowired
	private PostService postService;
	
	@PostMapping("/save")
	public Post save(@RequestBody Post post) {	
		return postService.savePost(post);
	}
	
	@GetMapping("/all")
	public List<Post> getAll() {
		return postService.getAllPostsList();
	}
	
	@GetMapping("/by/{postId}")
	public ResponseEntity<Post> getPost(@PathVariable(name = "postId") Long postId) {
		Optional<Post> optionalPostResult = postService.getPost(postId);
		return optionalPostResult
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.ok().build());
	}
}
