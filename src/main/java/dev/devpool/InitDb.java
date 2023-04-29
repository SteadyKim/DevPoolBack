package dev.devpool;

import dev.devpool.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.ss();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void ss() {
            Member member = Member.builder()
                    .name("김태우")
                    .nickName("찐빵")
                    .email("ㄱㄷㄱㄷ@naver.com")
                    .password("rter141")
                    .imageUrl("asdojasdsa/.sdfsdjfnj")
                    .build();

            em.persist(member);
        }
    }

}
