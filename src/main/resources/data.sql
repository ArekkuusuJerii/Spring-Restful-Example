DROP TABLE IF EXISTS posts;

CREATE TABLE posts (
  post_id BIGINT AUTO_INCREMENT PRIMARY Key,
  title VARCHAR(250) NOT NULL,
  body VARCHAR(500) NOT NULL,
  date_created TIMESTAMP WITH TIME ZONE NOT NULL
);

INSERT INTO posts (title, body, date_created) VALUES 
('Example post', 'With a description', CURRENT_TIMESTAMP);