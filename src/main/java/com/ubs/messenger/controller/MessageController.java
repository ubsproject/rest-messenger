package com.ubs.messenger.controller;

import com.ubs.messenger.api.InputMessage;
import com.ubs.messenger.service.impl.MessageTypeRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class MessageController {

    @Autowired
    private MessageTypeRouter messageRouter;

    @RequestMapping(method = RequestMethod.POST, path = "/send")
    ResponseEntity<?> send(@Valid @RequestBody InputMessage msg) {
        messageRouter.route(msg);

        //FIXME - replace by proper message id
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(1)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
