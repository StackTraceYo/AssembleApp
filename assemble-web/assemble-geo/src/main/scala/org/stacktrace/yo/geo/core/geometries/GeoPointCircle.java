package org.stacktrace.yo.geo.core.geometries;

import com.github.davidmoten.rtree.geometry.Point;

public class GeoPointCircle<K> {

    final float lat;
    final float lon;
    final double radiusKm;
    final K val;

    public GeoPointCircle(Point point, double radiusKm, K val) {
        this.lat = point.y();
        this.lon = point.x();
        this.radiusKm = radiusKm;
        this.val = val;
    }


    public GeoPointCircle(float lat, float lon, double radiusKm, K val) {
        this.lat = lat;
        this.lon = lon;
        this.radiusKm = radiusKm;
        this.val = val;
    }


}
