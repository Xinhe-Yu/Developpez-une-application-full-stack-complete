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

INSERT INTO `USERS` (username, email, password, created_at, updated_at) VALUES
('johndoe', 'john.doe@example.com', '$2y$10$1Qz3KvxLj5X8Yl2z9R7NueJf1r5gZ9X2Kj6L8m9O3pQ4W5R6T7Y8U', NOW(), NOW()),
('janedoe', 'jane.doe@example.com', '$2y$10$2Wx4MvNh6Y9Z5Rt8B3K2l.Fs1qP3Qj7L6m2N4O5R8T9U0V1S2X3Y4', NOW(), NOW()),
('bobsmith', 'bob.smith@example.com', '$2y$10$3Vw5NuMg7Z0Y1Sq9C4J3m.Gt2qR4Pj8K7n1M5O3R9T6U0W1S2X3Y4', NOW(), NOW()),
('alicejones', 'alice.jones@example.com', '$2y$10$4Ux6PvLh8Y0Z1Rq7B2I4l.Hs3sT5Qk9J6p2M4O5R8T9U0V1S2X3Y4', NOW(), NOW());
-- Seed for the topics
INSERT INTO `TOPICS` (name, description, created_at, updated_at) VALUES
('golang', 'Langage de programmation conçu par Google, Go offre une syntaxe simple, une compilation rapide et une gestion efficace de la concurrence. Idéal pour les systèmes distribués et le cloud computing.', NOW(), NOW()),
('typescript', 'Surensemble typé de JavaScript développé par Microsoft. TypeScript améliore la maintenabilité et la fiabilité du code, particulièrement adapté aux projets à grande échelle et aux applications web modernes.', NOW(), NOW()),
('python', 'Langage polyvalent reconnu pour sa simplicité et sa lisibilité. Python excelle en data science, IA, web et automatisation. Sa vaste bibliothèque standard et son écosystème riche en font un choix populaire.', NOW(), NOW()),
('java', 'Langage orienté objet largement utilisé, Java offre portabilité et sécurité. Prisé pour le développement d''applications d''entreprise, Android et les systèmes embarqués grâce à sa robustesse et sa scalabilité.', NOW(), NOW());

-- Seed for the article about Java
INSERT INTO `ARTICLES` (user_id, topic_id, title, content, created_at, updated_at)
VALUES (1, 4, 'Introduction to Java', 'Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible.', NOW(), NOW());
INSERT INTO `ARTICLES` (user_id, topic_id, title, content, created_at, updated_at) VALUES
(1, 1, 'Les avantages de Go pour le développement backend', 'Go, également connu sous le nom de Golang, offre des performances exceptionnelles et une gestion efficace de la concurrence, ce qui en fait un excellent choix pour le développement backend. Sa simplicité et sa rapidité de compilation accélèrent le cycle de développement.', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),
(2, 2, 'TypeScript : Améliorer la qualité du code JavaScript', 'TypeScript apporte un système de typage statique à JavaScript, permettant de détecter les erreurs plus tôt dans le processus de développement. Il améliore la maintenabilité du code et offre une meilleure expérience de développement, en particulier pour les grands projets.', DATE_SUB(NOW(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 12 DAY)),
(3, 3, 'Python dans la data science : un outil incontournable', 'Python s''est imposé comme le langage de prédilection en data science grâce à ses bibliothèques puissantes comme NumPy, Pandas et Scikit-learn. Sa syntaxe claire et sa grande communauté en font un choix évident pour l''analyse de données et le machine learning.', DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),
(1, 4, 'Java en 2025 : toujours d''actualité pour le développement d''entreprise', 'Malgré l''émergence de nouveaux langages, Java reste un pilier du développement d''entreprise en 2025. Sa portabilité, sa sécurité et son écosystème mature continuent d''en faire un choix privilégié pour les applications critiques et à grande échelle.', DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 14 DAY));

INSERT INTO COMMENTS (user_id, article_id, content, created_at, updated_at) VALUES
(2, 1, 'Excellent article sur Go ! J''apprécie particulièrement l''accent mis sur ses performances en backend. Avez-vous des exemples concrets de projets où Go a significativement amélioré les performances ?', DATE_SUB(NOW(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 9 DAY)),
(3, 1, 'Je suis d''accord sur les avantages de Go pour la concurrence. Comment le compareriez-vous à Node.js pour les applications nécessitant beaucoup d''I/O ?', DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),
(4, 1, 'Intéressant point de vue sur la simplicité de Go. En tant que développeur Java, je trouve la transition vers Go assez fluide. Quels sont selon vous les principaux défis pour un développeur Java passant à Go ?', DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY));
