package com.guardjo.freeworkslackbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guardjo.freeworkslackbot.constant.FreeWorkConstant;
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
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @MethodSource("testUserIdHeaders")
    void testWorkStart(String slackUserId) throws Exception {
        mockMvc.perform(post(FreeWorkConstant.WORK_START_URL)
                        .header(FreeWorkConstant.USER_ID, slackUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(isNotError())
                .andDo(print());
    }

    @DisplayName("/work-finish 요청 테스트")
    @ParameterizedTest
    @MethodSource("testUserIdHeaders")
    void testWorkFinish(String slackUserId) throws Exception {
        mockMvc.perform(post(FreeWorkConstant.WORK_FINISH_URL)
                        .header(FreeWorkConstant.USER_ID, slackUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(isNotError())
                .andDo(print());
    }

    @DisplayName("/worker 요청 테스트")
    @ParameterizedTest
    @MethodSource("testUserIdHeaders")
    void testGetStatus(String slackUserId) throws Exception {
        mockMvc.perform(post(FreeWorkConstant.WORK_STATUS_URL)
                        .header(FreeWorkConstant.USER_ID, slackUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(isNotError())
                .andDo(print());
    }

    @DisplayName("/worker/list 요청 테스트")
    @Test
    void testGetTeamStatus() throws Exception {
        mockMvc.perform(post(FreeWorkConstant.WORK_TEAM_STATUS))
                .andExpect(status().isOk())
                .andExpect(isNotError())
                .andDo(print());
    }

    @DisplayName("/reset-today, /reset-week 요청 테스트")
    @ParameterizedTest
    @MethodSource("resetURL")
    void testPutResetTime(String resetUrl) throws Exception {
        mockMvc.perform(post(FreeWorkConstant.WORK_FINISH_URL)
                        .header(FreeWorkConstant.USER_ID, "testId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(isNotError())
                .andDo(print());
    }

    @DisplayName("/delete-worker 요청 테스트")
    @ParameterizedTest
    @MethodSource("testUserIdHeaders")
    void testDeleteWorker(String slackUserId) throws Exception {
        mockMvc.perform(post(FreeWorkConstant.DELETE_URL)
                        .header(FreeWorkConstant.USER_ID, slackUserId))
                .andExpect(status().isOk())
                .andExpect(isNotError())
                .andDo(print());
    }

    private static Stream<Arguments> testUserIdHeaders() {
        return Stream.of(
                Arguments.of("slack-id-01"),
                Arguments.of("slack-id-02")
        );
    }

    private static Stream<Arguments> resetURL() {
        return Stream.of(
                Arguments.of(FreeWorkConstant.RESET_TODAY),
                Arguments.of(FreeWorkConstant.RESET_WEEK)
        );
    }

    /**
     * 요청에 대한 응답값이 Error일 경우 판별
     * @return Error 문자열이 들어가지 않으면 정상 응답으로 판단
     */
    private ResultMatcher isNotError() {
        return result -> assertThat(result.getResponse().getContentAsString().contains("Error")).isFalse();
    }
}