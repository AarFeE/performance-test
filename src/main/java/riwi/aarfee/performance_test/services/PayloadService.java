package riwi.aarfee.performance_test.services;

import org.springframework.boot.json.JsonParseException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import riwi.aarfee.performance_test.dtos.requests.PayloadReq;
import riwi.aarfee.performance_test.dtos.responses.PayloadRes;
import riwi.aarfee.performance_test.entitites.PalletEntity;
import riwi.aarfee.performance_test.entitites.PayloadEntity;
import riwi.aarfee.performance_test.entitites.UserEntity;
import riwi.aarfee.performance_test.enums.PayloadState;
import riwi.aarfee.performance_test.enums.UserRole;
import riwi.aarfee.performance_test.exceptions.customs.GenericException;
import riwi.aarfee.performance_test.exceptions.customs.ResourceNotFoundException;
import riwi.aarfee.performance_test.repositories.PalletRepository;
import riwi.aarfee.performance_test.repositories.PayloadRepository;
import riwi.aarfee.performance_test.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PayloadService {

    private final PayloadRepository payloadRepository;
    private final PalletRepository palletRepository;
    private final UserRepository userRepository;

    public PayloadService(PayloadRepository payloadRepository, PalletRepository palletRepository, UserRepository userRepository) {
        this.payloadRepository = payloadRepository;
        this.palletRepository = palletRepository;
        this.userRepository = userRepository;
    }

    public PayloadRes findById(Long id) {
        Optional<PayloadEntity> payloadOptional = payloadRepository.findById(id);
        if (payloadOptional.isEmpty()) {
            throw new ResourceNotFoundException("Payload not found");
        }

        PayloadEntity payload = payloadOptional.get();
        return new PayloadRes(payload);
    }

    public List<PayloadRes> findAll() {
        return payloadRepository.findAll().stream()
                .map(PayloadRes::new)
                .collect(Collectors.toList());
    }

    public void create(PayloadReq payloadReq) {

        PalletEntity pallet = palletRepository.findById(payloadReq.getPalletId()).orElseThrow(() -> new ResourceNotFoundException("Pallet not found"));
        UserEntity transporter = userRepository.findById(payloadReq.getTransporterId()).orElseThrow(() -> new ResourceNotFoundException("Transporter not found"));

        if(transporter.getRole().equals(UserRole.ADMIN)) {
            throw new GenericException("The user must be a transporter, but found an ADMIN");
        }

        if(payloadReq.getWeight() > (pallet.getMaxWeight() - palletRepository.getWeightByPalletId(pallet.getId()).orElse(0.0))) {
            throw new GenericException("The weight of the payload exceeds the maximum weight of the pallet");
        }

        PayloadEntity payload = new PayloadEntity();
        payload.setName(payloadReq.getName());
        payload.setWeight(payloadReq.getWeight());
        payload.setDimensions(payloadReq.getDimensions());
        payload.setState(payloadReq.getState());
        payload.setPallet(pallet);
        payload.setTransporter(transporter);

        payloadRepository.save(payload);
    }

    public void update(Long id, PayloadReq payloadReq) {

        PayloadEntity payload = payloadRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payload not found"));
        PalletEntity pallet = palletRepository.findById(payloadReq.getPalletId()).orElseThrow(() -> new ResourceNotFoundException("Pallet not found"));
        UserEntity transporter = userRepository.findById(payloadReq.getTransporterId()).orElseThrow(() -> new ResourceNotFoundException("Transporter not found"));

        if(transporter.getRole().equals(UserRole.ADMIN)) {
            throw new GenericException("The user must be a transporter, but found an ADMIN");
        }

        if(payloadReq.getWeight() > (pallet.getMaxWeight() - palletRepository.getWeightByPalletId(pallet.getId()).orElse(0.0))) {
            throw new GenericException("The weight of the payload exceeds the maximum weight of the pallet");
        }

        payload.setName(payloadReq.getName());
        payload.setWeight(payloadReq.getWeight());
        payload.setDimensions(payloadReq.getDimensions());
        payload.setState(payloadReq.getState());
        payload.setPallet(pallet);
        payload.setTransporter(transporter);

        payloadRepository.save(payload);
    }

    public void updateState(Long id, PayloadState state) {

        PayloadEntity payload = payloadRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payload not found"));

        try {
            payload.setState(state);
        } catch (HttpMessageNotReadableException e) {
            throw new GenericException("Invalid state");
        }

        payloadRepository.save(payload);
    }

    public void delete(Long id) {

        payloadRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payload not found"));

        payloadRepository.deleteById(id);
    }
}
