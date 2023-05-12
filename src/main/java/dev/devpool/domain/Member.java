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
     * 편의 메서드
     */
    public void addCertificate(Certificate certificate) {
        certificate.addMember(this);
        certificateList.add(certificate);
    }

    public void addLatter(Latter latter) {
        latter.addMember(this);
        latterList.add(latter);
    }

    public void addSite(Site site){
        site.addMember(this);
        siteList.add(site);
    }

    /**
     * 비지니스 로직
     */
    public void update(Member newMember) {
        name = newMember.getName();
        nickName = newMember.getNickName();
        email = newMember.getEmail();
        password = newMember.getPassword();
        imageUrl = newMember.getImageUrl();
    }

    public void setMemberPoolCreateTime(LocalDateTime localDateTime) {
        this.createTime = localDateTime;
    }

    public MemberDto.Response toDto() {
        return MemberDto.Response.builder()
                .memberId(this.id)
                .name(this.name)
                .nickName(this.nickName)
                .email(this.email)
                .imageUrl(this.imageUrl)
                .build();
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
