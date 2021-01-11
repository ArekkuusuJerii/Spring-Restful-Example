package spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import java.net.URL;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import spring.entity.Post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTests {

	@Autowired
	private TestRestTemplate template;

	@LocalServerPort
	private int port;
	private URL base;
	
	@BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/post");
    }
	
	public String makeURI(String path) {
		return String.format("%s%s", this.base.toString(), path);
	}
	
	@Test
	public void saveValidPostShouldSuceedWithSamePost() throws Exception {
		Post post = new Post();
		post.setTitle("test title");
		post.setBody("test body");
		post.setDateCreated(ZonedDateTime.now());
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Post> request = new HttpEntity<>(post, headers);
        
        ResponseEntity<Post> response = this.template.postForEntity(makeURI("/save"), request, Post.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).extracting(Post::getTitle).isNotNull().isEqualTo(post.getTitle());
        assertThat(response.getBody()).extracting(Post::getBody).isNotNull().isEqualTo(post.getBody());
        assertThat(response.getBody()).extracting(Post::getDateCreated).isNotNull().matches(d -> post.getDateCreated().isEqual(d));
	}
	
	@Test
	public void saveEmptyPostShouldFailWithEmptyPost() throws Exception {
		Post post = new Post();
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Post> request = new HttpEntity<>(post, headers);
        
        ResponseEntity<Post> response = this.template.postForEntity(makeURI("/save"), request, Post.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).extracting(Post::getTitle).isNull();
        assertThat(response.getBody()).extracting(Post::getBody).isNull();
        assertThat(response.getBody()).extracting(Post::getDateCreated).isNull();
	}
	
	@Test
	public void getPostsShouldSuceedWithMoreThanOnePost() throws Exception {
		ResponseEntity<Post[]> response = this.template.getForEntity(makeURI("/all"), Post[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
        assertThat(response.getBody()).doesNotContainNull();
        assertThat(response.getBody()).hasSizeGreaterThan(0);
	}
	
	@Test
	public void getPostByIdShouldSuceedWithNonnullPost() throws Exception {
		ResponseEntity<Post> response = this.template.getForEntity(makeURI("/by/1"), Post.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().extracting(Post::getPostId).isEqualTo(1L);
	}

	@Test
	public void getPostByInvalidIdShouldSuceedWithNullablePost() throws Exception {
		ResponseEntity<Post> response = this.template.getForEntity(makeURI("/by/-1"), Post.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
	}
}
