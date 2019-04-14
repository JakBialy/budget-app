package jakub.budgetapp.budgetapp.entites;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Getter @Setter
class Role {

    /**
     * Role class is used to define role of users in system
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private int id;

    @Column(name = "role")
    private String name;
}
