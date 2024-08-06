package org.example.models;

import java.util.Objects;

/** Класс Car
 * описыввает сущьность  - car (автомобиль) **/
public class Car
{
    //region Fields
    private int id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private String condition;
    private static int count = 1;
    //end region

    //region Constructors
    public Car(String brand, String model, int year, double price, String condition) {
        this.id = count++;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.condition = condition;
    }
    //endregion

    //region Getters
    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }
    //endregion

    //region Setters
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
    //endregion

    /** переопределение  метода equals **/
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id;
    }

    /** переопределение  метода hashCode**/
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /** переопределение  метода toString **/
    @Override
    public String toString()
    {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", condition='" + condition + '\'' +
                '}';
    }
}

