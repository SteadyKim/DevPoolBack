package dev.devpool.service;

import dev.devpool.domain.Comment;
import dev.devpool.domain.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    TeamService teamService;

    @Autowired
    CommentService commentService;

    @Autowired
    EntityManager em;

    @Autowired
    TransactionTemplate transactionTemplate;

    @AfterEach
    public void 지우기() {
        System.out.println("======@AfterEach======");
        commentService.deleteAll();
    }

    @Test
    public void 댓글저장_조회ByTeam() {
        transactionTemplate.execute(status -> {
            //given
            Team team = new Team();

            Comment parentComment1 = new Comment();
            Comment parentComment2 = new Comment();
            parentComment1.setTeam(team);
            parentComment2.setTeam(team);

            teamService.join(team);
            commentService.join(parentComment1);
            commentService.join(parentComment2);

            ArrayList<Long> ids = new ArrayList<>();
            ids.add(parentComment1.getId());
            ids.add(parentComment2.getId());

            //when
            em.flush();
            em.clear();

            //then
            List<Comment> parentComments = commentService.findAllParentByTeamId(team.getId());
            assertEquals(2, parentComments.size());
            for (Comment parentComment : parentComments) {
                ids.contains(parentComment.getId());
            }
            return null;
        });
    }

    @Test
    public void 대댓글저장_조회ByParent() {
        transactionTemplate.execute(status -> {
            //given

            Comment parentComment = new Comment();
            Comment childComment = new Comment();

            childComment.setParent(parentComment);

            commentService.join(parentComment);
            commentService.join(childComment);

            //when
            em.flush();
            em.clear();

            //then
            Comment findParentComment = commentService.findById(parentComment.getId());
            Comment findChild = commentService.findChildByParentId(findParentComment.getId());

            assertEquals(findParentComment.getId(), findChild.getParent().getId());

            return null;
        });
    }

    @Test
    public void 대댓글저장_조회ByParent_Null인경우() {
        transactionTemplate.execute(status -> {
            //given

            Comment parentComment = new Comment();
            commentService.join(parentComment);

            //when
            em.flush();
            em.clear();

            //then
            Comment findParentComment = commentService.findById(parentComment.getId());
            Comment findChild = commentService.findChildByParentId(findParentComment.getId());

            assertNull(findChild);

            return null;
        });
    }

    @Test
    public void 댓글_대댓글_한쌍으로조회ByTeam() {
        transactionTemplate.execute(status -> {
            // given
            Team team = new Team();

            Comment parentComment1 = new Comment();
            Comment parentComment2 = new Comment();

            parentComment1.setTeam(team);
            parentComment2.setTeam(team);

            Comment childComment1 = new Comment();
            Comment childComment2 = new Comment();

            childComment1.setParent(parentComment1);
            childComment2.setParent(parentComment2);

            teamService.join(team);
            commentService.join(parentComment1);
            commentService.join(parentComment2);
            commentService.join(childComment1);
            commentService.join(childComment2);

            // when
            em.flush();
            em.clear();


            // then
            List<Comment> parentComments = commentService.findAllParentByTeamId(team.getId());
            for (Comment parentComment : parentComments) {
                Comment child = commentService.findChildByParentId(parentComment.getId());
                assertNotNull(child);
            }

            return null;
        });
    }

    @Test
    public void 댓글삭제_자식이_있는_경우() {
        transactionTemplate.execute(status -> {
            // given

            Comment parentComment = new Comment();
            Comment childComment = new Comment();
            childComment.setParent(parentComment);

            commentService.join(parentComment);
            commentService.join(childComment);

            // when

            em.flush();
            em.clear();

            commentService.deleteById(parentComment.getId());
            em.flush();
            em.clear();

            // then
            Comment findParentComment = commentService.findById(parentComment.getId());
            Comment findChildComment = commentService.findById(childComment.getId());

            assertNull(findParentComment);
            assertNull(findChildComment);

            return null;
        });
    }

    @Test
    public void 댓글삭제_자식이_없는_경우() {
        transactionTemplate.execute(status -> {
            // given

            Comment parentComment = new Comment();

            commentService.join(parentComment);

            // when

            em.flush();
            em.clear();

            commentService.deleteById(parentComment.getId());
            em.flush();
            em.clear();

            // then
            Comment findParentComment = commentService.findById(parentComment.getId());

            assertNull(findParentComment);

            return null;
        });
    }

    @Test
    public void 대댓글삭제() {
        transactionTemplate.execute(status -> {
            // given

            Comment parentComment = new Comment();
            Comment childComment = new Comment();
            childComment.setParent(parentComment);

            commentService.join(parentComment);
            commentService.join(childComment);

            // when

            em.flush();
            em.clear();

            commentService.deleteChildByParentId(parentComment.getId());
            em.flush();
            em.clear();

            // then
            Comment findChildComment = commentService.findById(childComment.getId());

            assertNull(findChildComment);

            return null;
        });
    }

    @Test
    public void 댓글모두삭제ByTeam() {
        transactionTemplate.execute(status -> {
            // given
            Team team = new Team();

            Comment parentComment1 = new Comment();
            Comment parentComment2 = new Comment();

            parentComment1.setTeam(team);
            parentComment2.setTeam(team);

            Comment childComment1 = new Comment();
            Comment childComment2 = new Comment();

            childComment1.setParent(parentComment1);
            childComment2.setParent(parentComment2);

            teamService.join(team);
            commentService.join(parentComment1);
            commentService.join(parentComment2);
            commentService.join(childComment1);
            commentService.join(childComment2);

            // when
            em.flush();
            em.clear();
            commentService.deleteAllByTeamId(team.getId());

            // then
            List<Comment> comments = commentService.findAll();
            assertEquals(0, comments.size());
            return null;
        });
    }

    @Test
    public void 댓글수정() {
        transactionTemplate.execute(status -> {
            // given
            Comment oldComment = new Comment();
            oldComment.setBody("old");
            Comment newComment = new Comment();
            newComment.setBody("new");

            commentService.join(oldComment);
            // when
            em.flush();
            em.clear();
            commentService.update(oldComment.getId(), newComment);
            em.flush();
            em.clear();
            // then
            Comment findComment = commentService.findById(oldComment.getId());
            assertEquals("new", findComment.getBody());

            return null;
        });
    }

}
