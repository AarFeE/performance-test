package riwi.aarfee.performance_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import riwi.aarfee.performance_test.entitites.PayloadEntity;

@Repository
public interface PayloadRepository extends JpaRepository<PayloadEntity, Long> {
}
