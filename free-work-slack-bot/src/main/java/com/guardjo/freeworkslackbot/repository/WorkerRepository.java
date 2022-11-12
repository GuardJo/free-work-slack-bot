package com.guardjo.freeworkslackbot.repository;

import com.guardjo.freeworkslackbot.domain.Worker;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends MongoRepository<Worker, ObjectId> {
}
