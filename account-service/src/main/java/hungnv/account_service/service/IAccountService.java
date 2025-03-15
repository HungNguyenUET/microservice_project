package hungnv.account_service.service;

import hungnv.account_service.entity.AccountEntity;

import java.util.List;

public interface IAccountService {

    List<AccountEntity> getListAccounts();

}
