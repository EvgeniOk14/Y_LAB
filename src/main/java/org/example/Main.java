package org.example;

import org.example.authentification.AuthService;
import org.example.models.AuditLog;
import org.example.models.Car;
import org.example.models.Order;
import org.example.models.User;
import org.example.services.SwitchCaseMetod;
import org.example.services.auditService.AuditService;
import org.example.services.carService.CarService;
import org.example.services.menu.AdminMenu;
import org.example.services.menu.ClientMenu;
import org.example.services.menu.MainMenu;
import org.example.services.menu.ManagerMenu;
import org.example.services.orderService.OrderService;
import org.example.services.userService.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс Main
 * Точка входа в приложение
 * **/
public class Main
{
    public static void main(String[] args)
    {
        // Сначала создаем все необходимые сервисы
        Map<Integer, User> users = new HashMap<>();
        List<AuditLog> logs = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        List<Car> cars = new ArrayList<>();
        UserService userService = new UserService(users);
        AuthService authService = new AuthService(userService);
        AuditService auditService = new AuditService(logs, userService);
        CarService carService = new CarService(auditService, cars);
        OrderService orderService = new OrderService(auditService, carService, orders);

        // Создаем заглушку для RunApplication (будет переинициализирована позже)
        RunApplication runApplication = new RunApplication(null, null, null, null);

        // Создаем меню администратора, менеджера и клиента, передавая runApplication::choise как callback

        ManagerMenu managerMenu = new ManagerMenu(carService, orderService);
        ClientMenu clientMenu = new ClientMenu(carService, orderService, authService, userService);
        AdminMenu adminMenu = new AdminMenu(carService, orderService, auditService,  authService,  userService);

        // Создаем SwitchCaseMetod с зависимостями от других меню
        SwitchCaseMetod switchCaseMetod = new SwitchCaseMetod(managerMenu, clientMenu, adminMenu, runApplication::choise);

        // Создаем MainMenu с зависимостями от других сервисов и SwitchCaseMetod
        MainMenu mainMenu = new MainMenu(users, userService, switchCaseMetod, authService, auditService);

        // Теперь переинициализируем runApplication с правильными зависимостями
        runApplication.setDependencies(switchCaseMetod, mainMenu, users, userService);

        // Запуск начального меню
        runApplication.choise();
    }
}
