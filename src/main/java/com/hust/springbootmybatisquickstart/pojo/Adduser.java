package com.hust.springbootmybatisquickstart.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adduser {
    private String shipCompany;
    private String shipName;
    private @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime loadStartTime;
    private @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime loadEndTime;
    private @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime departTime;
    private @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime arriveTime;
    private @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime departStartTime;
    private @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime arriveEndTime;
    private String portName;
    private String ladingId;
    private String containerId;
    private String containerSize;
    private String departPart;
    private String arrivePart;
    private String departPort;
    private String arrivePort;
    private @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime unloadStartTime;
    private @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime unloadEndTime;
    private String[] containerYard;
    private String[] action;
    private String[] actionDate;
    private Integer number;
    private String ownerName;
    private String ownerId;
    private String logisticsCom;
    private String cargoName;
    private String cargoWeight;
}
