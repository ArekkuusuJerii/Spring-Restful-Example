package spring;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import spring.dao.PostDao;
import spring.entity.Post;
import spring.service.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTests {

	@Autowired
	private PostService postService;
	
	@MockBean
	private PostDao postDao;
	
	@Test
	public void getAllPostsListShouldReturnSizeOfOneWithOnePost() {
		when(postDao.findAll(Sort.by(Sort.Direction.DESC, "dateCreated"))).thenReturn(Stream.of(new Post("Mockito", "Testing", ZonedDateTime.now())).collect(Collectors.toList()));
		assertEquals(1, postService.getAllPostsList().size());
	}
	
	@Test
	public void findByIdShouldReturnPost() {
		when(postDao.findById(1L)).thenReturn(Optional.of(new Post("Mockito", "Testing", ZonedDateTime.now())));
		assertTrue(postService.getPost(1L).isPresent());
	}
	
	@Test
	public void savePostShouldReturnSamePost() {
		Post post = new Post("Mockito", "Testing", ZonedDateTime.now());
		when(postDao.save(post)).thenReturn(post);
		assertEquals(post, postService.savePost(post));
	}
}
