package me.meet.pattern.design.creation.abstractFactory;

public interface CarFamilyFactory {

    Car newSUVCar(); // SUV

    Car newBusCar(); // Bus

    Car newMiniCar(); // mini
}
