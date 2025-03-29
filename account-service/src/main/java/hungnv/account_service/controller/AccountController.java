package hungnv.account_service.controller;

import hungnv.account_service.dto.AccountDTO;
import hungnv.account_service.dto.DepartmentDTO;
import hungnv.account_service.entity.AccountEntity;
import hungnv.account_service.feignclient.DepartmentFeignClient;
import hungnv.account_service.service.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/accounts")
public class AccountController {
    @Value("${greeting.text}")
    private String greetingText;

    @Value("${department-service.uri}")
    private String departmentUri;

    private final IAccountService acService;
    private final ModelMapper modelMapper;
    private final RestClient restClient;
    private final DepartmentFeignClient dpFeignClient;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getListAccounts() {
        final List<AccountEntity> accountEntities = acService.getListAccounts();
        final List<AccountDTO> lsAccountDTO = modelMapper.map(
                accountEntities,
                new TypeToken<List<AccountDTO>>() {
                }.getType());
        return ResponseEntity.ok(lsAccountDTO);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable("accountId") int accountId) {
        final AccountEntity accountEntity = acService.findAccountById(accountId);
        final AccountDTO accountDTO = modelMapper.map(
                accountEntity,
                new TypeToken<AccountDTO>() {
                }.getType());
        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping("/departments")
    public List<DepartmentDTO> getDepartmentInfo() {
        return restClient.get()
                .uri(departmentUri)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @GetMapping("/departments/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentInfo(@PathVariable("departmentId") int departmentId) {
        return dpFeignClient.getDepartmentById(departmentId);
    }

    @GetMapping("/greeting")
    public String greet() {
        return greetingText;
    }
}
