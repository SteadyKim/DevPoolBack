package dev.devpool.service;

import dev.devpool.domain.Comment;
import dev.devpool.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;


    // 저장
    @Transactional
    public Long join(Comment comment) {
        commentRepository.save(comment);

        return comment.getId();
    }

    // 조회
    public Comment findById(Long commentId) {
        Comment findComment = commentRepository.findById(commentId);
        return findComment;
    }

    public List<Comment> findAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments;
    }

    public List<Comment> findChildByParentId(Long parentId) {
        List<Comment> childComments = commentRepository.findChildByParentId(parentId);
        return childComments;
    }

    public List<Comment> findAllParentByTeamId(Long teamId) {
        List<Comment> commentList = commentRepository.findAllParentCommentByTeamId(teamId);
        return commentList;
    }

    // 삭제
    @Transactional
    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void deleteChildByParentId(Long parentId) {
        commentRepository.deleteChildByParentId(parentId);
    }

    @Transactional
    public void deleteAllByTeamId(Long teamId) {
        commentRepository.deleteAllByTeamId(teamId);
    }

    @Transactional
    public void deleteAll() {
        commentRepository.deleteAll();
    }

    // 수정
    @Transactional
    public void update(Long commentId, Comment newComment) {
        commentRepository.update(commentId, newComment);
    }
}
