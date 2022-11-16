package com.guardjo.freeworkslackbot.service;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 두 요청 시간에 대한 차이 계산
 */
@Component
public class TimeCalculator {
    public float calculateTime(Date date1, Date date2) {
        float result = (float) (TimeUnit.MINUTES.convert(
                Math.abs(date1.getTime() - date2.getTime()),
                TimeUnit.MILLISECONDS) / 60.0);

        return (float) (Math.round(result * 10) / 10.0);
    }
}
