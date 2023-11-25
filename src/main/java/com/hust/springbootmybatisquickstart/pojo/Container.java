package com.hust.springbootmybatisquickstart.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Container {
    private LocalDate actionDate;
    private String  portName;
    private String cargoName;
    private String cargoWeight;
}
