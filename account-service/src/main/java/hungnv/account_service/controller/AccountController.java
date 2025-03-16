package hungnv.account_service.controller;

import hungnv.account_service.entity.AccountEntity;
import hungnv.account_service.model.Account;
import hungnv.account_service.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/accounts")
public class AccountController {
    private final IAccountService acService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<Account> getListAccounts() {
        List<AccountEntity> accountEntities = acService.getListAccounts();
        return modelMapper.map(
                accountEntities,
                new TypeToken<List<Account>>() {}.getType());
    }
}
