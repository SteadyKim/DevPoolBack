package dev.devpool.dto;

import dev.devpool.domain.Member;
import lombok.Data;

@Data
public class CreateMemberDto {
    private String name;

    private String nickName;

    private String email;

    private String password;

    private String imageUrl;

    public Member toEntity(){
        Member member = new Member();
        member.setName(name);
        member.setNickName(nickName);
        member.setEmail(email);
        member.setPassword(password);
        member.setImageUrl(imageUrl);

        return member;
    }
}
