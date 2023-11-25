package com.hust.springbootmybatisquickstart.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Updateuser {
    private String cargoName;
    private String cargoWeight;
    private String index;
    private String portName;
    private String departPart;
    private String arrivePart;
    private @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime loadStartTime;
    private @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime loadEndTime;
    private @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime unloadStartTime;
    private @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime unloadEndTime;
    private @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime departTime;
    private @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime arriveTime;
    private String ladingId;
    private @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate actionDate;
    private String port;
    private String containerId;
    private String action;
}
