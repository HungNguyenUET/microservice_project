package hungnv.account_service.controller;

import hungnv.account_service.dto.AccountDTO;
import hungnv.account_service.dto.DepartmentDTO;
import hungnv.account_service.entity.Account;
import hungnv.account_service.service.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService acService;
    private final ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public List<AccountDTO> getListAccounts() {
        List<Account> accounts = acService.getListAccounts();
        List<AccountDTO> listAccountDTO = modelMapper.map(
                accounts,
                new TypeToken<List<AccountDTO>>() {}.getType());
        return listAccountDTO;
    }

    @GetMapping
    public Account getAccount(@PathVariable final int id) {
        Account ac = acService.findAccountById(id);
        int dpId = ac.getDepartment().getId();
        DepartmentDTO department = restTemplate.getForObject("http://department-service:8083/api/v1/departments/" + dpId, DepartmentDTO.class);
        log.info("Department: {}", department);
        return null;
    }
}
