package org.libra.data.repositories;

import org.libra.data.entities.PriceDiffSignalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceDiffSignalRepository extends JpaRepository<PriceDiffSignalEntity, Long> {

    Long countBySymbol(String symbol);

    Optional<PriceDiffSignalEntity> findTopBySymbolOrderByDateDesc(String symbol);
}
