
package com.reconcilex.repository;

import com.reconcilex.entity.DataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataSourceRepository
        extends JpaRepository<DataSource, Long> {

    Optional<DataSource> findBySourceCode(String sourceCode);

    boolean existsBySourceCode(String sourceCode);
}
