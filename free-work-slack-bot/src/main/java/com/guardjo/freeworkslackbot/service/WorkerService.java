package com.guardjo.freeworkslackbot.service;

import com.guardjo.freeworkslackbot.domain.Worker;

import java.util.List;

public interface WorkerService {
    Worker createWorker(String workerName) throws Exception;
    Worker deleteWorker(String workerName) throws Exception;
    void startWork(String workerName) throws Exception;
    float finishWork(String workerName) throws Exception;
    void resetTodayWorkTime(String workerName) throws Exception;
    void resetWeeklyWorkTime(String workerName) throws Exception;
    Worker getWorker(String workerName) throws Exception;
    List<Worker> getWorkers() throws Exception;
}
