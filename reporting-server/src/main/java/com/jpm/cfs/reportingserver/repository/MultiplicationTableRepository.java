package com.jpm.cfs.reportingserver.repository;

import com.jpm.cfs.reportingserver.entity.MultiplicationTable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MultiplicationTableRepository extends ReactiveCrudRepository<MultiplicationTable, Integer> {

    @Query("select * from multiplication_table m where m.number = :number")
    Flux<MultiplicationTable> findByNumber(int number);
}
