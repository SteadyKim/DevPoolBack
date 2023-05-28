package dev.devpool.repository;

import dev.devpool.domain.Latter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LatterRepositoryImpl implements LatterRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Latter> findAllBySenderId(Long senderId) {

        List<Latter> latterList = em.createQuery("select l from Latter l where l.sender.id=:senderId ", Latter.class)
                .setParameter("senderId", senderId)
                .getResultList();
        return latterList;
    }

    @Override
    public List<Latter> findAllByReceiverId(Long receiverId) {
        List<Latter> latterList = em.createQuery("select l from Latter l where l.receiver.id=:receiverId", Latter.class)
                .setParameter("receiverId", receiverId)
                .getResultList();


        return latterList;
    }

    @Override
    public void deleteAllBySenderId(Long senderId) {
        Query query = em.createQuery("delete from Latter l where l.sender.id=:senderId")
                .setParameter("senderId", senderId);
        query.executeUpdate();
    }
}
