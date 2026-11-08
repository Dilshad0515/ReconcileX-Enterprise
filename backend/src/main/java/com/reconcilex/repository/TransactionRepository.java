
package com.reconcilex.repository;

import com.reconcilex.entity.SourceTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository
                extends JpaRepository<SourceTransaction, Long>,
                JpaSpecificationExecutor<SourceTransaction> {
}
