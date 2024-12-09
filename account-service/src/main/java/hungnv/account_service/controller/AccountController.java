package hungnv.account_service.controller;

import hungnv.account_service.dto.AccountDTO;
import hungnv.account_service.dto.DepartmentDTO;
import hungnv.account_service.entity.Account;
import hungnv.account_service.feignclient.DepartmentFeignClient;
import hungnv.account_service.service.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private DepartmentFeignClient departmentFeignClient;

    @GetMapping
    public List<AccountDTO> getListAccounts() {
        List<Account> accounts = acService.getListAccounts();
        List<AccountDTO> listAccountDTO = modelMapper.map(
                accounts,
                new TypeToken<List<AccountDTO>>() {}.getType());
        return listAccountDTO;
    }

    @GetMapping("/{id}")
    public AccountDTO getAccount(@PathVariable int id) {
        // Lấy thông tin Account từ service
        Account ac = acService.findAccountById(id);

        // Lấy ID của Department
        int dpId = ac.getDepartment().getId();

        DepartmentDTO department = departmentFeignClient.getDepartmentById(dpId);
        log.info("Received department: " + department);
        AccountDTO accountDTO = modelMapper.map(ac, AccountDTO.class);

        if (department != null) {
            accountDTO.setDepartmentId(department.getId());
            accountDTO.setDepartmentName(department.getName());
        } else {
            // Nếu không tìm thấy department, có thể đặt các giá trị mặc định cho department
            accountDTO.setDepartmentId(0);
            accountDTO.setDepartmentName("Unknown");
        }

        return accountDTO;
    }





}