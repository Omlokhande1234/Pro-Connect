package com.Pro_Connect.ConnectionService.service;

import com.Pro_Connect.ConnectionService.auth.AuthContextHolder;
import com.Pro_Connect.ConnectionService.entity.Person;
import com.Pro_Connect.ConnectionService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionService {
    private final PersonRepository personRepository;
    public List<Person> getFirstDegreeConnectionsOfUser(Long userId){
        log.info("get First Degree Connections Of User with id: {}", userId);

        return personRepository.getFirstDegreeConnections(userId);
    }

    public void sendConnectionRequest(Long receiverId){
        log.info("send Connection Request with id: {}", receiverId);
        Long senderId = AuthContextHolder.getCurrentUserId();
        log.info("senderId = {}", senderId);
        if(senderId.equals(receiverId)){
            throw new RuntimeException("Sender and Receiver are the same");
        }

        boolean alreadySendRequest = personRepository.connectionRequestExists(senderId,receiverId);
        if(alreadySendRequest){
            throw new RuntimeException("Connection request already exists, cannot send again");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId,receiverId);
        if(alreadyConnected){
            throw new RuntimeException("Already connected users, cannot add connection request");
        }

        log.info("Successfully send Connection Request with id: {}", receiverId);
        personRepository.addConnectionRequest(senderId,receiverId);
    }

    public void acceptConnectionRequest(Long senderId){
        Long receiverId = AuthContextHolder.getCurrentUserId();

        if(senderId.equals(receiverId)){
            throw new RuntimeException("Sender and Receiver are the same");
        }
        boolean alreadyConnected = personRepository.alreadyConnected(senderId,receiverId);
        if(alreadyConnected){
            throw new RuntimeException("Already connected users, cannot add connection request");
        }
        personRepository.acceptConnectionRequest(senderId,receiverId);
        log.info("Successfully accept Connection Request with id: {}", receiverId);
    }

    public void rejectConnectionRequest(Long senderId){
        Long receiverId = AuthContextHolder.getCurrentUserId();

        log.info("reject Connection Request with id: {}", receiverId);

        if(senderId.equals(receiverId)){
            throw new RuntimeException("Sender and Receiver are the same");
        }
        boolean alreadySendRequest = personRepository.connectionRequestExists(senderId,receiverId);
        if(!alreadySendRequest){
          throw new RuntimeException("No Connection request exists, cannot accept without request");
        }
        personRepository.rejectConnectionRequest(senderId,receiverId);

        log.info("Successfully reject Connection Request with id: {}", receiverId);
    }

}
