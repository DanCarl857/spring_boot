package com.example.repository;

import com.example.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by daniel on 1/8/17.
 */
@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long> {

}
