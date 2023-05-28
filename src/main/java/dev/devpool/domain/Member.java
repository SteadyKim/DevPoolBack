package dev.devpool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.devpool.dto.MemberDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member implements UserDetails {
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
    @JsonIgnore
    private List<Certificate> certificateList = new ArrayList<>();

    @OneToMany(mappedBy = "sender", orphanRemoval = true)
    @JsonIgnore
    private List<Latter> sendLatterList = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", orphanRemoval = true)
    @JsonIgnore
    private List<Latter> receiveLatterList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @JsonIgnore
    private List<Site> siteList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @JsonIgnore
    private List<TechField> techFieldList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @JsonIgnore
    private List<Project> projectList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @JsonIgnore
    private List<MemberTeam> memberTeamList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @JsonIgnore
    private List<Stack> stackList = new ArrayList<>();

    @OneToMany(mappedBy = "hostMember", orphanRemoval = true)
    @JsonIgnore
    private List<Team> hostTeamList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();


    /**
     * 비지니스 로직
     */
    public void update(MemberDto.Save newMemberDto, String url) {
        name = newMemberDto.getName();
        nickName = newMemberDto.getNickName();
        email = newMemberDto.getEmail();
        password = newMemberDto.getPassword();
        imageUrl = url;
    }

    public void setMemberPoolCreateTime(LocalDateTime localDateTime) {
        this.createTime = localDateTime;
    }


    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {

        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
