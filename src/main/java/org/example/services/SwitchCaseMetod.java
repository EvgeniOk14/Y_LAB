package org.example.services;

import org.example.interfases.StartProgram;
import org.example.models.User;
import org.example.services.menu.AdminMenu;
import org.example.services.menu.ClientMenu;
import org.example.services.menu.ManagerMenu;
import java.util.List;
import java.util.Scanner;

/**
 * Класс SwitchCaseMetod
 *  описывает работу с меню для пользователей
 *  **/
public class SwitchCaseMetod
{
    //region Fields
    private ManagerMenu managerMenu;
    private ClientMenu clientMenu;
    private AdminMenu adminMenu;
   // private StartProgram startProgram;
    //endregion

    //region Constructors
    public SwitchCaseMetod(ManagerMenu managerMenu, ClientMenu clientMenu, AdminMenu adminMenu, StartProgram startProgram)
    {
        this.managerMenu = managerMenu;
        this.adminMenu = adminMenu;
        this.clientMenu = clientMenu;
        //this.startProgram = startProgram;
    }
    //endregion

    /**
     * метод showUserMenu предоставляет доступ к меню пользователей, в зависимости от их статуса (администратор / менеджер / клиент)
     * @param scanner - считывание с консоли
     * @param listOfAllUsers - список всех зарегистрированных в системе пользователей
     * **/
    public void showUserMenu(Scanner scanner, List<User> listOfAllUsers)
    {

        System.out.println("Введите логин: ");
        String userLogin = scanner.nextLine();
        System.out.println("Введите пароль: ");
        String userPassword = scanner.nextLine();

        //List<User> listOfAllUsers = userService.getAllUsers(users); // получаем список пользователей для последующего поиска в нём нужного пользователя

        boolean userFound = false; // устанавливаем флаг нахождения пользователя false

        for (User user : listOfAllUsers) // итерируемся по списку пользователей
        {
            if (user.getUserLogin().equalsIgnoreCase(userLogin) && user.getUserPassword().equalsIgnoreCase(userPassword)) {

                userFound = true; // устанавливаем флаг нахождения пользователя true

                if (user.getUserRole().equalsIgnoreCase("client"))
                {
                    clientMenu.showClientMenu(scanner, user); // показываем меню клиента

                }
                else if (user.getUserRole().equalsIgnoreCase("admin"))
                {
                    adminMenu.showAdminMenu(scanner, user); // показываем меню администратора
                }
                else if (user.getUserRole().equalsIgnoreCase("manager"))
                {
                    managerMenu.showManagerMenu(scanner, user); // показываем меню менеджера
                }
                break;  // мы нашли пользователя и выходим из цикла
            }
        }

        if (!userFound) // если пользователь не найден, то:
        {

            System.out.println("Вас нет в базе данных! Зарегистрируйтесь! "); // уведомляем пользователя о том, что он не зарегистрирован в системе
            //startProgram.start(); // возврат в главное меню
        }
    }


    /**
     * метод showConcreteUserMenu предоставляет конкретное меню для конкретного пользователя с конкретным статусом (администратор / менеджер / клиент)
     * @param scanner - считывание с консоли
     * @param user - объект типа User (пользователь)
     * **/
    public void showConcreteUserMenu(Scanner scanner, User user)
    {
            if (user.getUserRole().equalsIgnoreCase("client"))
            {
                clientMenu.showClientMenu(scanner, user);
            }
            if (user.getUserRole().equalsIgnoreCase("admin"))
            {
                adminMenu.showAdminMenu(scanner, user);
            }
            if (user.getUserRole().equalsIgnoreCase("manager"))
            {
                managerMenu.showManagerMenu(scanner, user);
            }
    }
}
