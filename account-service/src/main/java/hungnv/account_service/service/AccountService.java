package hungnv.account_service.service;

import hungnv.account_service.entity.Account;
import hungnv.account_service.repository.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final IAccountRepository acRepository;

    @Override
    public List<Account> getListAccounts() {
        return acRepository.findAll();
    }

    @Override
    public Account findAccountById(int id) {
        return acRepository.findById(id).get();
    }
}
