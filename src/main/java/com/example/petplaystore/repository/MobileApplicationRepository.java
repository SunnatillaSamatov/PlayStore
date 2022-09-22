package com.example.petplaystore.repository;

import com.example.petplaystore.entity.MobileApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileApplicationRepository extends JpaRepository<MobileApplication, Long> {
}
