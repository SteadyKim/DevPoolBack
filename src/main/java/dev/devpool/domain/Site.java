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
public class Site {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SITE_ID")
    private Long id;

    private String url;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    Member member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
