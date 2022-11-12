package Planes;

import java.util.Objects;

public class PassengerPlane extends Plane{

    
    private int passengersCapacity;

    
    public PassengerPlane(String model, int maxSpeed, int maxFlightDistance, int maxLoadCapacity, int passengersCapacity) {
        super(model, maxSpeed, maxFlightDistance, maxLoadCapacity);
        this.passengersCapacity = passengersCapacity;
    }


    
    public int getPassengersCapacity() {
        return passengersCapacity;
    }

    @Override
    public String toString() {
        return super.toString().replace("}",
                String.format(",passengersCapacity=%d", passengersCapacity));
    }


    @Override
    public boolean equals(Object secondPlane) {
        if (this == secondPlane) return true;
        if (!(secondPlane instanceof PassengerPlane)) return false;
        if (!super.equals(secondPlane)) return false;
        PassengerPlane plane = (PassengerPlane) secondPlane;
        return passengersCapacity == plane.passengersCapacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), passengersCapacity);
    }
}
