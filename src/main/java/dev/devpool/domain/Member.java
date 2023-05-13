package dev.devpool.domain;

import dev.devpool.dto.MemberDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.Comment;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;


    private String name;
    private String nickName;
    private String email;
    private String password;

    private String imageUrl;

    @Comment("생성시간")
    private LocalDateTime createTime;

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Site> getSiteList() {
        return siteList;
    }


    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Certificate> certificateList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Latter> latterList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Site> siteList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<TechField> techFieldList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Project> projectList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<MemberTeam> memberTeamList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Stack> stackList = new ArrayList<>();

    @OneToMany(mappedBy = "hostMember", orphanRemoval = true)
    private List<Team> hostTeamList = new ArrayList<>();


    /**
     * 비지니스 로직
     */
    public void update(MemberDto.Save newMemberDto) {
        name = newMemberDto.getName();
        nickName = newMemberDto.getNickName();
        email = newMemberDto.getEmail();
        password = newMemberDto.getPassword();
        imageUrl = newMemberDto.getImageUrl();
    }

    public void setMemberPoolCreateTime(LocalDateTime localDateTime) {
        this.createTime = localDateTime;
    }


    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
