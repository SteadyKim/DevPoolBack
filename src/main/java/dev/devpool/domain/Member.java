package dev.devpool.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;


    private String name;
    private String nickName;
    private String email;
    private String password;

    public Member() {
    }

    public Member(String name, String nickName, String email, String password) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
    }

    @OneToMany(mappedBy = "member")
    private List<Certificate> certificates = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Latter> latters = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Site> sites = new ArrayList<>();

    public List<Latter> getLatters() {
        return latters;
    }

    public void setLatters(List<Latter> latters) {
        this.latters = latters;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }



    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * 편의 메서드
     */
    public void addCertificate(Certificate certificate) {
        certificate.setMember(this);
        certificates.add(certificate);
    }

    public void addLatter(Latter latter) {
        latter.setMember(this);
        latters.add(latter);
    }

    public void addSite(Site site){
        site.setMember(this);
        sites.add(site);
    }
}
