package hungnv.account_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hungnv.account_service.dto.AccountRequestDTO;
import hungnv.account_service.entity.Account;
import hungnv.account_service.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private AccountService acService;

    private Account accountResponse;
    private AccountRequestDTO accountRequestDTO;

    @BeforeEach
    void initData() {
        accountRequestDTO = AccountRequestDTO.builder()
                .username("vantoan")
                .firstName("Nguyen Van")
                .lastName("Toan")
                .password("123456")
                .role("ADMIN")
                .build();

        accountResponse = Account.builder()
                .id(4)
                .username("vantoan")
                .firstName("Nguyen Van")
                .lastName("Toan")
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(accountRequestDTO);

        Mockito.when(acService.createAccount(ArgumentMatchers.any())).thenReturn(accountResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value(4));
    }

}
