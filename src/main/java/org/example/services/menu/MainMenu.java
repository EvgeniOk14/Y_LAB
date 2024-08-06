package org.example.services.menu;

import org.example.authentification.AuthService;
import org.example.models.User;
import org.example.services.SwitchCaseMetod;
import org.example.services.auditService.AuditService;
import org.example.services.userService.UserService;
import java.util.Map;
import java.util.Scanner;

/**
 *  класс MainMenu
 * описывает отображение главное меню приложения (меню входа и регистрации)
 * **/
public class MainMenu
{
    //region Fields
    private Map<Integer, User> mapAllUsers;
    private   UserService userService;
    private  SwitchCaseMetod switchCaseMetod;
    private   AuthService authService;
    private  AuditService auditService;
    private String userLogin;
    private String userPassword;
    private String userRole;
    private static Integer userId;
    private static final Scanner scanner = new Scanner(System.in);
    //endregion


    //region Constructors
    public MainMenu(Map<Integer, User> mapAllUsers, UserService userService, SwitchCaseMetod switchCaseMetod, AuthService authService, AuditService auditService) {
        this.mapAllUsers = mapAllUsers;
        this.userService = userService;
        this.switchCaseMetod = switchCaseMetod;
        this.authService = authService;
        this.auditService = auditService;
    }
    //endregion

    /**
     * метод registerUser - регистрация пользователей
     * @return listAllUsers - список всех пользователей
     * **/
    public Map<Integer, User> registerUser()
    {
        boolean registerFlag = true; // устанавливаем флаг для главного цикла
        while (registerFlag) // главный цикл
        {
                System.out.print("Введите логин: "); // печатаем предложение ввести логин
                userLogin = scanner.nextLine(); // получение логина пользователя

                if (!userLogin.isEmpty()) // если пользователь ввёл логин, т.е. он не пустой, то:
                {
                    System.out.println("Вы ввели логин равный: " + userLogin); // печатаем в консоль для наглядности то что ввёл пользователь
                }
                else // иначе:
                {
                    System.out.println("Вы ни чего не ввели в поле логин! Повторите ввод имени пользователя!"); // уведомляем об отсутствии ввода
                    registerFlag = true;
                    continue; // продолжаем цикл и просим ввести заново
                }

                System.out.print("Введите пароль: "); // печатаем предложение ввести пароль
                userPassword = scanner.nextLine(); // получение пароля пользователя

                if (!userPassword.isEmpty()) // если пользователь ввёл пароль, т.е. он не пустой, то:
                {
                    System.out.println("Вы ввели пароль равный: " + userPassword); // печатаем в консоль для наглядности то что ввёл пользователь
                }
                else // иначе:
                {
                    System.out.println("Вы ни чего не ввели! Повторите ввод пароля!"); // уведомляем об отсутствии ввода
                    registerFlag = true;
                    continue; // продолжаем цикл и просим ввести заново
                }

                boolean status = true; // устанавливаем флаг для второго цикла

                while (status) // второй цикл
                {
                    System.out.print("Введите ваш статус пользователя: (admin/manager/client): "); // получение статуса пользователя
                    userRole = scanner.nextLine(); // считываем роль пользователя

                    if (userRole != null && (userRole.equalsIgnoreCase("admin") || userRole.equalsIgnoreCase("manager") || userRole.equalsIgnoreCase("client"))) // если всё это совпало, то:
                    {
                        System.out.println("Вы ввели userRole равный: " + userRole.toLowerCase());  // уведомляем в консоль для наглядности

                        if (!authService.checkRegistration(userLogin, userPassword)) // если регистрации у пользователя ещё не было, то:
                        {
                            mapAllUsers = authService.register(userLogin, userPassword, userRole.toLowerCase()); // регистрируем пользователя и добавляем его в список зарегистрированных и возвращаем этот список
                            System.out.println("Вы успешно зарегистрированы в системе! "); // уведомляем пользователя об успешной регистрации
                            System.out.println("список всех пользователей: " + userService.getAllUsers(mapAllUsers)); // выводим для проверки и наглядности в консоль список всех зарегистрированных пользователей
                            userId = userService.getIdByUserDetails(userLogin, userPassword, userRole);
                            auditService.logAction(userId, "регистрация нового пользователя"); // логирование события - регистрация нового пользователя
                            return mapAllUsers; // возвращаем список всех зарегистрированных пользователей
                        }
                        else // если пользователь уже был зарегистрирован ранее, то:
                        {
                            User user = userService.getUserById(userId, mapAllUsers);  // получаем пользователя по его id
                            switchCaseMetod.showConcreteUserMenu(scanner, user); // показываем меню для конкретного пользователя, в соответствие с его статусом (администратор / менеджер / клиент)
                        }
                    }
                    else // если ввёл не корректно статус, то:
                    {
                        System.out.println("Некорректный ввод статуса пользователя! Повторите ввод!"); // уведомляем пользователя в консоль об этом
                        registerFlag = true;
                    }
                    status = false;
                }
            registerFlag = false;
            }
        return  mapAllUsers; // возвращаем список всех зарегистрированных пользователей
    } // <--- скобка закрытия метода  registerUser()

} // <--- скобка закрытия класса MainMenu
