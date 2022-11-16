package com.guardjo.freeworkslackbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guardjo.freeworkslackbot.constant.FreeWorkConstant;
import com.guardjo.freeworkslackbot.domain.UpdateWorker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class FreeWorkControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("/work-start 요청 테스트")
    @ParameterizedTest
    @MethodSource("updateWorkerBody")
    void testWorkStart(UpdateWorker updateWorkDTO) throws Exception {
        String content = objectMapper.writeValueAsString(updateWorkDTO);

        mockMvc.perform(put(FreeWorkConstant.WORK_START_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("/work-finish 요청 테스트")
    @ParameterizedTest
    @MethodSource("updateWorkerBody")
    void testWorkFinish(UpdateWorker updateWorkDTO) throws Exception {
        String content = objectMapper.writeValueAsString(updateWorkDTO);

        mockMvc.perform(put(FreeWorkConstant.WORK_FINISH_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("/status 요청 테스트")
    @Test
    void testGetStatus() throws Exception {
        mockMvc.perform(get(FreeWorkConstant.WORK_STATUS_URL)
                        .param("name", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("/team-status 요청 테스트")
    @Test
    void testGetTeamStatus() throws Exception {
        mockMvc.perform(get(FreeWorkConstant.WORK_TEAM_STATUS))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("/reset-today, /reset-week 요청 테스트")
    @ParameterizedTest
    @MethodSource("resetURL")
    void testPutResetTime(String resetUrl) throws Exception {
        String content = objectMapper.writeValueAsString(UpdateWorker.of("testName"));

        mockMvc.perform(put(FreeWorkConstant.WORK_FINISH_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private static Stream<Arguments> updateWorkerBody() {
        return Stream.of(
                Arguments.of(UpdateWorker.of("testName")),
                Arguments.of(UpdateWorker.of("Rouvin Kendell"))
        );
    }

    private static Stream<Arguments> resetURL() {
        return Stream.of(
                Arguments.of(FreeWorkConstant.RESET_TODAY),
                Arguments.of(FreeWorkConstant.RESET_WEEK)
        );
    }
}