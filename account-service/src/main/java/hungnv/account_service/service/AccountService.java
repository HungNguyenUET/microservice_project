package hungnv.account_service.service;

import hungnv.account_service.entity.AccountEntity;
import hungnv.account_service.repository.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final IAccountRepository acRepository;

    @Override
    public List<AccountEntity> getListAccounts() {
        return acRepository.findAll();
    }
}
