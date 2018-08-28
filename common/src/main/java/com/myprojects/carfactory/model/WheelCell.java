package com.myprojects.carfactory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WheelCell {
    private Integer row;
    private Integer col;
    private Wheel wheel;
}
