package com.project.transactional.api.service.impl;

import com.project.transactional.api.domain.OperationType;
import com.project.transactional.api.repository.OperationTypeRepository;
import com.project.transactional.api.service.OperationTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OperationTypeServiceImpl implements OperationTypeService {
    private OperationTypeRepository repository;

    private List<OperationType> availableOperationTypes = new ArrayList<>();

    @Override
    public Boolean isOperationAmountValid(BigDecimal amount, Integer operationTypeId) throws Exception {
        OperationType operationType = getOperationType(operationTypeId);

        return switch (operationType.getSignal()) {
            case "positive" -> amount.floatValue() >= 0.0f;
            case "negative" -> amount.floatValue() <= 0.0f;
            default -> throw new Exception("Operation does not match");
        };
    }

    private OperationType getOperationType(Integer id) {
        if(availableOperationTypes.isEmpty())
            this.getAvailableOperationTypes();

        Optional<OperationType> operationType = this.availableOperationTypes.stream()
                .filter(operation -> id.equals(operation.getId()))
                .findFirst();

        if(operationType.isEmpty())
            throw new NoSuchElementException(String.format("Operation with id %d was not found", id));

        return operationType.get();
    }

    private void getAvailableOperationTypes() {
        this.availableOperationTypes = repository.findAll();
    }
}
