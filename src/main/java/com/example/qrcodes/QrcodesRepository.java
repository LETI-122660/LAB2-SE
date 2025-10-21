package com.example.qrcodes;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QrcodesRepository extends JpaRepository<Qrcodes, Long>, JpaSpecificationExecutor<Qrcodes> {
    Slice<Qrcodes> findAllBy(Pageable pageable);
}