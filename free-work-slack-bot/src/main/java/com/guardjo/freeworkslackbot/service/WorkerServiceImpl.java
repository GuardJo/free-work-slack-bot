package com.guardjo.freeworkslackbot.service;

import com.guardjo.freeworkslackbot.domain.Worker;
import com.guardjo.freeworkslackbot.repository.WorkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private TimeCalculator timeCalculator;

    @Override
    public Worker createWorker(String workerName) throws Exception {
        Worker worker = getWorker(workerName);

        if (worker != null) {
            log.warn("Already Exist Worker {}", workerName);
            return worker;
        }

        worker = Worker.of(workerName);
        workerRepository.save(worker);
        log.info("Created new Worker {}", workerName);

        return worker;
    }

    @Override
    public Worker deleteWorker(String workerName) throws Exception {
        Worker worker = getWorker(workerName);

        if (worker == null) {
            log.warn("[Test] Not Found Worker {}", workerName);
            return null;
        }

        workerRepository.delete(worker);
        log.info("Deleted new Worker {}", workerName);

        return worker;
    }

    @Override
    public void startWork(String workerName) throws Exception {
        Worker worker = getWorker(workerName);

        if (worker == null) {
            worker = createWorker(workerName);
        }

        worker.setWorkStartTime(new Date());

        workerRepository.save(worker);

        log.info("[Test] Updated WorkStartTime, ", worker.getWorkStartTime());
    }

    @Override
    public float finishWork(String workerName) throws Exception {
        Worker worker = getWorker(workerName);

        if (worker == null) {
            log.error("[Test] Not Found Work Start Time", workerName);

            // 올바르지 않은 시간 값이나, 사용자가 입력으로 들어온 경우
            return -1;
        }
        else {
            worker.setWorkFinishedTime(new Date());
            worker.setTodayWorkTime(
                    timeCalculator.calculateTime(worker.getWorkStartTime(), worker.getWorkFinishedTime())
            );

            worker.setWeeklyWorkTime(worker.getWeeklyWorkTime() + worker.getTodayWorkTime());
            log.info("[Test] Update WorkTime, todayWorkTime : {}, weeklyWorkTime : {}"
                    , worker.getTodayWorkTime(), worker.getWeeklyWorkTime());

            workerRepository.save(worker);

            return worker.getTodayWorkTime();
        }
    }

    @Override
    public void resetTodayWorkTime(String workerName) throws Exception {
        Worker worker = getWorker(workerName);

        if (worker == null) {
            log.error("[Test] Not Found Work Start Time", workerName);
        }
        else {
            worker.setTodayWorkTime(0);
            worker.setWorkStartTime(null);
            worker.setWorkFinishedTime(null);

            workerRepository.save(worker);

            log.info("[Test] Reset Time of Worker {}", workerName);
        }
    }

    @Override
    public void resetWeeklyWorkTime(String workerName) throws Exception {
        Worker worker = getWorker(workerName);

        if (worker == null) {
            log.error("[Test] Not Found Work Start Time", workerName);
        }
        else {
            worker.setWeeklyWorkTime(0);
            worker.setTodayWorkTime(0);
            worker.setWorkStartTime(null);
            worker.setWorkFinishedTime(null);

            workerRepository.save(worker);

            log.info("[Test] Reset Time of Worker {}", workerName);
        }
    }

    @Override
    public Worker getWorker(String workerName) throws Exception {
        List<Worker> workers = workerRepository.findByName(workerName);

        if (workers.isEmpty()) {
            log.warn("[Test] Not Found worker, {}", workerName);
            return null;
        }

        log.info("[Test] Founded Worker {}", workerName);

        return workers.get(0);
    }

    @Override
    public List<Worker> getWorkers() throws Exception {
        List<Worker> workers = workerRepository.findAll();

        if (workers.isEmpty()) {
            log.info("[Test] Not Found Any Workers");
            return null;
        }

        return workers;
    }
}
