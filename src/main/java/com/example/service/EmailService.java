package com.example.service;

import com.example.model.Greeting;

import java.util.concurrent.Future;

/**
 * Created by daniel on 1/8/17.
 */
public interface EmailService {

    Boolean send(Greeting greeting);

    void sendAsync(Greeting greeting);

    Future<Boolean> sendAsyncWithResult(Greeting greeting);
}
