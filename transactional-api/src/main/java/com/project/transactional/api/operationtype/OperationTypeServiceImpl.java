package com.project.transactional.api.operationtype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.project.transactional.api.transaction.TransactionServiceImpl;

import jakarta.ws.rs.InternalServerErrorException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OperationTypeServiceImpl implements OperationTypeService {
	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private OperationTypeRepository repository;

    private List<OperationType> availableOperationTypes = new ArrayList<>();

    @Override
    public Boolean isOperationAmountValid(BigDecimal amount, OperationType operationType) throws Exception {
        return switch (operationType.getOperationSignal()) {
            case "positive" -> amount.floatValue() >= 0.0f;
            case "negative" -> amount.floatValue() <= 0.0f;
            default -> {
                String errorMessage = String.format("Invalid operation signal. Operation data: %s", operationType.toString());
                logger.error(errorMessage);
                throw new InternalServerErrorException(errorMessage);
            }
        };
    }

    public Optional<OperationType> getOperationType(Integer id) {
        if(availableOperationTypes.isEmpty()) {
            logger.info("No cache for availableOperationTypes, retrieving from database");
            this.getAvailableOperationTypes();
        }
        return this.availableOperationTypes.stream()
                .filter(operation -> id.equals(operation.getId()))
                .findFirst();
    }

    private void getAvailableOperationTypes() {
        this.availableOperationTypes = repository.findAll();
    }
}
