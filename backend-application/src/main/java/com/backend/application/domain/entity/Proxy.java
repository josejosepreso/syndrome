package com.backend.application.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import jakarta.persistence.Column;

@Entity
@Table(name = "history", schema = "sch_proxies")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Proxy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;

    private Integer port;

    private String source;

    @Column(name = "registered_by")
    private String registeredBy;

    @Column(name = "created_at")
	private Date createdAt;
}
