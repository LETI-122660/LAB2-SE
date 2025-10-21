package com.example.pdf;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.pdf.Pdf;
interface PdfRepository extends JpaRepository<Pdf, Long>, JpaSpecificationExecutor<Pdf> {

    Slice<Pdf> findAllBy(Pageable pageable);
}


