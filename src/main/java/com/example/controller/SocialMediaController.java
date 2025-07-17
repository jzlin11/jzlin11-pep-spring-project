package com.example.controller;

import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.dao.DataIntegrityViolationException;

import com.example.service.AccountService;
import com.example.service.MessageService;

import com.example.entity.Account;
import com.example.entity.Message;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;
  
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account){ 
        return ResponseEntity.status(200).body(accountService.newUser(account));
    }

    @PostMapping("login")
    public ResponseEntity<Account> userLogin(@RequestBody Account account) throws AuthenticationException{
        return ResponseEntity.status(200).body(accountService.userLogin(account));
    }

    @PostMapping("messages")
    public ResponseEntity<Message> newMessage(@RequestBody Message message) throws IllegalArgumentException{
        if(!accountService.validAccount(message.getPostedBy())){
            throw new IllegalArgumentException("Not a User");
        }
        return ResponseEntity.status(200).body(messageService.createMessage(message));
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable("messageId") int id){
        return ResponseEntity.status(200).body(messageService.getMessageById(id));
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUserId(@PathVariable("accountId") int id){
        return ResponseEntity.status(200).body(messageService.getAllMessagesByUserId(id));
    }
    
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable("messageId") int id){
        int deleted = messageService.deleteMessageById(id);
        if(deleted == 0){
            return ResponseEntity.status(200).body(null);
        }
        return ResponseEntity.status(200).body(deleted);
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable("messageId") int id, @RequestBody Message message){
        return ResponseEntity.status(200).body(messageService.updateMessageById(id, message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> clientSideErrorHandler(IllegalArgumentException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> duplicateUserNameHandler(DataIntegrityViolationException e){
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> invalidLoginHandler(AuthenticationException e){
        return ResponseEntity.status(401).body("Unauthorized");
    }

}
