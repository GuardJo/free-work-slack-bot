package com.guardjo.freeworkslackbot.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SlackBotRequest {
    private String token;
    private String team_id;
    private String team_domain;
    private String enterprise_id;
    private String enterprise_name;
    private String channel_id;
    private String channel_name;
    private String user_id;
    private String user_name;
    private String command;
    private String text;
    private String response_url;
    private String trigger_id;
    private String api_app_id;
}
