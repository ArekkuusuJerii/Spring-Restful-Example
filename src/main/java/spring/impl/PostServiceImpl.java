package spring.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import spring.dao.PostDao;
import spring.entity.Post;
import spring.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDao postDao;
	
	@Override
	public Post savePost(Post post) {
		return postDao.save(post);
	}

	@Override
	public List<Post> getAllPostsList() {
		return postDao.findAll(Sort.by(Sort.Direction.DESC, "dateCreated"));
	}

	@Override
	public Optional<Post> getPost(Long postId) {
		return postDao.findById(postId);
	}
}
