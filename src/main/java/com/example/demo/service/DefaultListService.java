package com.example.demo.service;

import com.example.demo.domain.Subject;
import com.example.demo.dto.SubjectDto;
import com.example.demo.dto.SubjectStatus;
import com.example.demo.repository.StandardRepository;
import com.example.demo.repository.SubjectRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultListService {

    private final SubjectRepository subjectRepository;

    public List<SubjectDto> core1List(){
        List<Subject> subjectList = subjectRepository.findAllBySubjectStatus(SubjectStatus.핵심교양1);
        return toDtoList(subjectList);
    }

    public List<SubjectDto> core2List(){
        List<Subject> subjectList = subjectRepository.findAllBySubjectStatus(SubjectStatus.핵심교양2);
        return toDtoList(subjectList);
    }

    public List<SubjectDto> core3List(){
        List<Subject> subjectList = subjectRepository.findAllBySubjectStatus(SubjectStatus.핵심교양3);
        return toDtoList(subjectList);
    }

    public List<SubjectDto> core4List(){
        List<Subject> subjectList = subjectRepository.findAllBySubjectStatus(SubjectStatus.핵심교양4);
        return toDtoList(subjectList);
    }

    public List<SubjectDto> core5List(){
        List<Subject> subjectList = subjectRepository.findAllBySubjectStatus(SubjectStatus.핵심교양5);
        return toDtoList(subjectList);
    }

    public List<SubjectDto> core6List(){
        List<Subject> subjectList = subjectRepository.findAllBySubjectStatus(SubjectStatus.핵심교양6);
        return toDtoList(subjectList);
    }

    public List<SubjectDto> creativeList(){
        List<Subject> subjectList = subjectRepository.findAllBySubjectStatus(SubjectStatus.창의영역);
        return toDtoList(subjectList);
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
}
