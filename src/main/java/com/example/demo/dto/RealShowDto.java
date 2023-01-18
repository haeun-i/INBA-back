package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import java.util.*;

@Data
@Builder
public class RealShowDto {
    String studentNum;
    String pdfPath;
    List<ShowDto> showDtoList;
}
