package org.example.models;

import java.util.Objects;

/**
 * Класс User, описывает объект - user (пользователя)
 * **/
public class User
{
    //region Fields
    private int id;
    private String userLogin; // логин пользователя
    private String userPassword; // пароль пользователя
    private String userRole; //  роль пользователя: ("admin", "manager", "client")
    private static int count = 1;
    //endregion


    //region Constructors
    public User(String userLogin, String userPassword, String userRole)
    {
        this.id = count++;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }
    //endregion

    //region Getters
    public int getId()
    {
        return id;
    }

    public String getUserLogin()
    {
        return userLogin;
    }

    public String getUserPassword()
    {
        return userPassword;
    }

    public String getUserRole()
    {
        return userRole;
    }
    //endregion

    //region Setters
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    //endregion

    /** переопределение метода equals **/
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true; // проверяем, указывают ли две ссылки на один и тот же объект, если да, то они равны, метод возвращает true
        }
        if (o == null || getClass() != o.getClass()) // если ссылка на объект равна null и объект не принадлежит тому же классу что и текущий объект, то они не равны и возвращаем false
        {
            return false;
        }
        User user = (User) o; // приведение объекта o к типу User
        {
            return Objects.equals(id, user.id); // проверка, равны ли идентификаторы (ID) двух объектов. Метод Objects.equals сравнивает два объекта на равенство
        }
    }

    /** переопределение метода hashCode **/
    @Override
    public int hashCode() // метод гарантирует, что если два объекта User равны по id, то они будут иметь одинаковый хэш-код, что соответствует контракту между equals и hashCode
    {
        return Objects.hash(id); // возвращает хэш-код для указанных значений полей
    }

    /** переопределение метода toString для вывода объекта User в консоль **/
    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", userLogin='" + userLogin + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}

