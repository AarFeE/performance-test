package riwi.aarfee.performance_test.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import riwi.aarfee.performance_test.entitites.PalletEntity;
import riwi.aarfee.performance_test.entitites.PayloadEntity;
import riwi.aarfee.performance_test.entitites.UserEntity;
import riwi.aarfee.performance_test.enums.PalletState;
import riwi.aarfee.performance_test.enums.PayloadState;
import riwi.aarfee.performance_test.enums.UserRole;
import riwi.aarfee.performance_test.repositories.PalletRepository;
import riwi.aarfee.performance_test.repositories.PayloadRepository;
import riwi.aarfee.performance_test.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PalletRepository palletRepository;
    private final PayloadRepository payloadRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PalletRepository palletRepository, PayloadRepository payloadRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.palletRepository = palletRepository;
        this.payloadRepository = payloadRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Declare users and pallets variables
        UserEntity user1 = null;
        UserEntity user2 = null;
        Set<PalletEntity> pallets = null;

        if (userRepository.count() == 0) {
            user1 = UserEntity.builder()
                    .name("User One")
                    .email("test@test.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(UserRole.TRANSPORTER)
                    .build();

            user2 = UserEntity.builder()
                    .name("User Two")
                    .email("user2@example.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(UserRole.ADMIN)
                    .build();

            userRepository.save(user1);
            userRepository.save(user2);
        }

        if (palletRepository.count() == 0) {
            pallets = new HashSet<>();
            for (int i = 1; i <= 5; i++) {
                PalletEntity pallet = PalletEntity.builder()
                        .material("Material " + i)
                        .maxWeight(100.0 + i * 10)
                        .location("Location " + i)
                        .state(PalletState.AVAILABLE)
                        .build();
                pallets.add(pallet);
            }
            palletRepository.saveAll(pallets);
        }

        if (payloadRepository.count() == 0) {
            Set<PayloadEntity> payloads = new HashSet<>();
            if (user1 != null && pallets != null) {
                for (int i = 1; i <= 2; i++) {
                    PayloadEntity payload = PayloadEntity.builder()
                            .name("Payload " + i)
                            .weight(50.0 + i * 10)
                            .dimensions("10x10x10")
                            .state(PayloadState.PENDING)
                            .pallet(pallets.iterator().next())
                            .transporter(user1)
                            .build();
                    payloads.add(payload);
                }
                payloadRepository.saveAll(payloads);
            }
        }
    }
}
