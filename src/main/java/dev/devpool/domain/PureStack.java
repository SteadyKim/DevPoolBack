package dev.devpool.domain;

import javax.persistence.*;

/**
 * 나중에 쿼리로 insert만 해둘 예정
 */
@Entity
public class PureStack {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PURESTACK_ID")
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
