package org.example.authentification;

import org.example.services.userService.UserService;
import org.example.models.User;
import java.util.List;
import java.util.Map;

/**
 * Класс AuthService - идентификация пользователя
 * **/
public class AuthService
{
    //region Fields
    private  UserService userService;
    //endregion

    //region Constructors
    public AuthService(UserService userService)
    {
        this.userService = userService;
    }
    //endregion

    /**
     * Метод возвращает true или false, в зависимости от совпадения по имени и паролю
     *
     * @param userLogin имя пользователя который ввёл пользователь при регистрации
     * @param userPassword пароль пользователя который ввёл пользователь при регистрации
     * @return true если введённые пользователем имя и пароль совпали с базой данных
     * @return false если введённые пользователем имя и пароль не совпали с базой данных
     */
    public boolean checkLogin(String userLogin, String userPassword)
    {
            List<User> listOfUsers = userService.getUsersByUsername(userLogin);
            for (User user : listOfUsers)
            {
                if (user.getUserPassword().equals(userPassword))
                {
                    return true;
                }
            }
        return false;
    }

    /**
     * Метод проверяет регистрацию пользователя
     * возвращает true или false, в зависимости от совпадения по имени и паролю
     * @param userLogin имя пользователя который ввёл пользователь при регистрации
     * @param userPassword пароль пользователя который ввёл пользователь при регистрации
     * @return true если введённые пользователем имя и пароль совпали с базой данных
     * @return false если введённые пользователем имя и пароль не совпали с базой данных
     */
    public boolean checkRegistration(String userLogin, String userPassword)
    {
        if(userService.checkUsersByUsername(userLogin))
        {
            List<User> listOfUsers = userService.getUsersByUsername(userLogin);
            for (User user : listOfUsers)
            {
                if (user.getUserPassword().equals(userPassword))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод регистрирует нового пользователя (добавляет его в список пользователей)
     * @param userLogin имя пользователя который ввёл пользователь при регистрации
     * @param userPassword пароль пользователя который ввёл пользователь при регистрации
     * @param role роль пользователя который ввёл пользователь при регистрации
     */
    public Map<Integer, User> register(String userLogin, String userPassword, String role)
    {
        User newUser = new User(userLogin, userPassword, role);
        //userService.addNewUser(newUser);
        //listOfAllUsers.add(newUser);
        return userService.addNewUser(newUser);
    }

}
