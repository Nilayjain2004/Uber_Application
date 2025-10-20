<<<<<<< HEAD
package com.nilayjain.project.uber.uberApplication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointDto {

    private double[] coordinates;
    private String type ="Point";

    public PointDto(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
=======
package com.nilayjain.project.uber.uberApplication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointDto {

    private double[] coordinates;
    private String type ="Point";

    public PointDto(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
>>>>>>> master
