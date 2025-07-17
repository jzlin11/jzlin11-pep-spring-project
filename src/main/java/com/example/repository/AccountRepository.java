package com.example.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    boolean existsByUsername(String username);

    Account getByUsername(String username);
    
    boolean existsById(int id);
}
