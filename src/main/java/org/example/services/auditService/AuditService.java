package org.example.services.auditService;

import org.example.models.AuditLog;
import org.example.models.User;
import org.example.services.userService.UserService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Класс AuditService
 * описывает методы работы с объектами типа AuditLog
 * **/
public class AuditService {
    //region Fields
    private List<AuditLog> logs;
    private UserService userService;
    //endregion

    //region Constructors
    public AuditService(List<AuditLog> logs, UserService userService) {
        this.logs = logs;
        this.userService = userService;
    }
    //endregion


    public void logFilter(LocalDateTime time, Integer userId, String action) {

    }

    /**
     * метод выодит содержимое всех лог-файлов в консоль
     *
     * @param directory - директория в которой лежат лог-файлы
     **/
    public static void printLogFiles(String directory) {
        Path dirPath = Paths.get(directory);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "*.txt")) {
            boolean logFilesFound = false;
            for (Path entry : stream) {
                logFilesFound = true;
                System.out.println("Содержимое " + entry.toString() + ":");
                System.out.println("----------------------------------------");

                // Читаем и выводим содержимое лог-файла
                Files.lines(entry).forEach(System.out::println);

                System.out.println("----------------------------------------\n");
            }
            if (!logFilesFound) {
                System.out.println("В директории не найдено ни одного файла.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void printAllLogFiles(String directory) {
//        Path dirPath = Paths.get(directory);
//        List<AuditLog> newLogs = new ArrayList<>();
//        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "*.txt")) {
//            boolean logFilesFound = false;
//            for (Path entry : stream)
//            {
//                logFilesFound = true;
//                System.out.println("Содержимое " + entry.toString() + ":");
//                System.out.println("----------------------------------------");
//
//                // Читаем и выводим содержимое лог-файла
//                try {
//                    List<String> lines = Files.lines(entry).collect(Collectors.toList());
//
//                    // Преобразование строк в объекты AuditLog и добавление их в список logs
//                    for (String line : lines)
//                    {
//                        System.out.println(line); // Печать строки
//                        AuditLog log = parseLineToAuditLog(line); // Преобразование строки в AuditLog
//                        newLogs.add(log); // Добавление в список logs
//                    }
//                }
//                catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//
//                // Дополнительные действия с logs
//                newLogs.forEach(System.out::println);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }


//    private static AuditLog parseLineToAuditLog(String line)
//    {
//        // Пример преобразования строки в объект AuditLog
//        // Предполагается, что строка имеет формат "field1,field2,field3"
//        String[] fields = line.split(",");
//        int id = Integer.parseInt(fields[0]);
//        String action = fields[1];
//        //String timestamp = fields[2];
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime timestamp = LocalDateTime.parse(fields[2], formatter);
//        return new AuditLog(id, action, timestamp);
//    }

    // Метод для вывода отфильтрованных лог-файлов
    public void getLogsByFilter(int userId, String action, LocalDateTime timestamp)
    {
        for (AuditLog log : logs)
        {
            boolean matchUserId = (userId == 0 || log.getUserId() == userId);
            boolean matchAction = (action == null || action.isEmpty() || log.getAction().equalsIgnoreCase(action));
            boolean matchTimestamp = (timestamp == null || log.getTimestamp().isEqual(timestamp));

            if (matchUserId && matchAction && matchTimestamp)
            {
                System.out.println(log);
            }
        }
    }

    public int inputParamUserId()
    {
        Scanner scanner = new Scanner(System.in);
        int userId = -1;

        while (userId < 0) {
            System.out.print("Введите userId: ");
            String userIdInput = scanner.nextLine().trim();

            try
            {
                userId = Integer.parseInt(userIdInput);
                if (userId < 0) {
                    System.out.println("userId не может быть отрицательным. Пожалуйста, попробуйте снова.");
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Некорректный формат userId. Пожалуйста, введите целое число.");
            }
        }
        return userId;
    }

    public String inputParamAction()
    {
        Scanner scanner = new Scanner(System.in);
        String action = "";

        while (action.isEmpty())
        {
            System.out.print("Введите действие: ");
            action = scanner.nextLine().trim();

            if (action.isEmpty())
            {
                System.out.println("Действие не может быть пустым. Пожалуйста, попробуйте снова.");
            }
        }
        return action;
    }

    public LocalDateTime inputParamLocalDataTime()
    {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime dateTime = null;

        while (dateTime == null)
        {
            System.out.print("Введите дату (дд.ММ.гггг): ");
            String dateInput = scanner.nextLine().trim();

            System.out.print("Введите время (чч:мм): ");
            String timeInput = scanner.nextLine().trim();

            String dateTimeInput = dateInput + " " + timeInput;

            try
            {
                dateTime = LocalDateTime.parse(dateTimeInput, formatter);
                System.out.println("Вы ввели дату и время: " + dateTime);
            }
            catch (Exception e)
            {
                System.out.println("Неверный формат даты и/или времени. Пожалуйста, попробуйте снова.");
            }
        }

        scanner.close();
        return dateTime;
    }



    /**
     * Метод производит запись (логирование) действия пользователя.
     * Создает новый объект AuditLog с уникальным идентификатором
     * и добавляет его в коллекцию логов.
     * @param userId идентификатор пользователя, совершившего действие.
     * @param action описание действия, которое нужно залогировать.
     */
    public AuditLog logAction(int userId, String action)
    {
       AuditLog auditLog = new AuditLog(userId, action, LocalDateTime.now()); // создаём новый лог
       logs.add(auditLog);                                                   // добавляем новый лог в список логов
       saveLog(auditLog);                                                   // сохраняем лог в лог-файл
       return auditLog;                                                    // возвращаем созданный лог
    }


    /**
     * Метод производит запись лога в файл с именем .
     * @param auditLog - переданный лог с данными о произошедшем действии
     */
        public void saveLog(AuditLog auditLog)
        {
        // Форматирование текущей даты и времени для включения в имя файла
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        // Указание директории
        String directoryPath = "D:" + File.separator + "Y_LAB" + File.separator + "AutoShop" + File.separator + "logs";
        File directory = new File(directoryPath);

        // Проверка существования директории и создание при необходимости
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Формирование имени файла
        String fileName = directoryPath + File.separator + "log_userId-" + auditLog.getUserId()
                + "_action-" + auditLog.getAction() + "_" + formattedDateTime + ".txt";

        try (FileWriter writer = new FileWriter(fileName, true))   // режим дозаписи append: true
        {
            // Запись содержимого auditLog в файл
            writer.write("Log ID: " + auditLog.getId() + "\n");
            writer.write("User ID: " + auditLog.getUserId() + "\n");
            writer.write("Action: " + auditLog.getAction() + "\n");
            writer.write("Timestamp: " + auditLog.getTimestamp().toString() + "\n");
            writer.write("-------------------------\n"); // Разделитель между записями
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * метод получает все логи
     * @return new ArrayList<>(logs) - список логов
     * **/
    public List<AuditLog> getAllLogs()
    {
        printLogs();
        return logs;
    }

    public void printLogs()
    {
        for (AuditLog log: logs)
        {
            System.out.println(log);
        }
    }
    /**
     * метод получает все логи по заданному id пользователя
     * @return result - список логов по заданному id пользователя
     * **/
    public List<AuditLog> getLogsByUser(int userId)
    {
        List<AuditLog> result = new ArrayList<>();
        for (AuditLog log : logs)
        {
            if (log.getUserId() == userId)
            {
                result.add(log);
            }
        }
        return result;
    }

    /**
     * метод получает все логи по заданному действию
     * @param action  - действие
     * @return result - список логов по заданному действию
     * **/
    public List<AuditLog> getLogsByAction(String action)
    {
        List<AuditLog> result = new ArrayList<>();
        for (AuditLog log : logs)
        {
            if (log.getAction().equals(action))
            {
                result.add(log);
            }
        }
        return result;
    }
}
