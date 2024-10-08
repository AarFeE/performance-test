package riwi.aarfee.performance_test.services;

import org.springframework.stereotype.Service;
import riwi.aarfee.performance_test.dtos.responses.PayloadRes;
import riwi.aarfee.performance_test.entitites.UserEntity;
import riwi.aarfee.performance_test.enums.UserRole;
import riwi.aarfee.performance_test.exceptions.customs.GenericException;
import riwi.aarfee.performance_test.exceptions.customs.ResourceNotFoundException;
import riwi.aarfee.performance_test.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<PayloadRes> getPayloadsById(Long id) {

        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(user.getRole().equals(UserRole.ADMIN)) {
            throw new GenericException("The user must be a transporter, but found an ADMIN");
        }

        return user.getPayloads().stream().map(PayloadRes::new).collect(Collectors.toList());
    }
}
