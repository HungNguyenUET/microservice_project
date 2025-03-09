package hungnv.account_service.controller;

import hungnv.account_service.dto.AccountDTO;
import hungnv.account_service.dto.DepartmentDTO;
import hungnv.account_service.entity.Account;
import hungnv.account_service.feignclient.DepartmentFeignClient;
import hungnv.account_service.service.IAccountService;
import hungnv.account_service.utils.JsonUtils;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService acService;
    private final ModelMapper modelMapper;
    private RestTemplate restTemplate;
    private DepartmentFeignClient dpFeignClient;

    @CircuitBreaker(name = "departmentService", fallbackMethod = "fallbackNotCallDepartmentService")
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable final int id) {
        return acService.findAccountById(id);
    }

    @GetMapping
    public List<AccountDTO> getListAccounts() {
        List<Account> accounts = acService.getListAccounts();
        return modelMapper.map(
                accounts,
                new TypeToken<List<AccountDTO>>() {}.getType());
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    public String fallbackNotCallDepartmentService(int id, Throwable throwable) {
        return "Department Servers Down";
    }
}
