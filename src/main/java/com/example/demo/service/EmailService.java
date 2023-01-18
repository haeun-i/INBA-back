package com.example.demo.service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService{

    @Autowired
    JavaMailSender emailSender;

    private MimeMessage createMessage(String to, Integer pw)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ pw);
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("인하대학교 졸업 수강지도 서비스 이메일 인증");//제목

        String msgg="";
        msgg+= "<div align='center' style='margin:30px; color:black;'>";
        msgg+= "<h2 align='left' style='margin-left:10%;'>인증메일</h2>";
        msgg+= "<hr style='background-color:#000000; height:2px; width:80%;'/>";
        msgg+= "<br>";
        msgg+= "<br>";
        msgg+= "인하대학교 졸업 수강지도 사이트 회원 가입을 위한 인증번호 발송 메일입니다.\n";
        msgg+= "<br>";
        msgg+= "<br>";
        msgg+= "아래의 인증번호를 사용하여 이메일 주소 인증을 완료하면 다음 단계로 진행이 가능합니다.\n";
        msgg+= "<br>";
        msgg+= "<br>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:none; height: 50px;\n" +
                "   line-height: 50px; background-color:lightgrey; width:80%'>";
        msgg+= "<div style='font-size:130%;'>";
        msgg+= "<strong>"+pw+"</strong><div><br/></div>";

        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("gkdms1120s@gmail.com","INBA"));//보내는 사람

        return message;
    }


    public String sendSimpleMessage(String to, Integer pw)throws Exception {
        // TODO Auto-generated method stub
        MimeMessage message = createMessage(to, pw);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return pw.toString();
    }

}
