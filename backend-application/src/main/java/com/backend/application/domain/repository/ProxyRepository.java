package com.backend.application.domain.repository;

import com.backend.application.domain.entity.Proxy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProxyRepository extends JpaRepository<Proxy, Long> {
}
