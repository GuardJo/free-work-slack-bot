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
    public String startWork(@RequestBody UpdateWorker updateWorker) throws Exception {
        log.info("[Test] Work Start!");

        workerService.startWork(updateWorker.getWorkerName());

        return updateWorker.getWorkerName() + "님이 출근하셨습니다.";
    }

    @PutMapping(FreeWorkConstant.WORK_FINISH_URL)
    public String finishWork(@RequestBody UpdateWorker updateWorker) throws Exception {
        log.info("[Test] Work Finish!");

        float workTime = workerService.finishWork(updateWorker.getWorkerName());
        float weeklyTime = workerService.getWorker(updateWorker.getWorkerName()).getWeeklyWorkTime();

        return updateWorker.getWorkerName() + "님이 퇴근하셨습니다, 금일 업무시간은 " +
                workTime +  "/" + weeklyTime + "시간 입니다.";
    }

    @PutMapping(FreeWorkConstant.RESET_TODAY)
    public String resetTodayWorkTime(@RequestBody UpdateWorker updateWorker) throws Exception {
        log.info("[Test] Reset Today Work Time, {}", updateWorker.getWorkerName());

        workerService.resetTodayWorkTime(updateWorker.getWorkerName());

        return "Reset Today Work Time";
    }

    @PutMapping(FreeWorkConstant.RESET_WEEK)
    public String resetWeeklyWorkTime(@RequestBody UpdateWorker updateWorker) throws Exception {
        log.info("[Test] Reset Weekly Work Time, {}", updateWorker.getWorkerName());

        workerService.resetWeeklyWorkTime(updateWorker.getWorkerName());

        return "Reset Weekly Work Time";
    }

    @GetMapping(FreeWorkConstant.WORK_STATUS_URL)
    public Worker findOneWorker(@RequestParam("name") String name) throws Exception {
        log.info("[Test] Worker Status");

        return workerService.getWorker(name);
    }

    @GetMapping(FreeWorkConstant.WORK_TEAM_STATUS)
    public List<Worker> findAllWorkers() throws Exception {
        log.info("[Test] All Worker Status");

        return workerService.getWorkers();
    }
}
