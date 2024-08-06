package org.example.models;

import java.time.LocalDateTime;
import java.util.Objects;

/** Класс AuditLog для логирования важных действий **/
public class AuditLog
{
    //region Fields
    private int id;
    private int userId;
    private String action;
    private LocalDateTime timestamp;
    private static int count = 1;
    //endregion

    //region Constructors
    public AuditLog(int userId ,String action, LocalDateTime timestamp)
    {
        this.id = count++;
        this.userId = userId;
        this.action = action;
        this.timestamp = timestamp;
        System.out.println("Создан AuditLog с ID: " + this.id);
    }
    public AuditLog()
    {
        // default Constructor
    }
    //endregion

    //regio Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    //endregion

    /** переопределение метода equals **/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditLog auditLog = (AuditLog) o;
        return id == auditLog.id;
    }

    /** переопределение метода hashCode() **/
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /** переопределение метода toString() **/
    @Override
    public String toString() {
        return "AuditLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

