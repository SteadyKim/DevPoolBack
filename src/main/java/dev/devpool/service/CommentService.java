package dev.devpool.service;

import dev.devpool.domain.Comment;
import dev.devpool.domain.Member;
import dev.devpool.domain.Team;
import dev.devpool.dto.CommentDto;
import dev.devpool.dto.common.CommonResponseDto;
import dev.devpool.exception.CustomEntityNotFoundException;
import dev.devpool.exception.CustomException;
import dev.devpool.repository.CommentRepository;
import dev.devpool.repository.MemberRepository;
import dev.devpool.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    // 저장
    @Transactional
    public CommonResponseDto<Object> join(CommentDto.Save commentSaveDto) {
        Team findTeam = teamRepository.findOneById(commentSaveDto.getTeamId());
        Member findMember = memberRepository.findById(commentSaveDto.getMemberId())
                .orElseThrow(() -> new CustomEntityNotFoundException(Member.class.getName(), commentSaveDto.getMemberId()));

        Comment comment = Comment.builder()
                .team(findTeam)
                .member(findMember)
                .content(commentSaveDto.getContent())
                .build();

        commentRepository.save(comment);

        return CommonResponseDto.builder()
                .message("팀 댓글 저장에 성공하였습니다.")
                .build();
    }

    @Transactional
    public CommonResponseDto<Object> joinReply(CommentDto.SaveReply commentSaveReplyDto) {
        Team findTeam = teamRepository.findOneById(commentSaveReplyDto.getTeamId());
        Member findMember = memberRepository.findById(commentSaveReplyDto.getMemberId())
                .orElseThrow(() -> new CustomEntityNotFoundException(Member.class.getName(), commentSaveReplyDto.getMemberId()));
        Comment parentComment = commentRepository.findById(commentSaveReplyDto.getParentId())
                .orElse(null);

        Comment comment = Comment.builder()
                .team(findTeam)
                .member(findMember)
                .parent(parentComment)
                .content(commentSaveReplyDto.getContent())
                .build();


        commentRepository.save(comment);


        return CommonResponseDto.builder()
                .message("팀 대댓글 저장에 성공하였습니다.")
                .build();

    }

    // 조회
    public CommentDto.Response findById(Long commentId) {
        Comment findComment = commentRepository.findById(commentId)
                .orElse(null);
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
        List<CommentDto.Response> commentDtoList;

        Comment parentComment = commentRepository.findById(parentId)
                .orElse(null);
        List<Comment> commentList = commentRepository.findChildByParentId(parentId);

        if(commentList == null) {
            commentDtoList = null;
        } else {
            commentDtoList = commentList
                .stream()
                .map(comment -> CommentDto.Response.builder()
                        .teamId(parentComment.getId())
                        .commentId(comment.getId())
                        .content(comment.getContent())
                        .createTime(comment.getCreateDate())
                        .nickName(comment.getMember().getNickName())
                        .build())
                .collect(Collectors.toList());
        }

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

        commentRepository.deleteCustomById(commentId);
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
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException("DB에 comment가 없습니다.", CommentService.class.getName(), "update()"));

        comment.updateComment(newComment);
    }
}
