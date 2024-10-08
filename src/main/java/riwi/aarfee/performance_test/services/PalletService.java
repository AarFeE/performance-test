package riwi.aarfee.performance_test.services;

import org.springframework.boot.json.JsonParseException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import riwi.aarfee.performance_test.dtos.requests.PalletReq;
import riwi.aarfee.performance_test.dtos.responses.PalletRes;
import riwi.aarfee.performance_test.dtos.responses.PayloadRes;
import riwi.aarfee.performance_test.entitites.PalletEntity;
import riwi.aarfee.performance_test.entitites.PayloadEntity;
import riwi.aarfee.performance_test.enums.PalletState;
import riwi.aarfee.performance_test.exceptions.customs.GenericException;
import riwi.aarfee.performance_test.exceptions.customs.ResourceNotFoundException;
import riwi.aarfee.performance_test.repositories.PalletRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PalletService {

    private final PalletRepository palletRepository;

    public PalletService(PalletRepository palletRepository) {
        this.palletRepository = palletRepository;
    }

    public PalletRes findById(Long id) {
        Optional<PalletEntity> palletOptional = palletRepository.findById(id);
        if (palletOptional.isEmpty()) {
            throw new ResourceNotFoundException("Pallet not found");
        }

        PalletEntity pallet = palletOptional.get();

        return new PalletRes(pallet);
    }

    public List<PalletRes> findAll() {
        return palletRepository.findAll().stream()
                .map(PalletRes::new)
                .collect(Collectors.toList());
    }

    public List<PayloadRes> getPayloadsById(Long id) {

        PalletEntity pallet = palletRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pallet not found"));

        if(pallet.getState().equals(PalletState.DAMAGED)) {
            throw new GenericException("The pallet is damaged");
        }

        return pallet.getPayloads().stream()
                .map(PayloadRes::new)
                .collect(Collectors.toList());
    }

    public void create(PalletReq palletReq) {

        PalletEntity pallet = new PalletEntity();
        pallet.setMaterial(palletReq.getMaterial());
        pallet.setMaxWeight(palletReq.getMaxWeight());
        pallet.setLocation(palletReq.getLocation());
        pallet.setState(palletReq.getState());

        palletRepository.save(pallet);
    }

    public void update(Long id, PalletReq palletReq) {

        PalletEntity pallet = palletRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pallet not found"));

        pallet.setMaterial(palletReq.getMaterial());
        pallet.setMaxWeight(palletReq.getMaxWeight());
        pallet.setLocation(palletReq.getLocation());
        pallet.setState(palletReq.getState());

        palletRepository.save(pallet);
    }

    public void updateState(Long id, PalletState state) {

        PalletEntity pallet = palletRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pallet not found"));

        try {
            pallet.setState(state);
        } catch (HttpMessageNotReadableException e) {
            throw new GenericException("Invalid state");
        }

        palletRepository.save(pallet);
    }

    public void delete(Long id) {

        palletRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pallet not found"));

        palletRepository.deleteById(id);
    }
 }
