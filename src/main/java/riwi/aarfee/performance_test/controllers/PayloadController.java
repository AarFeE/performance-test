package riwi.aarfee.performance_test.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import riwi.aarfee.performance_test.dtos.requests.PayloadReq;
import riwi.aarfee.performance_test.dtos.responses.PayloadRes;
import riwi.aarfee.performance_test.enums.PayloadState;
import riwi.aarfee.performance_test.services.PayloadService;

@Tag(name = "Payload")
@RestController
@RequestMapping("/api/payloads")
@Validated
public class PayloadController {

    public final PayloadService payloadService;

    public PayloadController(PayloadService payloadService) {
        this.payloadService = payloadService;
    }

    @Operation(summary = "Find By ID",
            description = "Search for a payload by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payload found"),
                    @ApiResponse(responseCode = "404", description = "Payload not found")
            })
    @GetMapping("/{id}")
    public ResponseEntity<PayloadRes> getPayloadById(
            @Parameter(description = "ID of the payload to retrieve")
            @PathVariable Long id) {

        PayloadRes payload = payloadService.findById(id);
        return ResponseEntity.ok(payload);
    }

    @Operation(summary = "Create",
            description = "Create a new payload",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payload created")
            })
    @PostMapping
    public ResponseEntity<String> create(
            @Parameter(description = "Request containing ")
            @Valid @RequestBody PayloadReq payloadReq
    ) {
        payloadService.create(payloadReq);
        return ResponseEntity.status(HttpStatus.OK).body("Payload created successfully!");
    }

    @Operation(summary = "Update",
            description = "Update an existing payload",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payload updated")
            })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @Parameter(description = "Request containing name, weight, dimensions, state, pallet ID and transporter ID")
            @PathVariable Long id,
            @Valid @RequestBody PayloadReq payloadReq
    ) {
        payloadService.update(id, payloadReq);
        return ResponseEntity.status(HttpStatus.OK).body("Payload updated successfully!");
    }

    @Operation(summary = "Update State",
            description = "Update the state of an existing payload",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payload updated")
            })
    @PatchMapping("/state/{id}")
    public ResponseEntity<String> updateState(
            @Parameter(description = "Request containing state (PENDING, IN_TRANSIT, DELIVERED, DAMAGED)")
            @PathVariable Long id,
            @Valid @RequestBody PayloadState state
    ) {
        payloadService.updateState(id, state);
        return ResponseEntity.status(HttpStatus.OK).body("Payload state updated successfully!");
    }

    @Operation(summary = "Delete",
            description = "Delete an existing payload",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payload deleted")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "ID of the payload to delete")
            @PathVariable Long id
    ) {
        payloadService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Payload deleted successfully!");
    }

}
