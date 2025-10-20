package com.nilayjain.project.uber.uberApplication.services;

import org.locationtech.jts.geom.Point;

public interface DistanceService {
    double calculateDistance(Point src,Point dest);  // methods

}
