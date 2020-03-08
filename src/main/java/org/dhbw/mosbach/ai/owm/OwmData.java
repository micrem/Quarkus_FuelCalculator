package org.dhbw.mosbach.ai.owm;

import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class OwmData {
    private Coords coord;
    private List<Element> otherElements;

    public Coords getCoord() {
        return coord;
    }

    public void setCoord(Coords coord) {
        this.coord = coord;
    }

    @XmlAnyElement
    public List<Element> getOtherElements() {
        return otherElements;
    }

    public void setOtherElements(List<Element> otherElements) {
        this.otherElements = otherElements;
    }

    public static class Coords {
        private float lon;
        private float lat;

        public float getLon() {
            return lon;
        }

        public void setLon(float lon) {
            this.lon = lon;
        }

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }
    }
}
