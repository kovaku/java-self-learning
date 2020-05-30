package org.github.kovaku.designpatterns.creational.builder;

public class City {

    private final String name;
    private final String country;
    private final Double lat;
    private final Double lon;

    private City(CityBuilder builder) {
        this.name = builder.name;
        this.country = builder.country;
        this.lat = builder.lat;
        this.lon = builder.lon;
    }

    public static CityBuilder builder() {
        return new CityBuilder();
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public static class CityBuilder {

        private String name;
        private String country;
        private Double lat;
        private Double lon;

        public CityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CityBuilder withCountry(String country) {
            this.country = country;
            return this;
        }

        public CityBuilder withLat(Double lat) {
            this.lat = lat;
            return this;
        }

        public CityBuilder withLon(Double lon) {
            this.lon = lon;
            return this;
        }

        public City build() {
            return new City(this);
        }
    }

}
