package com.uniovi.sdi2324entrega181.repositories;

import com.uniovi.sdi2324entrega181.entities.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LogRepository extends CrudRepository<Log, Long> {
    @Query("SELECT l FROM Log l ORDER BY l.date DESC")
    Page<Log> findAllOrdered(Pageable pageable);
    @Query("SELECT l FROM Log l WHERE l.type = 'PET' ORDER BY l.date DESC")
    Page<Log> findPETOrdered(Pageable pageable);
    @Query("SELECT l FROM Log l WHERE l.type = 'ALTA' ORDER BY l.date DESC")
    Page<Log> findALTAOrdered(Pageable pageable);
    @Query("SELECT l FROM Log l WHERE l.type = 'LOGIN-EX' ORDER BY l.date DESC")
    Page<Log> findLOGINEXOrdered(Pageable pageable);
    @Query("SELECT l FROM Log l WHERE l.type = 'LOGIN-ERR' ORDER BY l.date DESC")
    Page<Log> findLOGINERROrdered(Pageable pageable);
    @Query("SELECT l FROM Log l WHERE l.type = 'LOGOUT' ORDER BY l.date DESC")
    Page<Log> findLOGOUTOrdered(Pageable pageable);
}
