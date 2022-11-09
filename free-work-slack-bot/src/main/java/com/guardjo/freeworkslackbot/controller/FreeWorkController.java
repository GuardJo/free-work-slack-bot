package com.guardjo.freeworkslackbot.controller;

import com.guardjo.freeworkslackbot.constant.FreeWorkConstant;
import com.guardjo.freeworkslackbot.domain.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class FreeWorkController {
    @PutMapping(FreeWorkConstant.WORK_START_URL)
    public void startWork() {
        log.info("[Test] Work Start!");
    }

    @PutMapping(FreeWorkConstant.WORK_FINISH_URL)
    public void finishWork() {
        log.info("[Test] Work Finish!");
    }

    @GetMapping(FreeWorkConstant.WORK_STATUS_URL)
    public Worker findOneWorker() {
        log.info("[Test] Worker Status");

        return Worker.of("test");
    }

    @GetMapping(FreeWorkConstant.WORK_TEAM_STATUS)
    public List<Worker> findAllWorkers() {
        log.info("[Test] All Worker Status");
        return List.of(Worker.of("test"));
    }
}
