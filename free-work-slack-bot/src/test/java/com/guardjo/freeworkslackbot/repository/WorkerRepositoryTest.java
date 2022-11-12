package com.guardjo.freeworkslackbot.repository;

import com.guardjo.freeworkslackbot.config.DataInitConfig;
import com.guardjo.freeworkslackbot.domain.Worker;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Import(DataInitConfig.class)
@DataMongoTest
class WorkerRepositoryTest {
    private final int TEST_DATA_SIZE = 100;
    @Autowired
    private WorkerRepository workerRepository;

    @DisplayName("작업자 생성 테스트")
    @Test
    void testCreateWorker() {
        workerRepository.save(Worker.of("TestUser"));

        assertThat(workerRepository.count()).isEqualTo(TEST_DATA_SIZE + 1);
    }

    @DisplayName("작업자 단일 조회 테스트")
    @Test
    void testGetWorker() {
        Worker worker = workerRepository.findAll().get(0);

        assertThat(worker).isNotNull();
    }

    @DisplayName("작업자 다수 조회 테스트")
    @Test
    void testGetWorkerList() {
        List<Worker> workers = workerRepository.findAll();

        assertThat(workers.size()).isEqualTo(TEST_DATA_SIZE);
    }

    @DisplayName("일일 근무 시간 업데이트 테스트")
    @Test
    void testUpdateDailyWorkTime() {
        Worker worker = workerRepository.findAll().get(0);
        worker.setWeeklyWorkTime(1);

        workerRepository.save(worker);

        Worker updateWorker = workerRepository.findById(worker.getId()).get();

        assertThat(updateWorker.getWeeklyWorkTime()).isEqualTo(1);
    }

    @DisplayName("작업자 제거 테스트")
    @Test
    void testDeleteWorker() {
        int oldSize = (int) workerRepository.count();
        ObjectId objectId = workerRepository.findAll().get(0).getId();
        workerRepository.deleteById(objectId);

        assertThat(workerRepository.count()).isEqualTo(oldSize - 1);
    }
}