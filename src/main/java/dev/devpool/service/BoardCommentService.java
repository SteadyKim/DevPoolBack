package dev.devpool.service;

import dev.devpool.domain.Board;
import dev.devpool.domain.BoardComment;
import dev.devpool.domain.Member;
import dev.devpool.dto.BoardCommentDto;
import dev.devpool.dto.BoardDto;
import dev.devpool.dto.common.CommonResponseDto;
import dev.devpool.exception.CustomEntityNotFoundException;
import dev.devpool.repository.BoardCommentRepository;
import dev.devpool.repository.BoardRepository;
import dev.devpool.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardCommentService {
    private final MemberRepository memberRepository;
    private final BoardCommentRepository boardCommentRepository;

    private final BoardRepository boardRepository;

    public List<BoardCommentDto.Response> findAllByBoardId(Long boardId) {
        List<BoardCommentDto.Response> boardCommentDtoList = boardCommentRepository.findAllByBoardId(boardId)
                .stream()
                .map(boardComment -> {
                    String memberNickName = boardComment.getMember().getNickName();

                    return BoardCommentDto.Response.builder()
                            .boardCommentId(boardComment.getId())
                            .boardId(boardId)
                            .memberNickName(memberNickName)
                            .createTime(boardComment.getCreateDate())
                            .content(boardComment.getContent())
                            .build();
                })
                .sorted(Comparator.comparing(BoardCommentDto.Response::getCreateTime))
                .collect(Collectors.toList());

        return boardCommentDtoList;
    }

    @Transactional
    public CommonResponseDto<Object> deleteById(Long boardCommentId) {
        boardCommentRepository.deleteById(boardCommentId);

        return CommonResponseDto.builder()
                .status(200)
                .message("boardComment" + boardCommentId + " 삭제에 성공하였습니다.")
                .build();
    }

    @Transactional
    public CommonResponseDto<Object> join(BoardCommentDto.Save boardCommentDto) {
        Member findMember = memberRepository.findById(boardCommentDto.getMemberId())
                .orElseThrow(() -> new CustomEntityNotFoundException(Member.class.getName(), boardCommentDto.getMemberId()));

        Board findBoard = boardRepository.findById(boardCommentDto.getBoardId())
                .orElseThrow(() -> new CustomEntityNotFoundException(Board.class.getName(), boardCommentDto.getBoardId()));

        BoardComment boardComment = BoardComment.builder()
                .board(findBoard)
                .member(findMember)
                .content(boardCommentDto.getContent())
                .build();

        boardCommentRepository.save(boardComment);

        return CommonResponseDto.builder()
                .status(200)
                .message("boardComment 생성에 성공하였습니다.")
                .build();
    }

    @Transactional
    public CommonResponseDto<Object> update(BoardCommentDto.Update boardCommentDto) {
        BoardComment findBoarderComment = boardCommentRepository.findById(boardCommentDto.getBoardCommentId())
                .orElseThrow(() -> new CustomEntityNotFoundException(BoardComment.class.getName(), boardCommentDto.getBoardCommentId()));

        findBoarderComment.update(boardCommentDto);

        return CommonResponseDto.builder()
                .status(200)
                .message("boardComment " + boardCommentDto.getBoardCommentId() + " 수정에 성공하였습니다.")
                .build();
    }
}
