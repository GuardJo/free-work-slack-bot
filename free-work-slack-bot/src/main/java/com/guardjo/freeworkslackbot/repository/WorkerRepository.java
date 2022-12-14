package com.guardjo.freeworkslackbot.repository;

import com.guardjo.freeworkslackbot.domain.Worker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepository extends MongoRepository<Worker, String> {
    List<Worker> findByName(String name);
    List<Worker> deleteByName(String name);
}
