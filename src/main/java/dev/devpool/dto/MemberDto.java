package dev.devpool.dto;

import dev.devpool.domain.Member;
import dev.devpool.exception.MemberNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private Long id;
    private String name;
    private String nickName;
    private String email;
    private String password;

    private String imageUrl;

    public Member toEntity() {
        Member member = new Member();
        member.setName(name);
        member.setNickName(nickName);
        member.setEmail(email);
        member.setPassword(password);
        member.setImageUrl(imageUrl);

        return member;
    }

    /**
     * 추후 Exception Handler 구현해야 함
     */
    public static MemberDto convertToMemberDto(Member member) {
        if(Optional.ofNullable(member).isPresent()) {
            return new MemberDto(member.getId(), member.getName(), member.getNickName(), member.getEmail(), member.getPassword(), member.getImageUrl());
        }
        else  {
            throw new MemberNotFoundException();
        }
    }
}
