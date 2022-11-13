package com.guardjo.freeworkslackbot.service;

import com.guardjo.freeworkslackbot.domain.Worker;

import java.util.List;

public interface WorkerService {
    Worker createWorker(String workerName);
    Worker deleteWorker(String workerName);
    void startWork(String workerName);
    void finishWork(String workerName);
    void resetWorkTime(String workerName);
    Worker getWorker(String workerName);
    List<Worker> getWorkers();
}
