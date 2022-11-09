package com.guardjo.freeworkslackbot.controller;

import com.guardjo.freeworkslackbot.constant.FreeWorkConstant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FreeWorkController.class)
class FreeWorkControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("/work-start 요청 테스트")
    @Test
    void testWorkStart() throws Exception {
        mockMvc.perform(put(FreeWorkConstant.WORK_START_URL))
                .andExpect(status().isOk());
    }

    @DisplayName("/work-finish 요청 테스트")
    @Test
    void testWorkFinish() throws Exception {
        mockMvc.perform(put(FreeWorkConstant.WORK_FINISH_URL))
                .andExpect(status().isOk());
    }

    @DisplayName("/status 요청 테스트")
    @Test
    void testGetStatus() throws Exception {
        mockMvc.perform(get(FreeWorkConstant.WORK_STATUS_URL).param("name", "test"))
                .andExpect(status().isOk());
    }

    @DisplayName("/team-status 요청 테스트")
    @Test
    void testGetTeamStatus() throws Exception {
        mockMvc.perform(get(FreeWorkConstant.WORK_TEAM_STATUS))
                .andExpect(status().isOk());
    }
}