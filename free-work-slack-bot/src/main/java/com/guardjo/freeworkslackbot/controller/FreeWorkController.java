package com.guardjo.freeworkslackbot.controller;

import com.guardjo.freeworkslackbot.constant.FreeWorkConstant;
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

    @PostMapping(FreeWorkConstant.WORK_START_URL)
    public String startWork(@RequestHeader(FreeWorkConstant.USER_ID) String userId) throws Exception {
        log.info("[Test] Work Start!");

        workerService.startWork(userId);

        return userId + "님이 출근하셨습니다.";
    }

    @PostMapping(FreeWorkConstant.WORK_FINISH_URL)
    public String finishWork(@RequestHeader(FreeWorkConstant.USER_ID) String userId) throws Exception {
        log.info("[Test] Work Finish!");

        float workTime = workerService.finishWork(userId);
        float weeklyTime = workerService.getWorker(userId).getWeeklyWorkTime();

        return userId + "님이 퇴근하셨습니다, 금일 업무시간은 " +
                workTime +  "/" + weeklyTime + "시간 입니다.";
    }

    @PostMapping(FreeWorkConstant.RESET_TODAY)
    public String resetTodayWorkTime(@RequestHeader(FreeWorkConstant.USER_ID) String userId) throws Exception {
        log.info("[Test] Reset Today Work Time, {}", userId);

        workerService.resetTodayWorkTime(userId);

        return "Reset Today Work Time";
    }

    @PostMapping(FreeWorkConstant.RESET_WEEK)
    public String resetWeeklyWorkTime(@RequestHeader(FreeWorkConstant.USER_ID) String userId) throws Exception {
        log.info("[Test] Reset Weekly Work Time, {}", userId);

        workerService.resetWeeklyWorkTime(userId);

        return "Reset Weekly Work Time";
    }

    @PostMapping(FreeWorkConstant.WORK_STATUS_URL)
    public Worker findOneWorker(@RequestHeader(FreeWorkConstant.USER_ID) String userId) throws Exception {
        log.info("[Test] Worker Status");

        return workerService.getWorker(userId);
    }

    @PostMapping(FreeWorkConstant.WORK_TEAM_STATUS)
    public List<Worker> findAllWorkers() throws Exception {
        log.info("[Test] All Worker Status");

        return workerService.getWorkers();
    }

    @PostMapping(FreeWorkConstant.DELETE_URL)
    public String deleteWorker(@RequestHeader(FreeWorkConstant.USER_ID) String userId) throws Exception {
        log.info("[Test] Delete Worker, {}", userId);

        Worker worker = workerService.deleteWorker(userId);

        return "Deleted Worker " + worker.getName();
    }
}
