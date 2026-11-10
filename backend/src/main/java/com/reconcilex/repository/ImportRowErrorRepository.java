package com.reconcilex.repository;

import com.reconcilex.entity.ImportRowError;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportRowErrorRepository
        extends JpaRepository<ImportRowError, Long> {

    List<ImportRowError> findByImportBatch_Id(Long importBatchId);

    long countByImportBatch_Id(Long importBatchId);
}