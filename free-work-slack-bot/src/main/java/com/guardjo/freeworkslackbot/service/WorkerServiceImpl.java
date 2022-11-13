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
    @Override
    public Worker createWorker(String workerName) {
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
    public Worker deleteWorker(String workerName) {
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
    public void startWork(String workerName) {
        Worker worker = getWorker(workerName);

        if (worker == null) {
            worker = createWorker(workerName);
        }

        worker.setWorkStartTime(new Date());

        workerRepository.save(worker);

        log.info("[Test] Updated WorkStartTime, ", worker.getWorkStartTime());
    }

    @Override
    public void finishWork(String workerName) {
        Worker worker = getWorker(workerName);

        if (worker == null) {
            log.error("[Test] Not Found Work Start Time", workerName);
        }
        else {
            worker.setWorkFinishedTime(new Date());
            calculateWorkTime(worker);
            log.info("[Test] Update WorkTime, todayWorkTime : {}, weeklyWorkTime : {}"
                    , worker.getTodayWorkTime(), worker.getWeeklyWorkTime());

            workerRepository.save(worker);
        }
    }

    @Override
    public void resetWorkTime(String workerName) {
        Worker worker = getWorker(workerName);

        if (worker == null) {
            log.error("[Test] Not Found Work Start Time", workerName);
        }
        else {
            worker.setWeeklyWorkTime(0);
            worker.setTodayWorkTime(0);
            worker.setWorkStartTime(null);
            worker.setWorkFinishedTime(null);

            log.info("[Test] Reset Time of Worker {}", workerName);
        }
    }

    @Override
    public Worker getWorker(String workerName) {
        List<Worker> workers = workerRepository.findByName(workerName);

        if (workers.isEmpty()) {
            log.warn("[Test] Not Found worker, {}", workerName);
            return null;
        }

        log.info("[Test] Founded Worker {}", workerName);

        return workers.get(0);
    }

    @Override
    public List<Worker> getWorkers() {
        List<Worker> workers = workerRepository.findAll();

        if (workers.isEmpty()) {
            log.info("[Test] Not Found Any Workers");
            return null;
        }

        return workers;
    }

    /**
     * 출, 퇴근 기록을 바탕으로 일일 근무시간 및 주간 근무시간을 계산한다
     * @param worker 직원
     */
    private void calculateWorkTime(Worker worker) {
        Date startTime = worker.getWorkStartTime();
        Date finishTime = worker.getWorkFinishedTime();

        int todayWorkTime = (int) (finishTime.getTime() - startTime.getTime());

        worker.setTodayWorkTime(todayWorkTime);
        worker.setWeeklyWorkTime(worker.getWeeklyWorkTime() + todayWorkTime);
    }
}
