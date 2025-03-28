package hungnv.account_service.controller;

import hungnv.account_service.entity.Account;
import hungnv.account_service.dto.AccountDTO;
import hungnv.account_service.entity.AccountEntity;
import hungnv.account_service.feignclient.DepartmentFeignClient;
import hungnv.account_service.service.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/accounts")
public class AccountController {
    @Value("${greeting.text}")
    private String greetingText;

    private final IAccountService acService;
    private final ModelMapper modelMapper;
    private RestTemplate restTemplate;
    private DepartmentFeignClient dpFeignClient;

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable final int id) {
        return acService.findAccountById(id);
    }

    @GetMapping
    public List<AccountDTO> getListAccounts() {
        List<AccountEntity> accountEntities = acService.getListAccounts();
        return modelMapper.map(
                accountEntities,
                new TypeToken<List<AccountDTO>>() {
                }.getType());
    }

    @GetMapping("/greeting")
    public String greet() {
        return greetingText;
    }
}
