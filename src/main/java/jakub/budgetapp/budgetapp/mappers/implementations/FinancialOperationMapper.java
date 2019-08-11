package jakub.budgetapp.budgetapp.mappers.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;
import jakub.budgetapp.budgetapp.entites.FinancialOperation;
import jakub.budgetapp.budgetapp.mappers.Mapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class FinancialOperationMapper implements Mapper<FinancialOperation, FinancialOperationDto> {

    @Override
    public FinancialOperationDto toDto(FinancialOperation entity) {
        return FinancialOperationDto
                .builder()
                .id(entity.getId())
                .costs(entity.getCosts().toString())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .currency(entity.getCurrency())
                .date(entity.getLocalDate().toString())
                .build();
    }

    @Override
    public FinancialOperation toEntity(FinancialOperationDto dto) {
        return FinancialOperation.builder()
                .id(dto.getId())
                .category(dto.getCategory())
                .costs(new BigDecimal(dto.getCosts()))
                .description(dto.getDescription())
                .currency(dto.getCurrency())
                .localDate(LocalDate.parse(dto.getDate()))
                .build();
    }
}
