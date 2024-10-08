package riwi.aarfee.performance_test.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import riwi.aarfee.performance_test.enums.PayloadState;

@Data
public class PayloadReq {


    @Schema(description = "Name of the payload",
            example = "Payload A",
            defaultValue = "Payload A")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Schema(description = "Weight of the payload",
            example = "10",
            defaultValue = "10")
    @NotNull(message = "Weight is mandatory")
    private Double weight;

    @Schema(description = "Dimensions of the payload",
            example = "10x10x10",
            defaultValue = "10x10x10")
    @NotBlank(message = "Dimensions is mandatory")
    private String dimensions;

    @Schema(description = "State of the payload (PENDING, IN_TRANSIT, DELIVERED, DAMAGED)",
            example = "PENDING",
            defaultValue = "PENDING")
    @NotNull(message = "State is mandatory")
    private PayloadState state;

    @Schema(description = "Pallet of the payload",
            example = "1",
            defaultValue = "1")
    @NotNull(message = "Pallet is mandatory")
    private Long palletId;

    @Schema(description = "Transporter of the payload",
            example = "1",
            defaultValue = "1")
    @NotNull(message = "Transporter is mandatory")
    private Long transporterId;
}
