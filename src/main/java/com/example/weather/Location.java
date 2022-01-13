package com.example.weather;

import java.util.Objects;
import java.util.StringJoiner;

class Location {

    private final String name;
    private ClassToRemove classToRemove;

    private Location(String name) {
        this.name = name;
    }

    static Location of(String name) {
        return new Location(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Location.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private static class ClassToRemove {

    }
}
