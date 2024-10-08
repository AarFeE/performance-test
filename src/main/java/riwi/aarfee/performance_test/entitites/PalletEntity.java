package riwi.aarfee.performance_test.entitites;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import riwi.aarfee.performance_test.enums.PalletState;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pallets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class PalletEntity extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "material", nullable = false)
    private String material;

    @Column(name = "max_weight", nullable = false)
    private Double maxWeight;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private PalletState state;

    @OneToMany(mappedBy = "pallet", cascade = CascadeType.ALL)
    private Set<PayloadEntity> payloads = new HashSet<>();

}
