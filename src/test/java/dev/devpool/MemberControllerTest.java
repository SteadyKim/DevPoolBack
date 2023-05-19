package dev.devpool;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

        @Autowired
        private MockMvc mockMvc;

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
    }


