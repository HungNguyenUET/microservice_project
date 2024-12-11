package hungnv.account_service.controller;

import hungnv.account_service.dto.DepartmentDTO;
import hungnv.account_service.entity.Account;
import hungnv.account_service.feignclient.DepartmentFeignClient;
import hungnv.account_service.service.IAccountService;
import hungnv.account_service.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

//    @GetMapping
//    public List<AccountDTO> getListAccounts() {
//        List<Account> accounts = acService.getListAccounts();
//        List<AccountDTO> listAccountDTO = modelMapper.map(
//                accounts,
//                new TypeToken<List<AccountDTO>>() {}.getType());
//        return listAccountDTO;
//    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable final int id) {
        Account ac = acService.findAccountById(id);
        int dpId = ac.getDepartment().getId();
        return ac;
    }

    @GetMapping("/department/{id}")
    public DepartmentDTO getDepartment(@PathVariable final  int id) {
        log.info("AccountController|getDepartment|/department/{id}|START|id|{}", id);
//        DepartmentDTO department = restTemplate.getForObject("http://department-service:8083/api/v1/departments/" + dpId, DepartmentDTO.class);
//        log.info("Department: {}", department);

        ResponseEntity<DepartmentDTO> dpResponseEntity = dpFeignClient.getDepartmentById(id);

        log.info("AccountController|getDepartment|Response from feign client");
        log.info("AccountController|getDepartment|Response body: {}", JsonUtils.toJson(dpResponseEntity));

        DepartmentDTO departmentDTO = dpResponseEntity.getBody();
        return departmentDTO;
    }
}
