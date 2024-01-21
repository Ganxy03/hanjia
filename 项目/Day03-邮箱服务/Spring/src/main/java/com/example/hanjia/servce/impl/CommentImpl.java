package com.example.hanjia.servce.impl;

import com.example.hanjia.entity.Comment;
import com.example.hanjia.repository.CommentRepository;
import com.example.hanjia.servce.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentImpl(CommentRepository commentRepository) {
        this.commentRepository =commentRepository;
    }

    @Override
    public Comment saveComment(String content) {
        Comment comment =new Comment();
        comment.setContent(content);
        comment.setTime(LocalDateTime.now());
        return commentRepository.save(comment);
    }
}
