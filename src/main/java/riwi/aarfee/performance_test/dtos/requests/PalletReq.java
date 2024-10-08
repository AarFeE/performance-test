package riwi.aarfee.performance_test.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import riwi.aarfee.performance_test.enums.PalletState;

@Data
public class PalletReq {

    @Schema(description = "Material of the pallet",
            example = "Plastic",
            defaultValue = "Plastic")
    @NotBlank(message = "Material is mandatory")
    private String material;

    @Schema(description = "Max weight of the pallet",
            example = "100",
            defaultValue = "100")
    @NotNull(message = "Max weight is mandatory")
    private Double maxWeight;

    @Schema(description = "Location of the pallet",
            example = "Warehouse A",
            defaultValue = "Warehouse A")
    @NotBlank(message = "Location is mandatory")
    private String location;

    @Schema(description = "State of the pallet (AVAILABLE, IN_USE, DAMAGED, OUT_OF_SERVICE)",
            example = "AVAILABLE",
            defaultValue = "AVAILABLE")
    @NotNull(message = "State is mandatory")
    private PalletState state;

}
