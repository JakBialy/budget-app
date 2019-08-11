package jakub.budgetapp.budgetapp.entites;

import jakub.budgetapp.budgetapp.services.enums.Currency;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "financial_operations")
@Builder
@Data
public class FinancialOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "financial_operation_id")
    private Long id;

    @Column
    @NotNull
    private BigDecimal costs;

    @Column
    private String description;

    @Column
    private String category;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "date")
    @NotNull
    private LocalDate localDate;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

}
