package ru.practicum.explorewithme.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.comments.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
