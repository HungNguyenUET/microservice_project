package hungnv.account_service.controller;

import hungnv.account_service.dto.AccountDTO;
import hungnv.account_service.dto.AccountRequestDTO;
import hungnv.account_service.dto.DepartmentDTO;
import hungnv.account_service.dto.ResponseAPIDTO;
import hungnv.account_service.entity.Account;
import hungnv.account_service.feignclient.DepartmentFeignClient;
import hungnv.account_service.service.IAccountService;
import hungnv.account_service.utils.JsonUtils;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService acService;
    private final ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DepartmentFeignClient dpFeignClient;

    @GetMapping
    public List<AccountDTO> getListAccounts() {
        List<Account> accounts = acService.getListAccounts();
        List<AccountDTO> listAccountDTO = modelMapper.map(
                accounts,
                new TypeToken() {}.getType());
        return listAccountDTO;
    }

    @CircuitBreaker(name = "departmentService", fallbackMethod = "fallbackNotCallDepartmentService")
    @GetMapping("/{id}")
    public String getAccount(@PathVariable final int id) {
        Account ac = acService.findAccountById(id);
        int dpId = ac.getDepartment().getId();

        DepartmentDTO department = restTemplate.getForObject("http://department-service-1:8080/api/v1/departments/" + dpId, DepartmentDTO.class);
        log.info("Department: {}", department);

        return ac.toString();
    }

    @GetMapping("/department/{id}")
    public DepartmentDTO getDepartment(@PathVariable final int id) {
        log.info("AccountController|getDepartment|/department/{id}|START|id|{}", id);
//        DepartmentDTO department = restTemplate.getForObject("http://department-service:8083/api/v1/departments/" + dpId, DepartmentDTO.class);
//        log.info("Department: {}", department);

        ResponseEntity<DepartmentDTO> dpResponseEntity = dpFeignClient.getDepartmentById(id);

        log.info("AccountController|getDepartment|Response from feign client");
        log.info("AccountController|getDepartment|Response body: {}", JsonUtils.toJson(dpResponseEntity));

        DepartmentDTO departmentDTO = dpResponseEntity.getBody();
        return departmentDTO;
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
}
