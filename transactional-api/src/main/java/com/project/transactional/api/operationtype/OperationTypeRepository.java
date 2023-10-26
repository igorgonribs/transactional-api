package com.project.transactional.api.operationtype;

import com.project.transactional.api.operationtype.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Integer> {
}
