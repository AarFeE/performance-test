package riwi.aarfee.performance_test.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import riwi.aarfee.performance_test.entitites.PalletEntity;
import riwi.aarfee.performance_test.enums.PalletState;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PalletRes {

    private Long id;
    private String material;
    private Double maxWeight;
    private String location;
    private PalletState state;
    private List<PayloadRes> payloads;

    public PalletRes(PalletEntity pallet) {
        this.id = pallet.getId();
        this.material = pallet.getMaterial();
        this.maxWeight = pallet.getMaxWeight();
        this.location = pallet.getLocation();
        this.state = pallet.getState();
        this.payloads = pallet.getPayloads().stream()
                .map(PayloadRes::new)
                .collect(Collectors.toList());
    }
}
