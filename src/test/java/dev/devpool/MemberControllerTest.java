package dev.devpool;

import dev.devpool.domain.Member;
import dev.devpool.dto.MemberDto;
import dev.devpool.dto.common.CommonResponseDto;
import dev.devpool.repository.MemberRepository;
import dev.devpool.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.PostConstruct;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private InitDb.InitService initService;

        @PostConstruct
        public void init() {
            initService.ss();
        }
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private MemberService memberService;

        @Autowired
        private MemberRepository memberRepository;

        @Test
        public void testGetMemberApi() throws Exception {
        String memberId = "1";

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/member/{memberId}", memberId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
        }


    @Test
    @WithMockUser(roles = "USER")
    public void testGetMemberWithAuthApi() throws Exception {
        String memberId = "1";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/member/{memberId}", memberId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberId").value(memberId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("1234@naver.com"))
        ;
    }


    @Test
    @WithMockUser(roles = "USER")
    public void testUpdateMemberWithAuthApi() throws Exception {

        // API 호출 및 응답 확인
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/member/{memberId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"잉잉잉\",\"nickName\":\"귀욤둥이\",\"email\":\"1111@naver.com\",\"password\":\"1234\",\"imageUrl\":\"11211\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/member/{memberId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("1111@naver.com"))
        ;

    }







    }


