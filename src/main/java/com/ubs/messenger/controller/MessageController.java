package com.ubs.messenger.controller;

import com.ubs.messenger.api.InputMessage;
import com.ubs.messenger.domain.Message;
import com.ubs.messenger.service.CacheRepository;
import com.ubs.messenger.service.MessageRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageRouter messageRouter;

    @Autowired
    private CacheRepository cacheRepository;

    @RequestMapping(method = RequestMethod.POST, path = "/messages")
    public ResponseEntity<Message> send(@Valid @RequestBody InputMessage msg) {
        Message sentMessage = messageRouter.route(msg);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(sentMessage.hashCode())
            .toUri();
        return ResponseEntity.created(location).body(sentMessage);
    }

    @RequestMapping("/messages")
    public List messages(){
        return cacheRepository.getAllEntries();
    }
}
