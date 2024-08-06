package org.example.services.orderService;

import org.example.models.Car;
import org.example.models.Order;
import org.example.models.User;
import org.example.services.auditService.AuditService;
import org.example.services.carService.CarService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Класс OrderService - содержит методы для работы с объектом Order (заявка)  **/
public class OrderService
{
    //regionFields
    private AuditService auditService;
    private List<Order> orders;
    private CarService carService;
    private static Scanner scanner = new Scanner(System.in);
    //endregion

    //region Constructors
    public OrderService(AuditService auditService, CarService carService, List<Order> orders)
    {
        this.carService = carService;
        this.auditService = auditService;
        this.orders = orders;
    }
    //endregion

        /**
         * метод создаёт заказ на покупку нового автомобиля в автосалоне
         * @param  user - принимает объект User пользователь
         * @return Order - возвращает объект заявка
         * **/
        public Order createOrderForBuyNewCar(User user)
        {
            System.out.print("Введите ID автомобиля: ");
            int carId = Integer.parseInt(scanner.nextLine()); // считываем id  нового автомобиля с консоли, введённое пользователем

            List<Car> listOfCars = carService.getAllCars(); // получаем список новых автомобилей

            if (listOfCars.isEmpty())
            {
                System.out.println("Список автомобилей пуст! Рекомендуем в начале добавить автомобиль в базу автосалона, а потом делать на него заказ! ");
            }
            else
            {
                for (Car car : listOfCars)
                {
                    if (car.getId() == carId) // если в списке у автомобиля его id совпадает с введённым пользователем id, то:
                    {

                        Order order = new Order(carId, user.getId(), car.getCondition(), LocalDateTime.now()); // Создали заявку на покупку нового автомобиля  и заполнили поля автоматически
                        addNewOrder(order); // добавили заявку в список всех заявок
                        System.out.println("Ваша заявка на покупку нового автомобиля принята в работу: " + order); // вывели для уведомления клиента о принятии заявки к работе
                        auditService.logAction(user.getId(), "создана заявка на покупку автомобиля"); // залогировали событие создание заявки
                        return order; // возвращаем заявку
                    }
                    else
                    {
                        System.out.println("Автомобиля с таким номеров id нет в базе! Реомендуем вначале просмотреть список всех автомобилей и затем произвести выбор! ");
                    }
                }
            }
            return null;
        }
    /**
     * метод создаёт заказ на техническое обслуживание личного автомобиля клиента в автосалоне
     * @param  user - принимает объект User пользователь
     * @return Order - возвращает объект заявка
     * **/
    public Order createOrderForServise(User user)
    {
        Order order = new Order(user.getId(), "Обслуживание личного автомобиля", LocalDateTime.now()); // Создали заявку на покупку нового автомобиля

        addNewOrder(order); // добавили заявку в список всех заявок

        System.out.println("Ваша заявка на обслуживание Вашего автомобиля принята в работу: " + order); // вывели для уведомления клиента о принятии заявки к работе

        auditService.logAction(user.getId(), "создана заявка на обслуживание личного автомобиля"); // залогировали событие создание заявки

        return order; // возвращаем заявку
    }

    /**
     * метод получает список объектов типа Order (заявка), по указанному пользователю пользователю
     * @param  user - принимает объект User пользователь
     * @return List<Order> orders - возвращает список объектов типа заявка Order
     * **/
    public List<Order> getOrdersByUser(User user)
    {
        List<Order> userOrderList = new ArrayList<>();
        for (Order order : orders)
        {
            if (order.getClientId() == user.getId())
            {
                userOrderList.add(order);
            }
        }
        System.out.println("Список Ваших заявок в нашем автосалоне: " + userOrderList);
        return orders;
    }

    /**
     * метод добавления новой заявки
     * @param order  заявка
     * **/
    public void addNewOrder(Order order)
    {
        orders.add(order);
    }

    /**
     * метод получения всех заявок
     * @return new ArrayList<>(orders) списокс заявками
     * **/
    public List<Order> getAllOrders()
    {
        System.out.println(orders);
        return orders;
    }

    /**
     * метод получения заявки по её id
     * @param id идентификационный номер заявки
     * @return order возвращает заявку
     * **/
    public Order getOrderById(int id)
    {
        for (Order order : orders)
        {
            if (order.getId() == id)
            {
                return order;
            }
        }
        return null;
    }

    /**
     * метод обновления заявки
     * @param orderId номер заявки
     * **/
    public void updateOrder(int orderId)
    {
        {
            boolean flag = true;
            while (flag)
            {
                Order order = getOrderById(orderId);
                if (order != null)
                {
                    System.out.println("Найден заказ, данные которого Вы хотите редактировать: " + order);

                    System.out.println("Выберите параметр заказа, который вы хотите изменить: ");

                    System.out.println("""
                            1. статус заказа
                            2. Выход на один уровень из меню
                            """);

                    String choise = scanner.nextLine();
                    try
                    {
                        Integer intChoise = Integer.parseInt(choise);
                        switch (intChoise)
                        {
                            case 1 ->
                            {
                                System.out.println("Статус заказа : " + order.getStatus());
                                System.out.println("Введите новый статус заказа: ");
                                String newStatus = scanner.nextLine();
                                order.setStatus(newStatus);
                                System.out.println("Параметр: статус заказа - был успешно изменён! ");
                            }
                            case 2 ->
                            {
                                flag = false;
                            }
                            default -> System.out.println("Некорректный ввод! Повторите ввод!");
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        e.getMessage();
                    }
                }
                else
                {
                    System.out.println("Заказ с таким id не найден!");
                }
            }
        }
    }

    /**
     * метод удаления заявки
     * @param id  - идентификационный номер заявки
     * **/
    public void deleteOrder(int id)
    {
        Order order = getOrderById(id);
        if (order != null)
        {
            orders.remove(order);
        }
    }
}

