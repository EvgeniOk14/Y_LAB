package org.example.services.menu;

import org.example.interfases.StartProgram;
import org.example.models.Car;
import org.example.models.Order;
import org.example.models.User;
import org.example.services.carService.CarService;
import org.example.services.orderService.OrderService;
import java.util.Scanner;

/**
 *  класс ManagerMenu
 * описывает отображение меню для менеджера
 * **/
public class ManagerMenu
{
    //region Fields
    private  CarService carService;
    private  OrderService orderService;
    //endregion

    //region Constructors
    public ManagerMenu(CarService carService, OrderService orderService)
    {
        this.carService = carService;
        this.orderService = orderService;
    }
    //endregion

    /**
     * метод отображает меню менеджера автосалона
     * @param  scanner - сканер (читает введённый пользователем текст)
     * @param user - объект типа User (пользователь)
     * **/
    public  void showManagerMenu(Scanner scanner, User user)
    {
        boolean showManageFlag = true;
        while (showManageFlag)
        {
            System.out.println("""
                    Меню менеджера:
                    1. Добавить новый автомобиль в автосалон
                    2. Редактировать данные об автомобиле
                    3. Удалить автомобиль
                    4. Просмотр всех автомобилей
                    5. Просмотр всех заказов
                    6. Создать новый заказ
                    7. Редактировать заказ
                    8. Удалить заказ
                    9. Выйти
                    """);
            String choiceElement = scanner.nextLine();
            try
            {
                int choice = Integer.parseInt(choiceElement);
                switch (choice)
                {
                    // добавить новый автомобиль
                    case 1 ->
                    {
                        carService.addNewCar(carService.createNewCar(user),user);
                    }
                    //  Редактировать данные об автомобиле
                    case 2 ->
                    {
                        boolean inputId = true;
                        while (inputId)
                        {
                            System.out.println("Введите идентификационный номер id автомобиля находящегося в списке продаваемых автомобилей автосалона: ");
                            String stringId = scanner.nextLine();
                            try
                            {
                                Integer carId = Integer.parseInt(stringId);
                                carService.updateCar(carId);
                                inputId = false;
                            }
                            catch (NumberFormatException e)
                            {
                                System.out.println("Вы ввели не число! Повторите ввод");
                                showManageFlag = true;
                            }
                        }
                    }
                    // Удалить автомобиль
                    case 3 ->
                    {
                        boolean carIdFlag = true;
                        while (carIdFlag)
                        {
                            System.out.println("Введите идентификационный номер id автомобиля который хотите удалить: ");
                            String stringId = scanner.nextLine();
                            try
                            {
                                Integer carId = Integer.parseInt(stringId);
                                Car car = carService.getCarById(carId);
                                System.out.println("Вы выбрали автомобиль для удаления: " + car);
                                carService.deleteCar(carId);
                                System.out.println("автомобиль: " + car  + " успешно удалён из автосалона!");
                                carIdFlag = false;
                            }
                            catch (NumberFormatException e)
                            {
                                System.out.println("Вы ввели не число! Повторите ввод");
                                showManageFlag = true;
                            }
                        }
                    }
                    // Просмотр всех автомобилей
                    case 4 ->
                    {
                        carService.getAllCars();
                    }
                    // Просмотр всех заказов
                    case 5 ->
                    {
                        orderService.getAllOrders();
                    }

                    // Создать новый заказ
                    case 6 ->
                    {
                        boolean orderFlag = true;
                        while (orderFlag)
                        {
                            System.out.println("Сделайте выбор: какой заказ вы хотите создать: ");
                            System.out.println("""
                                1. Заказ на покупку нового автомобиля
                                2. Заказ на обслуживание личного автомобиля
                                3. Выход
                                """);

                            String stringOrderChoice = scanner.nextLine();
                            try
                            {
                                int orderChoice = Integer.parseInt(stringOrderChoice);
                                if(orderChoice > 0 && orderChoice <4)
                                {
                                    switch (orderChoice)
                                    {
                                        case 1 ->  orderService.createOrderForBuyNewCar(user); // заявка на покупку нового автомобиля
                                        case 2 ->  orderService.createOrderForServise(user);  // заявка на обслуживание ТО личного автомобиля
                                        case 3 -> {orderFlag = false; } // выход
                                        default -> System.out.println("Некорректный ввод! Повторите ввод!");
                                    }
                                }
                                else
                                {
                                    System.out.println("Вы ввели число, вне диапазона ввода! Введите в диапазоне от 1 до 3");
                                    orderFlag = true;
                                }
                            }
                            catch (NumberFormatException e)
                            {
                                System.out.println("Вы ввели не число! Повторите ввод!");
                            }
                        }
                    }

                    // Редактировать заказ
                    case 7 ->
                    {
                        boolean inputId = true;
                        while (inputId)
                        {
                            System.out.println("Введите идентификационный номер id заказа который вы хотите отредактировать: ");
                            String stringId = scanner.nextLine();
                            try
                            {
                                Integer orderId = Integer.parseInt(stringId);

                                showManageFlag = false;
                                orderService.updateOrder(orderId);
                                inputId = false;
                            }
                            catch (NumberFormatException e)
                            {
                                System.out.println("Вы ввели не число! Повторите ввод");
                                showManageFlag = true;
                            }
                        }
                    }

                    // Удалить заказ
                    case 8 -> {
                        boolean deleteCarIdFlag = true;
                        while (deleteCarIdFlag)
                        {
                            System.out.println("Введите идентификационный номер id заказа, который хотите удалить: ");
                            String stringId = scanner.nextLine(); // считываем id В виде строки
                            try
                            {
                                Integer orderDeleteId = Integer.parseInt(stringId); // получаем id в виде числа

                                Order deleteOrder = orderService.getOrderById(orderDeleteId); // получаем заявку по id

                                System.out.println("Вы выбрали автомобиль для удаления: " + deleteOrder);
                                orderService.deleteOrder(orderDeleteId);
                                System.out.println("заявка: " + deleteOrder  + " успешно удалена из базы данных!");
                                deleteCarIdFlag = false;
                            }
                            catch (NumberFormatException e)
                            {
                                System.out.println("Вы ввели не число! Повторите ввод");
                                showManageFlag = true;
                            }
                        }
                    }

                    // Выйти
                    case 9 ->
                    {
                        showManageFlag = false;
                    }
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
