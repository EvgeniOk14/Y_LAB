package org.example.models;

import java.time.LocalDateTime;
import java.util.Objects;

/** Класс заказов **/
public class Order
{
    //region Fields
    private int id;
    private int carId;
    private int clientId;
    private String status;
    private LocalDateTime localDateTime;
    private static int count = 1;
    //endregion

    //region Constructors
    public Order(int carId, int clientId, String status, LocalDateTime localDateTime)
    {
        this.id = count++;
        this.carId = carId;
        this.clientId = clientId;
        this.status = status;
        this.localDateTime = localDateTime;
    }
    public Order(int clientId, String status, LocalDateTime localDateTime)
    {
        this.id = count++;
        this.clientId = clientId;
        this.status = status;
        this.localDateTime =localDateTime;
    }
    //endregion

    //region Getters
    public int getId() {
        return id;
    }

    public int getCarId() {
        return carId;
    }

    public int getClientId() {
        return clientId;
    }

    public String getStatus() {
        return status;
    }
    //endregion

    //region Setters
    public void setStatus(String status) {
        this.status = status;
    }
    //endregion

    /** переопределение метода equals **/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    /** переопределение метода hashCode **/
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /** переопределение метода hashCode **/
    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", carId=" + carId +
                ", clientId=" + clientId +
                ", status='" + status + '\'' +
                '}';
    }
}

