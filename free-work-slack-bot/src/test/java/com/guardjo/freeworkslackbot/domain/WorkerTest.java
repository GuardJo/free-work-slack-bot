package com.guardjo.freeworkslackbot.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class WorkerTest {
    @DisplayName("Worker 객체 생성 테스트")
    @Test
    void testCreateWorker() {
        assertThatCode(() -> Worker.of("testWorker")).doesNotThrowAnyException();
    }
}