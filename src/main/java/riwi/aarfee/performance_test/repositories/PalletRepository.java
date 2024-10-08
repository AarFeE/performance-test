package riwi.aarfee.performance_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import riwi.aarfee.performance_test.entitites.PalletEntity;

import java.util.Optional;

@Repository
public interface PalletRepository extends JpaRepository<PalletEntity, Long> {

    @Query("SELECT SUM(p.weight) FROM PayloadEntity p WHERE p.pallet.id = :palletId")
    Optional<Double> getWeightByPalletId(Long palletId);

}
