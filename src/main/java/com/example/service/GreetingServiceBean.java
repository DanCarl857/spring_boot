package com.example.service;

import com.example.model.Greeting;
import com.example.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by daniel on 1/8/17.
 */
@Service
@Transactional(
        propagation = Propagation.SUPPORTS,
        readOnly = true)
public class GreetingServiceBean implements GreetingService {

    @Autowired
    private GreetingRepository greetingRepository;

    @Override
    public Collection<Greeting> findAll() {
        Collection<Greeting> greetings = greetingRepository.findAll();
        return greetings;
    }

    @Override
    @Cacheable(
            value = "greetings",
            key = "#id")
    public Greeting findOne(Long id) {
        Greeting greeting = greetingRepository.findOne(id);
        return greeting;
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    @CachePut(
            value="greetings",
            key = "#result.id")
    public Greeting create(Greeting greeting) {

        if(greeting.getId() != null){
            // Cannot create Greeting with specific ID value
            return null;
        }

        Greeting savedGreeting = greetingRepository.save(greeting);

        // Illustration Tx rollback
        if(savedGreeting.getId() == 4L){
            throw new RuntimeException("Roll me back");
        }
        return savedGreeting;
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    @CachePut(
            value="greetings",
            key="#greeting.id")
    public Greeting update(Greeting greeting){

        Greeting greetingPersisted = findOne(greeting.getId());
        if(greetingPersisted == null){
            // Cannot update Greeting that hasn't been created yet
            return null;
        }
        Greeting updatedGreeting = greetingRepository.save(greeting);
        return updatedGreeting;
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    @CacheEvict(
            value = "greetings",
            key="#id")
    public void delete(Long id) {
        greetingRepository.delete(id);
    }

    @Override
    @CacheEvict(
            value = "greetings",
            allEntries = true)
    public void evictCache(){

    }

}
