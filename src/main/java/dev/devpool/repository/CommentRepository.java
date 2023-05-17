package dev.devpool.repository;

import dev.devpool.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository {
    private final EntityManager em;

    @Autowired
    public CommentRepository(EntityManager em) {
        this.em = em;
    }

    // 저장
    public void save(Comment comment) {
        em.persist(comment);
    }

    // 조회
    public Comment findById(Long commentId) {
        Comment findComment = em.find(Comment.class, commentId);
        return findComment;
    }

    public List<Comment> findAll() {
        List<Comment> Comments = em.createQuery("select c from Comment c", Comment.class).getResultList();

        return Comments;
    }

    public List<Comment> findChildByParentId(Long parentId) {
        List<Comment> childComments = em.createQuery("select c from Comment c where c.parent.id=:parentId", Comment.class)
                .setParameter("parentId", parentId)
                .getResultList();
        System.out.println("childComments = " + childComments);
        if (childComments.size() == 0) {
            return null;
        } else return childComments;
    }

    // 팀Id로 Parent 조회
    public List<Comment> findAllParentCommentByTeamId(Long teamId) {
        List<Comment> commentList = em.createQuery("select c from Comment c where c.team.id=:teamId and c.parent IS NULL", Comment.class)
                .setParameter("teamId", teamId)
                .getResultList();

        return commentList;
    }

    // 삭제
    public void deleteById(Long commentId) {
        Comment findComment = em.find(Comment.class, commentId);
        if (findComment != null) {
            deleteChildByParentId(findComment.getId());
            em.remove(findComment);
        }
    }

    // 대댓글 삭제
    public void deleteChildByParentId(Long parentId) {
        // 어차피 조회하고 지우나, 그냥 지우나 쿼리는 한번 무조건 나가므로 find 하지 않음
        Query query = em.createQuery("delete from Comment c where c.parent.id=:parentId")
                .setParameter("parentId", parentId);

        query.executeUpdate();
    }

    // 팀 관련된 것 모두 삭제
    public void deleteAllByTeamId(Long teamId) {
        List<Comment> allParentComment = findAllParentCommentByTeamId(teamId);
        if(!allParentComment.isEmpty()){
            for (Comment parentComment : allParentComment) {
                deleteById(parentComment.getId());
            }
        }
    }

    public void deleteAll() {
        Query query = em.createQuery("delete from Comment c");
        query.executeUpdate();
    }


    // 수정
    public void update(Long commentId, Comment newComment) {
        Comment oldComment = em.find(Comment.class, commentId);

        oldComment.updateComment(newComment);
    }
}


