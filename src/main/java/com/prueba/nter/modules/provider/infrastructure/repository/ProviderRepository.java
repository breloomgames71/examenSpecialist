package com.prueba.nter.modules.provider.infrastructure.repository;

import com.prueba.nter.modules.provider.domain.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<ProviderEntity, Long> {
}
