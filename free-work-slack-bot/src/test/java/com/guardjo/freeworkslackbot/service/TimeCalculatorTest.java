package com.guardjo.freeworkslackbot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TimeCalculatorTest {
    @DisplayName("두 시간 사이의 차이값 반환 테스트")
    @Test
    void testCalculateTime() throws ParseException {
        String startTimeStr = "2022-11-15 01:00:00.000";
        String finishTimeSTr = "2022-11-15 09:31:00.000";

        Date startTime = generateStartTime(startTimeStr);
        Date finishTime = generateStartTime(finishTimeSTr);

        TimeCalculator timeCalculator = new TimeCalculator();
        float calcTime = timeCalculator.calculateTime(startTime, finishTime);

        assertThat(calcTime).isEqualTo(8.5F);
    }

    private Date generateStartTime(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        return simpleDateFormat.parse(dateStr);
    }
}