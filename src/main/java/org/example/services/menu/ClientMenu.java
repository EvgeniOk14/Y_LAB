package org.example.services.menu;

import org.example.authentification.AuthService;
import org.example.models.User;
import org.example.services.carService.CarService;
import org.example.services.orderService.OrderService;
import org.example.services.userService.UserService;
import java.util.Scanner;

/**
 *  класс ClientMenu
 * описывает отображение меню для клиента
 * **/
public class ClientMenu
{
    //region Fields
    private  CarService carService;
    private  OrderService orderService;
    private  AuthService authService;
    private  UserService userService;
    //endregion

    //region Constructors
    public ClientMenu(CarService carService, OrderService orderService, AuthService authService, UserService userService) {
        this.carService = carService;
        this.orderService = orderService;
        this.authService = authService;
        this.userService = userService;
    }
    //endregion

    /**
     * метод отображает меню клиента автосалона
     * @param  scanner - сканер (читает введённый пользователем текст)
     * @param user - объект типа User (пользователь)
     * **/
    public  void showClientMenu(Scanner scanner, User user)
    {
        boolean flag = true;
        while (flag)
        {
            System.out.println("""
                    Меню клиента:
                    1. Просмотр всех автомобилей
                    2. Создать новый заказ на покупку нового автомобиля
                    3. Создать новый заказ на обслуживание вашего автомобиля
                    4. Просмотр своих заказов
                    5. Выйти
                    """);

            String choiceElement = scanner.nextLine();
            try
            {
                int choice = Integer.parseInt(choiceElement);
                switch (choice)
                {
                    case 1 ->  carService.getAllCars();  // просмотр все новых автомобилей в автосалоне
                    case 2 ->  orderService.createOrderForBuyNewCar(user); // заявка на покупку нового автомобиля
                    case 3 ->  orderService.createOrderForServise(user);  // заявка на обслуживание ТО личного автомобиля
                    case 4 ->  orderService.getOrdersByUser(user); // просмотреть все свои заказы (заявки)
                    case 5 -> {  flag = false; } // выход
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
