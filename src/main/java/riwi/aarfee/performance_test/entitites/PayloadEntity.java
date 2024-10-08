package riwi.aarfee.performance_test.entitites;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import riwi.aarfee.performance_test.enums.PayloadState;

@Entity
@Table(name = "payloads")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class PayloadEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "dimensions", nullable = false)
    private String dimensions;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private PayloadState state;

    @ManyToOne
    @JoinColumn(name = "pallet_id")
    private PalletEntity pallet;

    @ManyToOne
    @JoinColumn(name = "transporter_id")
    private UserEntity transporter;
}
