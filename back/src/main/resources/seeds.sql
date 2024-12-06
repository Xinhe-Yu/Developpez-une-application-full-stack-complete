USE mdd;

DELETE FROM `COMMENTS`;
DELETE FROM `ARTICLES`;
DELETE FROM `SUBS`;
DELETE FROM `USERS`;
DELETE FROM `TOPICS`;

ALTER TABLE `COMMENTS` AUTO_INCREMENT = 1;
ALTER TABLE `ARTICLES` AUTO_INCREMENT = 1;
ALTER TABLE `SUBS` AUTO_INCREMENT = 1;
ALTER TABLE `USERS` AUTO_INCREMENT = 1;
ALTER TABLE `TOPICS` AUTO_INCREMENT = 1;

-- Seed for the user instance
INSERT INTO `USERS` (username, email, password, created_at, updated_at)
VALUES ('admin', 'admin@test.fr', '$2y$10$kp1V7UYDEWn17WSK16UcmOnFd1mPFVF6UkLrOOCGtf24HOYt8p1iC', NOW(), NOW());

-- Seed for the topics
INSERT INTO `TOPICS` (name, created_at, updated_at) VALUES
('golang', NOW(), NOW()),
('typescript', NOW(), NOW()),
('python', NOW(), NOW()),
('java', NOW(), NOW());

-- Seed for the article about Java
INSERT INTO `ARTICLES` (user_id, topic_id, title, content, created_at, updated_at)
VALUES (1, 4, 'Introduction to Java', 'Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible.', NOW(), NOW());
