package com.guardjo.freeworkslackbot.service;

import com.guardjo.freeworkslackbot.domain.Worker;
import com.guardjo.freeworkslackbot.repository.WorkerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class WorkerServiceTest {
    private final String TEST_NAME = "testName";
    @Mock
    private WorkerRepository workerRepository;
    @Mock
    private TimeCalculator timeCalculator;
    @InjectMocks
    private WorkerServiceImpl workerService;

    @DisplayName("worker 생성 테스트")
    @Test
    void testCreateWorker() {
        assertThatCode(() -> workerService.createWorker(TEST_NAME))
                .doesNotThrowAnyException();
    }

    @DisplayName("worker 삭제 테스트")
    @Test
    void testDeleteWorker() {
        assertThatCode(() -> workerService.deleteWorker(TEST_NAME))
                .doesNotThrowAnyException();
    }

    @DisplayName("출근 기록 갱신 테스트")
    @Test
    void testUpdateWorkStart() {
        Worker worker = Worker.of(TEST_NAME);

        given(workerRepository.findByName(any())).willReturn(List.of(worker));
        workerService.startWork(TEST_NAME);

        Worker updateWorker = workerService.getWorker(TEST_NAME);

        assertThat(updateWorker.getWorkStartTime()).isNotNull();
    }

    @DisplayName("퇴근 기록 및 업무시간 기록 테스트")
    @Test
    void testUpdateWorkFinish() {
        Worker worker = Worker.of(TEST_NAME);
        worker.setWorkStartTime(new Date());
        given(workerRepository.findByName(any())).willReturn(List.of(worker));
        given(timeCalculator.calculateTime(any(), any())).willReturn(8.0F);

        assertThat(workerService.finishWork(TEST_NAME)).isEqualTo(8.0F);
    }

    @DisplayName("특정 사용자의 업무 기록 조회 테스트")
    @Test
    void testGetWorker() {
        given(workerRepository.findByName(any())).willReturn(List.of(Worker.of(TEST_NAME)));

        Worker worker = workerService.getWorker(TEST_NAME);

        assertThat(worker.getName()).isEqualTo(TEST_NAME);
    }

    @DisplayName("모든 사용자의 업무 기록 조회 테스트")
    @Test
    void testGetWorkers() {
        given(workerRepository.findAll()).willReturn(List.of(Worker.of(TEST_NAME)));

        assertThatCode(() -> workerService.getWorkers()).doesNotThrowAnyException();
    }
}