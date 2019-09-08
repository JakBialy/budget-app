package jakub.budgetapp.budgetapp.entites;

import jakub.budgetapp.budgetapp.services.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "financial_operations")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private LocalDate date;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

}
