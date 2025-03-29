package hungnv.account_service.service;

import hungnv.account_service.dto.AccountDTO;
import hungnv.account_service.entity.AccountEntity;
import hungnv.account_service.repository.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final IAccountRepository acRepository;

    @Override
    public List<AccountEntity> getListAccounts() {
        return acRepository.findAll();
    }

    @Override
    public AccountEntity findAccountById(int id) {
        final Optional<AccountEntity> accountEntityOpt = acRepository.findById(id);
        return accountEntityOpt.orElse(null);

    }
}
