
package com.reconcilex.repository;

import com.reconcilex.entity.ImportBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportBatchRepository
        extends JpaRepository<ImportBatch, Long> {

    List<ImportBatch> findByBatchStatus(String batchStatus);

    List<ImportBatch> findByDataSourceId(Long dataSourceId);
}
