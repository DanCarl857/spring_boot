package com.example.service;

import com.example.model.Greeting;

import java.util.Collection;

/**
 * Created by daniel on 1/8/17.
 */
public interface GreetingService {

    Collection<Greeting> findAll();

    Greeting findOne(Long id);

    Greeting create(Greeting greeting);

    Greeting update(Greeting greeting);

    void delete(Long id);

    void evictCache();
}
