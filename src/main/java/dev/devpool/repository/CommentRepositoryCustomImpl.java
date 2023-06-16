package dev.devpool.repository;

import dev.devpool.domain.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{
    private final EntityManager em;

    @Override
    public List<Comment> findChildByParentId(Long parentId) {
        List<Comment> childComments = em.createQuery("select c from Comment c where c.parent.id=:parentId", Comment.class)
                .setParameter("parentId", parentId)
                .getResultList();
        if (childComments.size() == 0) {
            return null;
        }
        return childComments;
    }

    @Override
    public List<Comment> findAllParentCommentByTeamId(Long teamId) {
        List<Comment> commentList = em.createQuery("select c from Comment c where c.team.id=:teamId and c.parent IS NULL", Comment.class)
                .setParameter("teamId", teamId)
                .getResultList();

        return commentList;
    }

    @Override
    public void deleteCustomById(Long commentId) {
        Comment findComment = em.find(Comment.class, commentId);
        if (findComment != null) {
            deleteChildByParentId(findComment.getId());
            em.remove(findComment);
        }
    }

    @Override
    public void deleteChildByParentId(Long parentId) {
        Query query = em.createQuery("delete from Comment c where c.parent.id=:parentId")
                .setParameter("parentId", parentId);

        query.executeUpdate();
    }

    @Override
    public void deleteAllByTeamId(Long teamId) {
        List<Comment> allParentComment = findAllParentCommentByTeamId(teamId);
        if(!allParentComment.isEmpty()){
            for (Comment parentComment : allParentComment) {
                deleteCustomById(parentComment.getId());
            }
        }
    }
}
