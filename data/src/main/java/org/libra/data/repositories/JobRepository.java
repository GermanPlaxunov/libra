package org.libra.data.repositories;

import org.libra.data.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<JobEntity, Long> {

    List<JobEntity> findAllByIsActive(boolean isActive);

}
