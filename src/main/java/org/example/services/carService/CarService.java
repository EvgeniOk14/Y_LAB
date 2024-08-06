package org.example.services.carService;

import org.example.models.Car;
import org.example.models.User;
import org.example.services.auditService.AuditService;
import java.util.List;
import java.util.Scanner;

/**
 *  Класс CarService
 * описывает методы для работы с объектом Car (автомобиль)
 * **/
public class CarService
{

    //region Fields
    private  final List<Car> cars;
    private Scanner scanner = new Scanner(System.in);
    private  AuditService auditService;
    private Integer yearOfProduction;
    private Integer priceOfProduction;
    //endregion

    //region Constructors
    public CarService(AuditService auditService, List<Car> cars)
    {
        this.auditService = auditService;
        this.cars = cars;
    }
    //endregion

    /**
     * метод создаёт новый автомобиль
     * @return car  возвращает новый автомобиль
     * **/
    public Car createNewCar(User user)
    {
        System.out.println("Введите марку автомобиля: ");
        String brand = scanner.nextLine();

        System.out.println("Введите модель автомобиля: ");
        String model = scanner.nextLine();



        boolean yearOfProductionFlad = true;
        while (yearOfProductionFlad)
        {
            System.out.println("Введите год выпуска автомобиля: ");
            String stringYearOfProduction = scanner.nextLine();
            try
            {
                yearOfProduction = Integer.parseInt(stringYearOfProduction);
                yearOfProductionFlad = false;
            }
            catch (NumberFormatException e)
            {
                System.out.println("Вы ввели не число! ");
            }
        }

        boolean priceFlad = true;
        while (priceFlad)
        {
            System.out.println("Введите цену автомобиля в рублях: ");
            String stringPrice = scanner.nextLine();
            try
            {
                priceOfProduction = Integer.parseInt(stringPrice);
                priceFlad = false;
            }
            catch (NumberFormatException e)
            {
                System.out.println("Вы ввели не число! ");
            }
        }

        System.out.println("Введите состояние автомобиля новый: (новый / (б/у)) ");
        String condition = scanner.nextLine();

        Car car = new Car(brand, model, yearOfProduction, priceOfProduction, condition);
        System.out.println("Вы создали новый автомобиль: " + car);

        auditService.logAction(user.getId(), "заполнение данных на новый автомобиль"); // логирование события - создание нового автомобиля

        return car;
    }

    /**
     * метод добавления новых автомобилей в список (автосалон)
     * @param car - новый созданный автомобиль
     * **/
    public void addNewCar(Car car, User user)
    {
        listCarsAddTemp(user).add(car); // временно что бы не набивать список в ручную каждый раз при новом запуске программы
        auditService.logAction(user.getId(), "добавление нового автомобиля в салон"); // логирование события - добавление нового автомобиля в список (автосалон)
    }

    // метод временный, для демонстрации, что бы не набивать список в ручную каждый раз при новом запуске программы
    public List<Car> listCarsAddTemp(User user)
    {
        Car car1 = new Car("Mersedes", "SEl-600", 2024, 40585000, "новый");
        auditService.logAction(user.getId(), "заполнение данных на новый автомобиль"); // логирование события - создание нового автомобиля
        Car car2 = new Car("BMW", "760", 2024, 4000000, "новый");
        auditService.logAction(user.getId(), "заполнение данных на новый автомобиль"); // логирование события - создание нового автомобиля
        Car car3 = new Car("AUDI", "A8", 2024, 38885000, "новый");
        auditService.logAction(user.getId(), "заполнение данных на новый автомобиль"); // логирование события - создание нового автомобиля
        Car car4 = new Car("LEXUS", "LS-600H", 2024, 12000000, "новый");
        auditService.logAction(user.getId(), "заполнение данных на новый автомобиль"); // логирование события - создание нового автомобиля
        Car car5 = new Car("TOYOTA", "CAMRY", 2024, 5500000, "новый");
        auditService.logAction(user.getId(), "заполнение данных на новый автомобиль"); // логирование события - создание нового автомобиля
        Car car6 = new Car("INFINITY", "QX-500", 2024, 8000000, "новый");
        auditService.logAction(user.getId(), "заполнение данных на новый автомобиль"); // логирование события - создание нового автомобиля
        Car car7 = new Car("NISSAN", "PATROL", 2024, 7000000, "новый");
        auditService.logAction(user.getId(), "заполнение данных на новый автомобиль"); // логирование события - создание нового автомобиля
        cars.add(car1);
        auditService.logAction(user.getId(), "добавление нового автомобиля в салон"); // логирование события - добавление нового автомобиля в список (автосалон)
        cars.add(car2);
        auditService.logAction(user.getId(), "добавление нового автомобиля в салон"); // логирование события - добавление нового автомобиля в список (автосалон)
        cars.add(car3);
        auditService.logAction(user.getId(), "добавление нового автомобиля в салон"); // логирование события - добавление нового автомобиля в список (автосалон)
        cars.add(car4);
        auditService.logAction(user.getId(), "добавление нового автомобиля в салон"); // логирование события - добавление нового автомобиля в список (автосалон)
        cars.add(car5);
        auditService.logAction(user.getId(), "добавление нового автомобиля в салон"); // логирование события - добавление нового автомобиля в список (автосалон)
        cars.add(car6);
        auditService.logAction(user.getId(), "добавление нового автомобиля в салон"); // логирование события - добавление нового автомобиля в список (автосалон)
        cars.add(car7);
        auditService.logAction(user.getId(), "добавление нового автомобиля в салон"); // логирование события - добавление нового автомобиля в список (автосалон)
        return cars;
    }
    //endregion

    /**
     *  метод получения всех автомобилей
     * @return new ArrayList<>(cars)
     * **/
    public List<Car> getAllCars()
    {
        if (!cars.isEmpty() && cars != null)
        {
            System.out.println(cars);
            return cars;
        }
        else
        {
            System.out.println("Список автомобилей пуст. Вначале нужно добавить автомобили в базу данных!");
        }
        return null;
    }

    /**
     *  метод получения автомобия по его id
     * @param id идентификационный номер автомобиля
     * @return car автомобиль
     * @return null в случае его отсутствия
     * **/
    public Car getCarById(int id)
    {
        for (Car car : cars)
        {
            if (car.getId() == id)
            {
                return car;
            }
        }
        return null;
    }

    /**
     *  метод обновления информации по автомобилю
     * @param  carId - идентификационный номер обновляемого автомобиля
     * **/
    public void updateCar(int carId)
    {
        boolean flag = true;
        while (flag)
        {
            Car car = getCarById(carId);
            if (car != null)
            {
                System.out.println("Найден автомобиль, данные которого Вы хотите редактировать: " + car);

                System.out.println("Выберите параметр автомобиля, который вы хотите изменить: ");

                System.out.println("""
                        1. марку
                        2. модель
                        3. год выпуска
                        4. цена
                        5. состояние
                        6. Выход на один уровень из меню
                        """);

                String choise = scanner.nextLine();
                try
                {
                    Integer intChoise = Integer.parseInt(choise);
                    switch (intChoise)
                    {
                        case 1 ->
                        {
                            System.out.println("марка автомобиля: " + car.getBrand());
                            System.out.println("Введите новую марку автомобиля");
                            String newBrand = scanner.nextLine();
                            car.setBrand(newBrand);
                            System.out.println("Параметр: марка автомобиля - был успешно изменён! ");

                        }
                        case 2 ->
                        {
                            System.out.println("модель автомобиля: " + car.getModel());
                            System.out.println("Введите новую модель автомобиля");
                            String newModel = scanner.nextLine();
                            car.setModel(newModel);
                            System.out.println("Параметр: модель автомобиля - был успешно изменён! ");

                        }
                        case 3 ->
                        {
                            System.out.println("год выпуска автомобиля: " + car.getYear());
                            System.out.println("Введите новый год выпуска автомобиля");
                            Integer newYear = scanner.nextInt();
                            car.setYear(newYear);
                            scanner.nextLine();
                            System.out.println("Параметр: год выпуска автомобиля - был успешно изменён! ");

                        }

                        case 4 ->
                        {
                            System.out.println("цена автомобиля: " + car.getPrice());
                            System.out.println("Введите новую цену автомобиля");
                            Integer newPrice = scanner.nextInt();
                            scanner.nextLine();
                            car.setPrice(newPrice);
                            System.out.println("Параметр: цена автомобиля - был успешно изменён! ");
                        }

                        case 5 ->
                        {
                            System.out.println("состояние автомобиля: " + car.getCondition());
                            System.out.println("Введите новое состояние автомобиля");
                            String newCondition = scanner.nextLine();
                            car.setCondition(newCondition);
                            System.out.println("Параметр: состояние автомобиля - был успешно изменён! ");

                        }
                        case 6 ->
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
                System.out.println("Автомобиль с таким id не найден!");
            }
        }
    }

    /**
     *  метод удаления автомобиля по его id
     * @param  id - идентификациооный номер автомобиля
     * **/
    public void deleteCar(int id)
    {
        Car car = getCarById(id);
        if (car != null)
        {
            cars.remove(car);
        }
        else
        {
            System.out.println("автомобиль с таким id = " + id + "не найден! ");
        }
    }
}

