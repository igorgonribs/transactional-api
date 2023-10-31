package com.project.transactional.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.transactional.api.domain.OperationType;

@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Integer> {
}
