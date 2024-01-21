package com.example.hanjia.controller;

import com.example.hanjia.entity.Comment;
import com.example.hanjia.repository.CommentRepository;
import com.example.hanjia.servce.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/save")
    public ResponseEntity<Comment> saveComment(@RequestBody CommentRequest commentRequest) {
        Comment comment = commentService.saveComment(commentRequest.getContent());
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }

    public static class CommentRequest {
        private String content;
        private LocalDateTime time;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public LocalDateTime getTime() {
            return time;
        }

        public void setTime(LocalDateTime time) {
            this.time = time;
        }
    }

}
