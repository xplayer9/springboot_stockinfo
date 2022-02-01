package com.stockApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stockApp.model.StockModel;


@Repository
public interface StockRepository extends JpaRepository<StockModel, Long> {
	
	@Query(value="select * from stocktable a where a.name = :name", nativeQuery=true)
    List<StockModel> listAll(String name);
	
	@Query(value="select exists(select 1 from tradetable where name = :name)", nativeQuery=true)
    boolean exists(String name);
	
	@Modifying
	@Transactional
	@Query(value="delete from stocktable where stock = :sym", nativeQuery=true)
    void deleteSymbol(String sym);
	
	/*
	@Modifying
	@Transactional
	@Query(value="INSERT INTO tradetable VALUES (:name)", nativeQuery=true)
    void insertTable(String name);
    */
}





