package riwi.aarfee.performance_test.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import riwi.aarfee.performance_test.dtos.requests.PalletReq;
import riwi.aarfee.performance_test.dtos.responses.PalletRes;
import riwi.aarfee.performance_test.dtos.responses.PayloadRes;
import riwi.aarfee.performance_test.enums.PalletState;
import riwi.aarfee.performance_test.services.PalletService;

import java.util.List;

@Tag(name = "Pallets")
@RestController
@RequestMapping("/api/pallets")
@Validated
public class PalletController {

    private final PalletService palletService;

    @Autowired
    public PalletController(PalletService palletService) {
        this.palletService = palletService;
    }

    @Operation(summary = "Find By ID",
            description = "Search for a pallet by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pallet found"),
                    @ApiResponse(responseCode = "404", description = "Pallet not found")
            })
    @GetMapping("/{id}")
    public ResponseEntity<PalletRes> getPalletById(
            @Parameter(description = "ID of the pallet to retrieve")
            @PathVariable Long id) {

        PalletRes pallet = palletService.findById(id);
        return ResponseEntity.ok(pallet);
    }

    @Operation(summary = "Find All",
            description = "Retrieve all pallets",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pallets retrieved")
            })
    @GetMapping
    public ResponseEntity<List<PalletRes>> getAllPallets() {
        List<PalletRes> pallets = palletService.findAll();
        return ResponseEntity.ok(pallets);
    }

    @Operation(summary = "Find Pallet's Payloads",
            description = "Retrieve all payloads of a pallet by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payloads retrieved")
            })
    @GetMapping("/{id}/payloads")
    public ResponseEntity<List<PayloadRes>> getPalletPayloads(
            @Parameter(description = "ID of the pallet to retrieve payloads")
            @PathVariable Long id
    ) {
        List<PayloadRes> payloads = palletService.getPayloadsById(id);
        return ResponseEntity.ok(payloads);
    }

    @Operation(summary = "Create",
            description = "Create a new pallet",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pallet created")
            })
    @PostMapping
    public ResponseEntity<String> createPallet(
            @Parameter(description = "Request containing material, max weight, location, and state")
            @Valid @RequestBody PalletReq palletReq
    ) {
        palletService.create(palletReq);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pallet created successfully!");
    }

    @Operation(summary = "Update",
            description = "Update an existing pallet",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pallet updated")
            })
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePallet(
            @Parameter(description = "Request containing material, max weight, location, and state")
            @PathVariable Long id,
            @Valid @RequestBody PalletReq palletReq
    ) {
        palletService.update(id, palletReq);
        return ResponseEntity.status(HttpStatus.OK).body("Pallet updated successfully!");
    }

    @Operation(summary = "Update State",
            description = "Update the state of an existing pallet",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pallet updated")
            })
    @PatchMapping("/state/{id}/")
    public ResponseEntity<String> updatePalletState(
            @Parameter(description = "Request containing state AVAILABLE, IN_USE, DAMAGED, OUT_OF_SERVICE")
            @PathVariable Long id,
            @Valid @RequestBody PalletState state
    ) {
        palletService.updateState(id, state);
        return ResponseEntity.status(HttpStatus.OK).body("Pallet state updated successfully!");
    }

    @Operation(summary = "Delete",
            description = "Delete an existing pallet",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pallet deleted")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePallet(
            @Parameter(description = "ID of the pallet to delete")
            @PathVariable Long id
    ) {
        palletService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Pallet deleted successfully!");
    }
}
