package hungnv.account_service.repository;

import hungnv.account_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

}
