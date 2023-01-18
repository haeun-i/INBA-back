package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Slf4j
@Component
@RequiredArgsConstructor
public class WebSockChatHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        boolean start_flag = true;
        String str = ""; // 보내 줄 내용

        if(start_flag){
            str = "당신은 [컴퓨터공학과][단일전공] 이군요! \n졸업 조건으로 [영어점수, 졸업고사 응시]가 필요합니다. \n졸업 기준의 영어 공인 시험 성적을 만족 하셨나요?";
            start_flag = false;
       }
        if(message.getPayload().equals("네")){
            str = "졸업 고사 응시 면제 여부를 확인하겠습니다. \n전공학점이 3.3 이상인가요?";
        }


        TextMessage textMessage = new TextMessage(str);

        session.sendMessage(textMessage);
    }


}