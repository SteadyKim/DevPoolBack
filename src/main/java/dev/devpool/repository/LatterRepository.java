package dev.devpool.repository;


import dev.devpool.domain.Latter;
import dev.devpool.domain.Member;
import dev.devpool.dto.LatterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class LatterRepository {
    private final EntityManager em;

    @Autowired
    public LatterRepository(EntityManager em) {
        this.em = em;
    }

    // 저장
    public void save(Latter letter) {
        em.persist(letter);
    }

    // 조회
    public List<Latter> findAllBySenderId(Long senderId) {
        List<Latter> latterList = em.createQuery("select l from Latter l where l.sender.id=:senderId ", Latter.class)
                .setParameter("senderId", senderId)
                .getResultList();

//        latterDtoList.sort(Comparator.comparing(l -> l.get(0)));


        return latterList;
    }

    public List<Latter> findAllByReceiverId(Long receiverId) {
        List<Latter> latterList = em.createQuery("select l from Latter l where l.receiver.id=:receiverId", Latter.class)
                .setParameter("receiverId", receiverId)
                .getResultList();


        return latterList;
    }

    public Latter findById(Long latterId) {
        Latter findLatter = em.find(Latter.class, latterId);

        return findLatter;
    }


    // 삭제
    public void deleteById(Long latterId) {
        Latter latter = em.find(Latter.class, latterId);
        em.remove(latter);
    }

    public void deleteAllBySenderId(Long senderId) {
        Query query = em.createQuery("delete from Latter l where l.sender.id=:senderId")
                .setParameter("senderId", senderId);
        query.executeUpdate();
    }

    public void deleteAll() {
        Query query = em.createQuery("delete from Latter c");
        query.executeUpdate();
    }
}
