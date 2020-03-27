package com.pluralsight.conferencedemo.controllers;


import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {
    @Autowired
    private SessionRepository sessionRepository;

    //list all
    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }
    //get id
    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id) {
        return sessionRepository.getOne(id);
    }
    //create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //retrieve body and create session object
    public Session create(@RequestBody final Session session) {
        //save and flush to save and "commit" to db
        return sessionRepository.saveAndFlush(session);
    }
    //delete
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        sessionRepository.deleteById(id);
    }
    //update
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        //PUT updates all attributes and PATCH only specific attributes
        Session existingSession = sessionRepository.getOne(id);
        //ignoring primary key
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }
}
