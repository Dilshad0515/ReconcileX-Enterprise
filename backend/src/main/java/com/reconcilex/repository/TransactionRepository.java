
package com.reconcilex.repository;

import com.reconcilex.entity.SourceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository
        extends JpaRepository<SourceTransaction, Long> {

    List<SourceTransaction> findByImportBatchId(Long importBatchId);

    List<SourceTransaction> findBySourceTransactionRef(
            String sourceTransactionRef);

    List<SourceTransaction> findByTransactionStatus(
            String transactionStatus);
}
