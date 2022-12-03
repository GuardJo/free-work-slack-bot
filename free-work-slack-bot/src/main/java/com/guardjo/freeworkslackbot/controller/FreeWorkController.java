package com.guardjo.freeworkslackbot.controller;

import com.guardjo.freeworkslackbot.constant.FreeWorkConstant;
import com.guardjo.freeworkslackbot.domain.SlackBotRequest;
import com.guardjo.freeworkslackbot.domain.Worker;
import com.guardjo.freeworkslackbot.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class FreeWorkController {
    @Autowired
    private WorkerService workerService;

    @PostMapping(value = FreeWorkConstant.WORK_START_URL, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String startWork(SlackBotRequest slackBotRequest) throws Exception {
        log.info("Work Start!");

        workerService.startWork(slackBotRequest.getUser_name());

        return slackBotRequest.getUser_name() + "님이 출근하셨습니다.";
    }

    @PostMapping(value = FreeWorkConstant.WORK_FINISH_URL, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String finishWork(SlackBotRequest slackBotRequest) throws Exception {
        log.info("Work Finish!");

        float workTime = workerService.finishWork(slackBotRequest.getUser_name());
        float weeklyTime = workerService.getWorker(slackBotRequest.getUser_name()).getWeeklyWorkTime();

        return slackBotRequest.getUser_name() + "님이 퇴근하셨습니다, 금일 업무시간은 " +
                workTime +  "/" + weeklyTime + "시간 입니다.";
    }

    @PostMapping(value = FreeWorkConstant.RESET_TODAY, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String resetTodayWorkTime(SlackBotRequest slackBotRequest) throws Exception {
        log.info("Reset Today Work Time, {}", slackBotRequest.getUser_name());

        workerService.resetTodayWorkTime(slackBotRequest.getUser_name());

        return "Reset Today Work Time";
    }

    @PostMapping(value = FreeWorkConstant.RESET_WEEK, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String resetWeeklyWorkTime(SlackBotRequest slackBotRequest) throws Exception {
        log.info("Reset Weekly Work Time, {}", slackBotRequest.getUser_name());

        workerService.resetWeeklyWorkTime(slackBotRequest.getUser_name());

        return "Reset Weekly Work Time";
    }

    @PostMapping(value = FreeWorkConstant.WORK_STATUS_URL, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Worker findOneWorker(SlackBotRequest slackBotRequest) throws Exception {
        log.info("Worker Status");

        return workerService.getWorker(slackBotRequest.getUser_name());
    }

    @PostMapping(FreeWorkConstant.WORK_TEAM_STATUS)
    public List<Worker> findAllWorkers() throws Exception {
        log.info("All Worker Status");

        return workerService.getWorkers();
    }

    @PostMapping(value = FreeWorkConstant.DELETE_URL, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String deleteWorker(SlackBotRequest slackBotRequest) throws Exception {
        log.info("Delete Worker, {}", slackBotRequest.getUser_name());

        Worker worker = workerService.deleteWorker(slackBotRequest.getUser_name());

        return "Deleted Worker " + worker.getName();
    }
}
