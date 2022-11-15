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
    public String startWork(@RequestBody UpdateWorker updateWorker) {
        log.info("[Test] Work Start!");

        workerService.startWork(updateWorker.getWorkerName());

        return updateWorker.getWorkerName() + "님이 출근하셨습니다.";
    }

    @PutMapping(FreeWorkConstant.WORK_FINISH_URL)
    public String finishWork(@RequestBody UpdateWorker updateWorker) {
        log.info("[Test] Work Finish!");

        float workTime = workerService.finishWork(updateWorker.getWorkerName());
        float weeklyTime = workerService.getWorker(updateWorker.getWorkerName()).getWeeklyWorkTime();

        return updateWorker.getWorkerName() + "님이 퇴근하셨습니다, 금일 업무시간은 " +
                workTime +  "/" + weeklyTime + "시간 입니다.";
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
