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

    public List<Site> getSites() {
        return sites;
    }


    @OneToMany(mappedBy = "member")
    private List<Certificate> certificates = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Latter> latters = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Site> sites = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<TechField> techFields = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Project> projects = new ArrayList<>();


    /**
     * 편의 메서드
     */
    public void addCertificate(Certificate certificate) {
        certificate.addMember(this);
        certificates.add(certificate);
    }

    public void addLatter(Latter latter) {
        latter.addMember(this);
        latters.add(latter);
    }

    public void addSite(Site site){
        site.addMember(this);
        sites.add(site);
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

    public void setMemberPoolCreateTime() {
        this.createTime = LocalDateTime.now();
    }

    public MemberDto.Response toDto() {
        return MemberDto.Response.builder()
                .name(this.name)
                .nickName(this.nickName)
                .email(this.email)
                .password(this.password)
                .imageUrl(this.imageUrl)
                .build();
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
