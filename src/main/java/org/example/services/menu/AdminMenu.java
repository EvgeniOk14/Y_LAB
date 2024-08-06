package org.example.services.menu;

import org.example.authentification.AuthService;
import org.example.models.AuditLog;
import org.example.models.Order;
import org.example.models.User;
import org.example.services.auditService.AuditService;
import org.example.services.carService.CarService;
import org.example.services.orderService.OrderService;
import org.example.services.userService.UserService;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.function.Predicate;

import static org.example.services.auditService.AuditService.printLogFiles;

/**
 *  класс AdminMenu
 * описывает отображение меню для администратора
 * **/
public class AdminMenu
{
    //region Fields
    private CarService carService;
    private OrderService orderService;
    private AuditService auditService;

    private AuthService authService;
    private UserService userService;
    //endregion

    //region Constructors

    public AdminMenu(CarService carService, OrderService orderService, AuditService auditService, AuthService authService, UserService userService) {
        this.carService = carService;
        this.orderService = orderService;
        this.auditService = auditService;
        this.authService = authService;
        this.userService = userService;
    }

    //endregion

    /**
     * метод отображает меню администратора автосалона
     * @param  scanner - сканер (читает введённый пользователем текст)
     * @param user - объект типа User (пользователь)
     * **/
    public  void showAdminMenu(Scanner scanner, User user)
    {
        boolean flag = true;
        while (flag) {
            System.out.println("""
                    Меню администратора:
                    1. Просмотр всех автомобилей
                    2. Просмотр всех заявок
                    3. Просмотр всех  пользователей
                    4. Редактировать пользователя
                    5. Удалить пользователя
                    6. Просмотр всех логов
                    7. Фильтрация логов
                    8. Выйти
                    """);

            String choiceElement = scanner.nextLine();
            try
            {
                int choice = Integer.parseInt(choiceElement);
                switch (choice)
                {
                    case 1 ->
                    {
                        if (carService.getAllCars() == null)
                        {
                            System.out.println("В базе данных отсутствуют автомобили! ");
                            break;
                        }
                        else
                        {

                            System.out.println(carService.getAllCars());  // просмотр всех автомобилей
                            break;
                        }
                    }
                    case 2 ->  orderService.getAllOrders();  // просмотр всех заявок
                    case 3 -> System.out.println(userService.getAllUsers(userService.getMapOfAllUsers())); // просмотр всех пользователей
                    case 4 ->
                    {
                        boolean userIdFlag = true;
                        while (userIdFlag)
                        {
                            System.out.println("Введите id пользователя, которого хотите обновить: ");
                            String stringUserId = scanner.nextLine();
                            try
                            {
                                Integer userId = Integer.parseInt(stringUserId);
                                userService.updateUser(userId);
                                flag = false;
                                break;
                            }
                            catch (NumberFormatException e)
                            {
                                System.out.println("Вы ввели не число! Повторите ввод!");
                                flag = true;
                            }
                        }
                    }
                    // редактировать пользователя
                    case 5 ->
                    {
                        boolean deleteUserIdFlag = true;
                        while (deleteUserIdFlag)
                        {
                            System.out.println("Введите идентификационный номер id пользователя, которого хотите удалить: ");
                            String stringId = scanner.nextLine(); // считываем id В виде строки
                            try
                            {
                                Integer userDeleteId = Integer.parseInt(stringId); // получаем id в виде числа

                                User deleteUser = userService.getUserById(userDeleteId, userService.getMapOfAllUsers()); // получаем пользователя по id

                                System.out.println("Вы выбрали пользователя для удаления: " + deleteUser);
                                userService.deleteUser(userDeleteId);
                                System.out.println("пользователь: " + deleteUser  + " успешно удален из базы данных!");
                                deleteUserIdFlag = false;
                            }
                            catch (NumberFormatException e)
                            {
                                System.out.println("Вы ввели не число! Повторите ввод");
                                flag = true;
                            }
                        }
                    } // удалить пользователя

                     case 6 ->
                     {
                         String directory = "D:" + File.separator + "Y_LAB" + File.separator + "AutoShop" + File.separator + "logs";
                         printLogFiles(directory);
                     } // просмотр всех логов

                     case 7 ->
                     {
                         String directory = "D:" + File.separator + "Y_LAB" + File.separator + "AutoShop" + File.separator + "logs";

                          auditService.getLogsByFilter(auditService.inputParamUserId(), auditService.inputParamAction(), auditService.inputParamLocalDataTime());

                         // Фильтр по User ID
                         //Predicate<AuditLog> userIdFilter = log -> log.getUserId() == 5;

                         // Фильтр по дате (например, все записи после 2024-08-05T16:00:00)
                         //Predicate<AuditLog> dateFilter = log -> log.getTimestamp().isAfter(LocalDateTime.parse("2024-08-05T16:00:00"));

                         // Фильтр по действию
                         //Predicate<AuditLog> actionFilter = log -> log.getAction().contains("добавление нового автомобиля");

                         // Комбинированный фильтр
                         //Predicate<AuditLog> combinedFilter = userIdFilter.and(dateFilter).and(actionFilter);


                         //auditService.printLogFilesByFilter(directory, userIdFilter);
                     } // фильртрация логов
                    case 8 -> { flag = false; }
                    default -> System.out.println("Некорректный ввод! Повторите ввод!");
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Вы ввели не число! Повторите ввод!");
            }
        }
    }
}
