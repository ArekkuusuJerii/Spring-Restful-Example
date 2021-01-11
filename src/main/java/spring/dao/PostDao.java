package spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.entity.Post;

@Repository
public interface PostDao extends JpaRepository<Post, Long> {

}
