package org.example.barlasvoyage_backend.repository;

import org.example.barlasvoyage_backend.entity.Days;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface DaysRepository extends JpaRepository<Days, UUID> {
}
