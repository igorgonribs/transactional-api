package com.project.transactional.api.operationtype;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.InternalServerErrorException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class OperationTypeServiceImpl implements OperationTypeService {
    private static final Logger logger = Logger.getLogger("");

    private OperationTypeRepository repository;

    private List<OperationType> availableOperationTypes = new ArrayList<>();

    @Override
    public Boolean isOperationAmountValid(BigDecimal amount, Integer operationTypeId) throws Exception {
        OperationType operationType = getOperationType(operationTypeId);

        return switch (operationType.getSignal()) {
            case "positive" -> amount.floatValue() >= 0.0f;
            case "negative" -> amount.floatValue() <= 0.0f;
            default -> {
                String errorMessage = String.format("Invalid operation signal. Operation data: %s", operationType.toString());
                logger.severe(errorMessage);
                throw new InternalServerErrorException(errorMessage);
            }
        };
    }

    private OperationType getOperationType(Integer id) {
        if(availableOperationTypes.isEmpty()) {
            logger.fine("No cache for availableOperationTypes, retrieving from database");
            this.getAvailableOperationTypes();
        }
        Optional<OperationType> operationType = this.availableOperationTypes.stream()
                .filter(operation -> id.equals(operation.getId()))
                .findFirst();

        if(operationType.isEmpty()) {
            String errorMessage = String.format("Operation with id %d was not found", id);
            logger.severe(errorMessage);
            throw new NoSuchElementException(errorMessage);
        }

        return operationType.get();
    }

    private void getAvailableOperationTypes() {
        this.availableOperationTypes = repository.findAll();
    }
}
