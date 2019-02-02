package me.meet.pattern.design.creation.factory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class SimpleCarFactory {
    private static final Logger LOG = Logger.getLogger(SimpleCarFactory.class.getName());

    public static Car newCar() {
        String filePath = (ClassLoader.getSystemResource("") + "/settings.properties").substring(5); // file:
        String key = "car.name";
        Properties props = new Properties();

        InputStream in = null;
        String value = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            in.close();

            value = props.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ("BMW".equals(value)) {
            return new BMWCar();
        } else if ("Benz".equals(value)) {
            return new BenzCar();
        } else {
            class DefaultCar implements Car {
                public void drive() {
                    LOG.info("DefaultCar drive now....");
                }

                public void drive(String driverName) {
                    LOG.info(driverName + "is driving DefaultCar now....");
                }
            }
            return new DefaultCar();
        }
    }

    public static void main(String[] args) {
        Car car = SimpleCarFactory.newCar();
        car.drive();
    }
}
