package hungnv.account_service.controller;

import hungnv.account_service.dto.AccountDTO;
import hungnv.account_service.dto.AccountRequestDTO;
import hungnv.account_service.dto.ResponseAPIDTO;
import hungnv.account_service.entity.Account;
import hungnv.account_service.entity.AccountEntity;
import hungnv.account_service.feignclient.DepartmentFeignClient;
import hungnv.account_service.service.IAccountService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    @Value("${greeting.text}")
    private String greetingText;

    private final IAccountService acService;
    private final ModelMapper modelMapper;
    private RestTemplate restTemplate;
    private DepartmentFeignClient dpFeignClient;

    @GetMapping
    public List<AccountDTO> getListAccounts() {
        List<Account> accounts = acService.getListAccounts();
        List<AccountDTO> listAccountDTO = modelMapper.map(
                accounts,
                new TypeToken() {
                }.getType());
        return listAccountDTO;
    }

    @CircuitBreaker(name = "departmentService", fallbackMethod = "fallbackNotCallDepartmentService")
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable final int id) {
        return acService.findAccountById(id);
    @CircuitBreaker(name = "departmentService", fallbackMethod = "fallbackNotCallDepartmentService")
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable final int id) {
        return acService.findAccountById(id);
    }

    public List<Account> getListAccounts() {
        List<AccountEntity> accountEntities = acService.getListAccounts();
        return modelMapper.map(
                accountEntities,
                new TypeToken<List<Account>>() {}.getType());
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    public String fallbackNotCallDepartmentService(int id, Throwable throwable) {
        return "Department Servers Down";
    }

    @PostMapping
    public ResponseAPIDTO<AccountDTO> createAccount(@RequestBody AccountRequestDTO acRequestDTO) {
        Account account = modelMapper.map(acRequestDTO, Account.class);
        Account ac = acService.createAccount(account);

        return ResponseAPIDTO.<AccountDTO>builder()
                .result(modelMapper.map(ac, AccountDTO.class))
                .build();
    }

    @GetMapping("/greeting")
    public String greet() {
        return greetingText;
    }
}
