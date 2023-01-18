package com.example.demo.service;

import com.example.demo.domain.Standard;
import com.example.demo.domain.Subject;
import com.example.demo.domain.User;
import com.example.demo.domain.UserSubject;
import com.example.demo.dto.SubjectDto;
import com.example.demo.dto.SubjectStatus;
import com.example.demo.dto.UserStatus;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.demo.dto.MajorStatus.컴퓨터공학과;

@Service
@RequiredArgsConstructor
public class ListService {

    private final UserRepository userRepository;
    private final UserSubjectRepository userSubjectRepository;
    private final StandardRepository standardRepository;
    private final SubjectRepository subjectRepository;

    public List<SubjectDto> requireMajorList(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserEmail(authentication.getName()).get();
        String userNum = user.getUserNum().substring(2,4);

        // 컴퓨터공학과 전공필수 목록 가져오기
        Standard standard = standardRepository.findByMajorStatusAndStudentStatusAndStudentYear(컴퓨터공학과, UserStatus.SINGLE, userNum).get();
        String ess_list = standard.getEssList();
        List<Subject> parsingSubjectList = parsing(ess_list);

        // 전체 전공필수에서 유저가 수강한 컴퓨터공학과 전공필수 삭제
        List<UserSubject> userSubjectList = userSubjectRepository.findAllBySubjectStatusAndMajorStatusAndUser(SubjectStatus.전공필수, 컴퓨터공학과, user);
        List<Subject> subjectList = userSubjectDelete(parsingSubjectList, userSubjectList);

        if(user.getUserSmajor() != 컴퓨터공학과){ // 복부전이 있는 경우

            // 복부전과 전공필수 리스트 가져오기
            Standard standard2 = standardRepository.findByMajorStatusAndStudentStatusAndStudentYear(user.getUserSmajor(), user.getUserStatus(), userNum).get();
            String ess_list2 = standard2.getEssList();
            List<Subject> parsingSubjectList2 = parsing(ess_list2);

            // 유저가 수강한 복수전공 전공필수 리스트
            List<UserSubject> userSubjectList2 = userSubjectRepository.findAllBySubjectStatusAndMajorStatusAndUser(SubjectStatus.전공필수, user.getUserSmajor(), user);
            List<Subject> subjectList2 = userSubjectDelete(parsingSubjectList2, userSubjectList2);

            subjectList.addAll(subjectList2);
        }

        // 폐강되고 대체 과목이 생긴 경우 -> 지금 만들어진 과목으로 대체
        closeSubjectDelete(subjectList);

        Collections.sort(subjectList, new Comparator<Subject>() {
            @Override
            public int compare(Subject o1, Subject o2) {
                return o1.getSubjectTime().compareTo(o2.getSubjectTime());
            }
        });

        return toDtoList(subjectList);
    }

    public List<SubjectDto> chooseComputerMajorList(){ // 컴퓨터공학과 전공선택 리스트

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserEmail(authentication.getName()).get();

        // 컴퓨터공학과 전공선택 목록 가져오기
        Standard standard = standardRepository.findByMajorStatusAndStudentStatusAndStudentYear(컴퓨터공학과, UserStatus.SINGLE, "22").get();
        String maj_list = standard.getMajList();
        List<Subject> parsingSubjectList = parsing(maj_list);

        // 유저가 수강한 컴퓨터공학과 전공선택 리스트
        List<UserSubject> userSubjectList = userSubjectRepository.findAllBySubjectStatusAndMajorStatusAndUser(SubjectStatus.전공선택, 컴퓨터공학과, user);
        List<Subject> subjectList = userSubjectDelete(parsingSubjectList, userSubjectList);

        Collections.sort(subjectList, new Comparator<Subject>() {
            @Override
            public int compare(Subject o1, Subject o2) {
                return o1.getSubjectTime().compareTo(o2.getSubjectTime());
            }
        });

        return toDtoList(subjectList);
    }

    public List<SubjectDto> chooseSecondMajorList(){ // 컴퓨터공학과 전공선택 리스트

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserEmail(authentication.getName()).get();

        // 다중전공 전공선택 목록 가져오기
        Standard standard = standardRepository.findByMajorStatusAndStudentStatusAndStudentYear(user.getUserSmajor(), user.getUserStatus(), "22").get();
        String maj_list = standard.getMajList();
        List<Subject> parsingSubjectList = parsing(maj_list);

        // 유저가 수강한 컴퓨터공학과 전공선택 리스트
        List<UserSubject> userSubjectList = userSubjectRepository.findAllBySubjectStatusAndMajorStatusAndUser(SubjectStatus.전공선택, user.getUserSmajor(), user);
        List<Subject> subjectList = userSubjectDelete(parsingSubjectList, userSubjectList);

        Collections.sort(subjectList, new Comparator<Subject>() {
            @Override
            public int compare(Subject o1, Subject o2) {
                return o1.getSubjectTime().compareTo(o2.getSubjectTime());
            }
        });

        return toDtoList(subjectList);
    }

    public List<SubjectDto> requireMinorList(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserEmail(authentication.getName()).get();
        String userNum = user.getUserNum().substring(2,4);

        // 컴퓨터공학과 교양필수 목록 가져오기
        Standard standard = standardRepository.findByMajorStatusAndStudentStatusAndStudentYear(컴퓨터공학과, UserStatus.SINGLE, userNum).get();
        String edu_list = standard.getEduList();
        List<Subject> parsingSubjectList = parsing(edu_list);

        // 유저가 수강한 컴퓨터공학과 전공선택 리스트
        List<UserSubject> userSubjectList = userSubjectRepository.findAllBySubjectStatusAndMajorStatusAndUser(SubjectStatus.교양필수, 컴퓨터공학과, user);
        List<Subject> subjectList = userSubjectDelete(parsingSubjectList, userSubjectList);

        if(user.getUserSmajor() != 컴퓨터공학과){ // 복부전이 있는 경우

            // 복부전과 전공필수 리스트 가져오기
            Standard standard2 = standardRepository.findByMajorStatusAndStudentStatusAndStudentYear(user.getUserSmajor(), user.getUserStatus(), userNum).get();
            String ess_list2 = standard2.getEssList();
            List<Subject> parsingSubjectList2 = parsing(ess_list2);

            // 유저가 수강한 복수전공 전공필수 리스트
            List<UserSubject> userSubjectList2 = userSubjectRepository.findAllBySubjectStatusAndMajorStatusAndUser(SubjectStatus.교양필수, user.getUserSmajor(), user);
            List<Subject> subjectList2 = userSubjectDelete(parsingSubjectList2, userSubjectList2);
            subjectList.addAll(subjectList2);
        }

        // 폐강되고 대체 과목이 생긴 경우 -> 지금 만들어진 과목으로 대체
        closeSubjectDelete(subjectList);

        Collections.sort(subjectList, new Comparator<Subject>() {
            @Override
            public int compare(Subject o1, Subject o2) {
                return o1.getSubjectTime().compareTo(o2.getSubjectTime());
            }
        });

        return toDtoList(subjectList);
    }

    // 비정규화로 가져온 과목 리스트 parsing
    public List<Subject> parsing(String list){
        String[] ess_list_str = list.split("/");
        List<Subject> subjectList = new ArrayList<>();
        for(String s : ess_list_str){
            subjectList.add(subjectRepository.findById(Long.valueOf(s)).get());
        }
        return subjectList;
    }

    // 유저가 수강한 과목 삭제하기
    public List<Subject> userSubjectDelete(List<Subject> subjectList, List<UserSubject> userSubjectList){
        // 사용자가 해당 과목을 이수하였다면 리스트에서 삭제
        for(UserSubject u : userSubjectList){
            Subject stt = subjectRepository.findBySubjectNum(u.getSubjectNum()).get();
            if(stt.getSubjectGroup() != 0){
                // 들어야 하는 과목 리스트에서 해당 그룹군 모두 삭제
                List<Subject> deleteList = subjectRepository.findAllBySubjectGroup(stt.getSubjectGroup());
                for(Subject s2 : deleteList){
                    subjectList.removeIf(item -> item.equals(s2));
                }
            }else{ // 아니라면 해당 과목 삭제
                subjectList.removeIf(item -> item.equals(stt));
            }
        }
        return subjectList;
    }

    // 사용자에게 보여주기 위한 dtoList로의 변환
    public List<SubjectDto> toDtoList(List<Subject> subjects){
        List<SubjectDto> subjectDtos = new ArrayList<>();
        for(Subject s : subjects){
            SubjectDto subjectDto = SubjectDto.builder()
                    .subjectId(s.getSubjectId())
                    .subjectNum(s.getSubjectNum())
                    .subjectName(s.getSubjectName())
                    .subjectTime(s.getSubjectTime())
                    .subjectScore(s.getSubjectScore())
                    .build();
            subjectDtos.add(subjectDto);
        }
        return subjectDtos;
    }

    // 폐강된 과목 삭제하기
    public void closeSubjectDelete(List<Subject> subjectList){
        List<Subject> subjectAddList = new ArrayList<>();
        List<Subject> subjectSubList = new ArrayList<>();
        for(Subject st2 : subjectList){
            if(st2.getSubjectNow() == 0 && st2.getSubjectGroup() != 0){ // 폐강됨
                subjectSubList.add(st2);
                List<Subject> groupList = subjectRepository.findAllBySubjectGroup(st2.getSubjectGroup());
                for(Subject st22 : groupList){
                    if(st22.getSubjectNow() == 1) subjectAddList.add(st22);
                }
            }
        }
        subjectList.addAll(subjectAddList);
        subjectList.removeAll(subjectSubList);
    }

}
