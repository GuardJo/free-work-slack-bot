package com.guardjo.freeworkslackbot.controller;

import com.guardjo.freeworkslackbot.constant.FreeWorkConstant;
import com.guardjo.freeworkslackbot.domain.UpdateWorker;
import com.guardjo.freeworkslackbot.domain.Worker;
import com.guardjo.freeworkslackbot.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class FreeWorkController {
    @Autowired
    private WorkerService workerService;

    @PutMapping(FreeWorkConstant.WORK_START_URL)
    public void startWork(@RequestBody UpdateWorker updateWorker) {
        log.info("[Test] Work Start!");

        workerService.startWork(updateWorker.getWorkerName());
    }

    @PutMapping(FreeWorkConstant.WORK_FINISH_URL)
    public void finishWork(@RequestBody UpdateWorker updateWorker) {
        log.info("[Test] Work Finish!");

        workerService.finishWork(updateWorker.getWorkerName());
    }

    @GetMapping(FreeWorkConstant.WORK_STATUS_URL)
    public Worker findOneWorker(@RequestParam("name") String name) {
        log.info("[Test] Worker Status");

        return workerService.getWorker(name);
    }

    @GetMapping(FreeWorkConstant.WORK_TEAM_STATUS)
    public List<Worker> findAllWorkers() {
        log.info("[Test] All Worker Status");

        return workerService.getWorkers();
    }
}
