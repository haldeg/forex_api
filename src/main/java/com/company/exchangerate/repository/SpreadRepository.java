package com.company.exchangerate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.exchangerate.model.Spread;

@Repository
public interface SpreadRepository extends JpaRepository<Spread, String> {

}
