package dev.devpool.repository;

import dev.devpool.domain.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> findChildByParentId(Long parentId);

    List<Comment> findAllParentCommentByTeamId(Long teamId);

    void deleteCustomById(Long commentId);

    void deleteChildByParentId(Long parentId);

    void deleteAllByTeamId(Long teamId);
}
