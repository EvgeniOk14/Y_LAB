package org.example;

import org.example.models.User;
import org.example.services.SwitchCaseMetod;
import org.example.services.menu.MainMenu;
import org.example.services.userService.UserService;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс RunApplication для запуска программы,
 * сделан для сокрытия логики в клиентском коде
 * отображает главное начальное меню и его логику вызова методов в зависимости от выбора пользователем
 * **/
public class RunApplication
{
    //region Fields
    private SwitchCaseMetod switchCaseMetod;
    private MainMenu mainMenu;
    private UserService userService;
    private Map<Integer, User> users;
    //endregion

    //region Constructors
    public RunApplication(SwitchCaseMetod switchCaseMetod, MainMenu mainMenu, Map<Integer, User> users, UserService userService)
    {
        this.mainMenu = mainMenu;
        this.switchCaseMetod= switchCaseMetod;
        this.users = users;
        this.userService = userService;
    }
    //endregion

    /**
     * метод устанавливает зависимости, для соблюдения порядка вызова в запускающем классе Main
     * @param switchCaseMetod - устанавливает зависимость класс SwitchCaseMetod
     * @param mainMenu - устанавливает зависимость класс MainMenu
     * @param users - список всех пользователей
     * **/
    public void setDependencies(SwitchCaseMetod switchCaseMetod, MainMenu mainMenu, Map<Integer, User> users, UserService userService)
    {
        this.switchCaseMetod = switchCaseMetod;
        this.mainMenu = mainMenu;
        this.users = users;
        this.userService = userService;
    }


    /**
     * Метод отображения меню и вызова методов в зависимости от выбора в меню
     * **/
    public  void choise()
    {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);

        mainLoop:
        while (flag)
        {
            System.out.println("Добро пожаловать на сайт нашего автосалона!");
            System.out.println("1. Зарегистрироваться: ");
            System.out.println("2. Войти: ");
            System.out.println("3. Выход: ");

            System.out.println("Сделайте свой выбор, введите число из предложенного выше меню (1 или 2 или 3): ");

            try
            {
                String choiceElement = scanner.nextLine();
                Integer choice = Integer.parseInt(choiceElement);

                if (choice > 0 && choice < 4)
                {
                    switch (choice)
                    {
                        case 1:
                        {
                            users = mainMenu.registerUser();
                            //continue;
                            break;
                        }
                        case 2:
                        {
                            if (userService.getAllUsers(users) != null && !userService.getAllUsers(users).isEmpty())
                            {
                                switchCaseMetod.showUserMenu(scanner, userService.getAllUsers(users));
                                //continue;
                                break;
                            }
                            else
                            {
                                System.out.println("Нет зарегистрированных пользователей. Сначала зарегистрируйтесь.");
                                flag = true;
                                //continue;
                                break;
                            }
                        }
                        case 3:
                        {
                            System.out.println("Выход из системы...");
                            flag = false;
                            break;
                        }
                        default:
                        {
                            System.out.println("Не корректный выбор. Повторите ввод снова!");
                            flag = false;
                            break;
                        }
                    }
                }
                else
                {
                    System.out.println("Вы ввели не число вне диапазона от 1 до 3 !!! Повторите ввод!");
                    flag = true;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Вы ввели не число! Повторите ввод!");
            }
        }
    }
}
