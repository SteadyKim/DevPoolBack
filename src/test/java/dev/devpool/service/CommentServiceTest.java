//package dev.devpool.service;
//
//import dev.devpool.domain.Comment;
//import dev.devpool.domain.Team;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import javax.persistence.EntityManager;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class CommentServiceTest {
//    @Autowired
//    MemberService memberService;
//
//    @Autowired
//    TeamService teamService;
//
//    @Autowired
//    CommentService commentService;
//
//    @Autowired
//    EntityManager em;
//
//    @Autowired
//    TransactionTemplate transactionTemplate;
//
//    @AfterEach
//    public void 지우기() {
//        System.out.println("======@AfterEach======");
//        commentService.deleteAll();
//    }
//
//    @Test
//    public void 댓글저장_조회ByTeam() {
//        transactionTemplate.execute(status -> {
//            //given
//            Team team = new Team();
//
//            Comment parentComment1 = new Comment();
//            Comment parentComment2 = new Comment();
//            parentComment1.addTeam(team);
//            parentComment2.addTeam(team);
//
//            teamService.join(team);
//            commentService.join(parentComment1);
//            commentService.join(parentComment2);
//
//            ArrayList<Long> ids = new ArrayList<>();
//            ids.add(parentComment1.getId());
//            ids.add(parentComment2.getId());
//
//            //when
//            em.flush();
//            em.clear();
//
//            //then
//            List<Comment> parentComments = commentService.findAllParentByTeamId(team.getId());
//            assertEquals(2, parentComments.size());
//            for (Comment parentComment : parentComments) {
//                ids.contains(parentComment.getId());
//            }
//            return null;
//        });
//    }
//
//    @Test
//    public void 대댓글저장_조회ByParent() {
//        transactionTemplate.execute(status -> {
//            //given
//
//            Comment parentComment = new Comment();
//            Comment childComment1 = new Comment();
//            Comment childComment2 = new Comment();
//
//            childComment1.addParent(parentComment);
//            childComment2.addParent(parentComment);
//
//            commentService.join(parentComment);
//            commentService.join(childComment1);
//            commentService.join(childComment2);
//
//            ArrayList<Long> ids = new ArrayList<>();
//            ids.add(childComment1.getId());
//            ids.add(childComment2.getId());
//
//            //when
//            em.flush();
//            em.clear();
//
//            //then
//            Comment findParentComment = commentService.findById(parentComment.getId());
//            List<Comment> childComments = commentService.findChildByParentId(findParentComment.getId());
//
//            assertEquals(2, childComments.size());
//
//            for (Comment childComment : childComments) {
//                ids.contains(childComment.getId());
//            }
//
//            return null;
//        });
//    }
//
//    @Test
//    public void 대댓글저장_조회ByParent_Null인경우() {
//        transactionTemplate.execute(status -> {
//            //given
//
//            Comment parentComment = new Comment();
//            commentService.join(parentComment);
//
//
//
//            //when
//            em.flush();
//            em.clear();
//
//            //then
//            Comment findParentComment = commentService.findById(parentComment.getId());
//            List<Comment> childComments = commentService.findChildByParentId(findParentComment.getId());
//
//            assertNull(childComments);
//
//            return null;
//        });
//    }
//
//    @Test
//    public void 댓글_대댓글_한쌍으로조회ByTeam() {
//        transactionTemplate.execute(status -> {
//            // given
//            Team team = new Team();
//
//            Comment parentComment1 = new Comment();
//            Comment parentComment2 = new Comment();
//
//            parentComment1.addTeam(team);
//            parentComment2.addTeam(team);
//
//            Comment childComment1 = new Comment();
//            Comment childComment2 = new Comment();
//            Comment childComment3 = new Comment();
//            Comment childComment4 = new Comment();
//
//            childComment1.addParent(parentComment1);
//            childComment2.addParent(parentComment1);
//            childComment3.addParent(parentComment2);
//            childComment4.addParent(parentComment2);
//
//            teamService.join(team);
//            commentService.join(parentComment1);
//            commentService.join(parentComment2);
//
//            commentService.join(childComment1);
//            commentService.join(childComment2);
//            commentService.join(childComment3);
//            commentService.join(childComment4);
//
//            ArrayList<Long> ids1 = new ArrayList<>();
//            ids1.add(childComment1.getId());
//            ids1.add(childComment2.getId());
//
//            ArrayList<Long> ids2 = new ArrayList<>();
//            ids2.add(childComment3.getId());
//            ids2.add(childComment4.getId());
//
//            // when
//            em.flush();
//            em.clear();
//
//
//            // then
//            List<Comment> parentComments = commentService.findAllParentByTeamId(team.getId());
//            for (Comment parentComment : parentComments) {
//                if(parentComment.getId() == 1) {
//                    List<Comment> childComments = commentService.findChildByParentId(parentComment.getId());
//                    assertEquals(2, childComments.size());
//                    for (Comment childComment : childComments) {
//                        assertTrue(ids1.contains(childComment.getId()));
//                    }
//                }
//
//                if(parentComment.getId() == 2) {
//                    List<Comment> childComments = commentService.findChildByParentId(parentComment.getId());
//                    assertEquals(2, childComments.size());
//                    for (Comment childComment : childComments) {
//                        assertTrue(ids2.contains(childComment.getId()));
//                    }
//                }
//            }
//
//            return null;
//        });
//    }
//
//    @Test
//    public void 댓글삭제_자식이_있는_경우() {
//        transactionTemplate.execute(status -> {
//            // given
//
//            Comment parentComment = new Comment();
//            Comment childComment = new Comment();
//            childComment.addParent(parentComment);
//
//            commentService.join(parentComment);
//            commentService.join(childComment);
//
//            // when
//
//            em.flush();
//            em.clear();
//
//            commentService.deleteById(parentComment.getId());
//            em.flush();
//            em.clear();
//
//            // then
//            Comment findParentComment = commentService.findById(parentComment.getId());
//            Comment findChildComment = commentService.findById(childComment.getId());
//
//            assertNull(findParentComment);
//            assertNull(findChildComment);
//
//            return null;
//        });
//    }
//
//    @Test
//    public void 댓글삭제_자식이_없는_경우() {
//        transactionTemplate.execute(status -> {
//            // given
//
//            Comment parentComment = new Comment();
//
//            commentService.join(parentComment);
//
//            // when
//
//            em.flush();
//            em.clear();
//
//            commentService.deleteById(parentComment.getId());
//            em.flush();
//            em.clear();
//
//            // then
//            Comment findParentComment = commentService.findById(parentComment.getId());
//
//            assertNull(findParentComment);
//
//            return null;
//        });
//    }
//
//    @Test
//    public void 대댓글삭제() {
//        transactionTemplate.execute(status -> {
//            // given
//
//            Comment parentComment = new Comment();
//            Comment childComment = new Comment();
//            childComment.addParent(parentComment);
//
//            commentService.join(parentComment);
//            commentService.join(childComment);
//
//            // when
//
//            em.flush();
//            em.clear();
//
//            commentService.deleteChildByParentId(parentComment.getId());
//            em.flush();
//            em.clear();
//
//            // then
//            Comment findChildComment = commentService.findById(childComment.getId());
//
//            assertNull(findChildComment);
//
//            return null;
//        });
//    }
//
//    @Test
//    public void 댓글모두삭제ByTeam() {
//        transactionTemplate.execute(status -> {
//            // given
//            Team team = new Team();
//
//            Comment parentComment1 = new Comment();
//            Comment parentComment2 = new Comment();
//
//            parentComment1.addTeam(team);
//            parentComment2.addTeam(team);
//
//            Comment childComment1 = new Comment();
//            Comment childComment2 = new Comment();
//
//            childComment1.addParent(parentComment1);
//            childComment2.addParent(parentComment2);
//
//            teamService.join(team);
//            commentService.join(parentComment1);
//            commentService.join(parentComment2);
//            commentService.join(childComment1);
//            commentService.join(childComment2);
//
//            // when
//            em.flush();
//            em.clear();
//            commentService.deleteAllByTeamId(team.getId());
//
//            // then
//            List<Comment> comments = commentService.findAll();
//            assertEquals(0, comments.size());
//            return null;
//        });
//    }
//
//    @Test
//    public void 댓글수정() {
//        transactionTemplate.execute(status -> {
//            // given
//            Comment oldComment = Comment.builder()
//                    .body("old")
//                    .build();
//
//            Comment newComment = Comment.builder()
//                    .body("new")
//                    .build();
//
//            commentService.join(oldComment);
//            // when
//            em.flush();
//            em.clear();
//            commentService.update(oldComment.getId(), newComment);
//            em.flush();
//            em.clear();
//            // then
//            Comment findComment = commentService.findById(oldComment.getId());
//            assertEquals("new", findComment.getContent());
//
//            return null;
//        });
//    }
//
//}
