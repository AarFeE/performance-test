package riwi.aarfee.performance_test.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import riwi.aarfee.performance_test.entitites.PayloadEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayloadRes {

    private Long id;
    private String name;
    private Double weight;
    private String dimensions;
    private Long palletId;
    private Long transporterId;

    public PayloadRes(PayloadEntity pallet) {
        this.id = pallet.getId();
        this.name = pallet.getName();
        this.weight = pallet.getWeight();
        this.dimensions = pallet.getDimensions();
        this.palletId = pallet.getId();
        this.transporterId = pallet.getTransporter().getId();
    }
}
