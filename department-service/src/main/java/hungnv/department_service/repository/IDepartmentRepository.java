package hungnv.department_service.repository;

import hungnv.department_service.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
}
