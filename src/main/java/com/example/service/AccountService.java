package com.example.service;


import javax.security.sasl.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account newUser(Account account) throws IllegalArgumentException, DataIntegrityViolationException{
        String userName = account.getUsername();
        if(userName == null || userName.length() < 4)
        {
            throw new IllegalArgumentException("Must be a username with more than 4 characters");
        }
        if (accountRepository.existsByUsername(userName))
        {
            throw new DataIntegrityViolationException("Duplicate username found");
        }
        return accountRepository.save(account);    

    }

    public Account userLogin(Account account) throws AuthenticationException{
        Account account2 = accountRepository.getByUsername(account.getUsername());
        if(account2 == null ){
            throw new AuthenticationException("Username not found");
        }
        if(account2.getPassword().equals(account.getPassword()) == false) {
            throw new AuthenticationException("Invalid password");
        }
        return account2;
    }

    public boolean validAccount(int id){
        return accountRepository.existsById(id);
    }
}
