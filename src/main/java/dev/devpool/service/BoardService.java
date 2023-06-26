package dev.devpool.service;

import dev.devpool.domain.Board;
import dev.devpool.domain.Member;
import dev.devpool.dto.BoardDto;
import dev.devpool.dto.common.CommonResponseDto;
import dev.devpool.exception.CustomEntityNotFoundException;
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
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardCommentService boardCommentService;

    // 저장
    @Transactional
    public CommonResponseDto<Object> join(BoardDto.Save boardDto) {
        Member findMember = memberRepository.findById(boardDto.getMemberId())
                .orElseThrow(() -> new CustomEntityNotFoundException(Member.class.getName(), boardDto.getMemberId()));

        Board board = Board.builder()
                .content(boardDto.getContent())
                .title(boardDto.getTitle())
                .member(findMember)
                .build();
        boardRepository.save(board);

        return CommonResponseDto.builder()
                .status(200)
                .message("board 저장에 성공하였습니다.")
                .id(board.getId())
                .build();
    }

    // 조회
    public List<BoardDto.Response> findAll() {
        return boardRepository.findAll()
                .stream()
                .map(board ->{
                    return getBoardDto(board);
                        }
                )
                .sorted(Comparator.comparing(BoardDto.Response::getCreateTime))
                .collect(Collectors.toList());
    }

    public Board findById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomEntityNotFoundException(Board.class.getName(), boardId));
    }

    public List<BoardDto.Response> findAllByMemberId(Long memberId) {
        return boardRepository.findAllByMemberId(memberId)
                .stream()
                .map(board -> getBoardDto(board))
                .sorted(Comparator.comparing(BoardDto.Response::getCreateTime))
                .collect(Collectors.toList());
    }

    private BoardDto.Response getBoardDto(Board board) {
        return BoardDto.Response.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .memberNickName(board.getMember().getNickName())
                .content(board.getContent())
                .commentList(boardCommentService.findAllByBoardId(board.getId()))
                .createTime(board.getCreateDate())
                .build();
    }

    // 삭제
    @Transactional
    public CommonResponseDto<Object> deleteById(Long boardId) {
        boardRepository.deleteById(boardId);

        return CommonResponseDto.builder()
                .status(200)
                .message("board" + boardId + " 삭제에 성공하였습니다.")
                .build();
    }

    @Transactional
    public CommonResponseDto<Object> deleteAllByMemberId(Long memberId) {
        boardRepository.findAllByMemberId(memberId)
                .stream()
                .forEach(board -> boardRepository.deleteById(board.getId()));

        return CommonResponseDto.builder()
                .status(200)
                .message("board 삭제 by memberId에 성공하였습니다.")
                .build();
    }

    // 수정
    @Transactional
    public CommonResponseDto<Object> update(BoardDto.Update boardDto) {

        Board findBoard = boardRepository.findById(boardDto.getBoardId())
                .orElseThrow(() -> new CustomEntityNotFoundException(Board.class.getName(), boardDto.getBoardId()));

        findBoard.update(boardDto);
        return CommonResponseDto.builder()
                .status(200)
                .message("board" + boardDto.getBoardId() +" 수정에 성공하였습니다.")
                .build();

    }
}
