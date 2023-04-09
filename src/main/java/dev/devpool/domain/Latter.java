package dev.devpool.domain;

import dev.devpool.domain.enums.IsCheck;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;

@Entity
public class Latter {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LATTER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String body;

    private LocalDate createDate;

    @Enumerated(STRING)
    private IsCheck isCheck;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public IsCheck getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(IsCheck isCheck) {
        this.isCheck = isCheck;
    }
}
