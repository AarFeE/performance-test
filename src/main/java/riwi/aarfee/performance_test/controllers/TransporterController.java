package riwi.aarfee.performance_test.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import riwi.aarfee.performance_test.dtos.responses.PayloadRes;
import riwi.aarfee.performance_test.services.UserService;

import java.util.List;

@Tag(name = "Transporter")
@RestController
@RequestMapping("/api/transporters")
@Validated
public class TransporterController {

    private final UserService userService;

    public TransporterController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Find Transporter's Payloads",
            description = "Retrieve all payloads of a transporter by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payloads retrieved")
            })
    @GetMapping("/{id}/payloads")
    public ResponseEntity<List<PayloadRes>> getTransporterPayloads(
            @Parameter(description = "ID of the transporter to retrieve payloads")
            @PathVariable Long id
    ) {
        List<PayloadRes> payloads = userService.getPayloadsById(id);
        return ResponseEntity.ok(payloads);
    }
}
