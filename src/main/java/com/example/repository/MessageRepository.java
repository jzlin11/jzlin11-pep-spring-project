package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Message WHERE messageId = :messageId")
    public int deleteMessageById(@Param("messageId") int messageId);

    @Modifying
    @Transactional
    @Query("UPDATE Message SET messageText =:messageText WHERE messageId = :messageId")
    public int updateMessageById(@Param("messageId") int messageId, @Param("messageText") String messageText);

    @Query("FROM Message WHERE postedBy = :id")
    public List<Message> findAllByUserId(@Param("id")int id);
}
