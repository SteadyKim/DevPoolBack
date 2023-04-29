package dev.devpool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CERTIFICATE_ID")
    private Long id;

    private String body;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    Member member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
