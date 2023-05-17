package dev.devpool.service;

import dev.devpool.domain.Comment;
import dev.devpool.dto.CommentDto;
import dev.devpool.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public CommentDto.Response findById(Long commentId) {
        Comment findComment = commentRepository.findById(commentId);
        Comment parent = findComment.getParent();


        List<CommentDto.Response> childDtoList = new ArrayList<>();
        // 첫 댓글인지 대댓글인지 체크
        if( parent == null ) { // 첫 댓글인 경우
            Long parentId = findComment.getId();
            childDtoList = findChildByParentId(parentId);
        }

        CommentDto.Response commentDto = CommentDto.Response.builder()
                .commentId(findComment.getId())
                .content(findComment.getContent())
                .createTime(findComment.getCreateDate())
                .nickName(findComment.getMember().getNickName())
                .replies(childDtoList)
                .build();

        return commentDto;
    }

    public List<Comment> findAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments;
    }

    public List<CommentDto.Response> findChildByParentId(Long parentId) {
        Comment parentComment = commentRepository.findById(parentId);

        List<CommentDto.Response> commentDtoList = commentRepository.findChildByParentId(parentId)
                .stream()
                .map(comment -> CommentDto.Response.builder()
                        .teamId(parentComment.getId())
                        .commentId(comment.getId())
                        .content(comment.getContent())
                        .createTime(comment.getCreateDate())
                        .nickName(comment.getMember().getNickName())
                        .build())
                .collect(Collectors.toList());

        return commentDtoList;
    }

    public List<CommentDto.Response> findAllParentByTeamId(Long teamId) {
        List<Comment> commentList = commentRepository.findAllParentCommentByTeamId(teamId);

        List<CommentDto.Response> commentDtoList = commentList.stream()
                .map(comment -> CommentDto.Response.builder()
                        .teamId(teamId)
                        .nickName(comment.getMember().getNickName())
                        .commentId(comment.getId())
                        .content(comment.getContent())
                        .createTime(comment.getCreateDate())
                        .replies(findChildByParentId(comment.getId()))
                        .build())
                .collect(Collectors.toList());

        return commentDtoList;
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
