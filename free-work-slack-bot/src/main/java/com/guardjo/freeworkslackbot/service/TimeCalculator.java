package com.guardjo.freeworkslackbot.service;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 두 요청 시간에 대한 차이 계산
 */
@Component
public class TimeCalculator {
    /**
     * 두 시간 사이의 차이값을 반환
     * @param startDate 앞선 시간값
     * @param finishDate 이후 시간값
     * @return 두 시간 값의 차를 분 단위까지 절사해서 시간단위로 반환한다
     */
    public float calculateTime(Date startDate, Date finishDate) {
        float result = (float) (TimeUnit.MINUTES.convert(
                Math.abs(startDate.getTime() - finishDate.getTime()),
                TimeUnit.MILLISECONDS) / 60.0);

        if (afterLunchTime(finishDate)) {
            // 점심시간 이후에 퇴근 할 경우 점심시간 (1H) 만큼 제외
            result -= 1;
        }

        return (float) (Math.round(result * 10) / 10.0);
    }

    /**
     * 요청 시각이 점심 시간 이후 여부 판단
     * @return 점심 시간 이후의 시각이면 True, 아니면 False
     */
    private boolean afterLunchTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        return hour > 12;
    }
}
