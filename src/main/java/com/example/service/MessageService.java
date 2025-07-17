package com.example.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;

@Service
public class MessageService{
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) throws IllegalArgumentException{
        int messageLength =  message.getMessageText().length();
        if(0 == messageLength){
            throw new IllegalArgumentException("Message cannnot be empty");
        }
        else if(messageLength > 255){
            throw new IllegalArgumentException("Message must be under 255 characters");
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int id){
        try{
        return messageRepository.findById(id).get();
        }
        catch(NoSuchElementException e){
            System.out.println(e);
        }
        return null;
    }

    public int deleteMessageById(int id){
        return messageRepository.deleteMessageById(id);
    }

    public int updateMessageById(int id, Message message) throws IllegalArgumentException{
        String messageText = message.getMessageText();
        int messageLength = messageText.length();
        if(messageLength == 0){
            throw new IllegalArgumentException("Message cannnot be empty");
        }
        else if(messageLength > 255){
            throw new IllegalArgumentException("Message must be under 255 characters");
        }
        
        int updated =  messageRepository.updateMessageById(id, messageText);
        if(updated == 0){
            throw new IllegalArgumentException("No message found");
        }
        return updated;
    }

    public List<Message> getAllMessagesByUserId(int id){
        return messageRepository.findAllByUserId(id);
    }

}
