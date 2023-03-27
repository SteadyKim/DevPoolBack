package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.MemberTeam;
import dev.devpool.domain.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class TestService {
    private final EntityManager em;

    public TestService(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void test() {

    }
}
