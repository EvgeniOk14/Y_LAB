package org.example.services.userService;

import org.example.models.User;
import java.util.*;

/**
 * Класс UserService - содержит методы для работы с объектом User
 * **/
public class UserService
{
    //region Fields
    private Map<Integer, User> users;
    private static Scanner scanner = new Scanner(System.in);
    //endregion

    //region Constructors
    public UserService(Map<Integer, User> users)
    {
        this.users = users;
    }
    //endregion

    public Map<Integer, User> getMapOfAllUsers()
    {
        return users;
    }


    /**
     * Метод для нахождения ID пользователя по его логину, паролю и роли.
     *
     * @param userLogin    Логин пользователя.
     * @param userPassword Пароль пользователя.
     * @param userRole     Роль пользователя.
     * @return ID пользователя, если найден, или -1, если пользователь не найден.
     */
    public int getIdByUserDetails(String userLogin, String userPassword, String userRole)
    {
        for (Map.Entry<Integer, User> entry : users.entrySet())
        {
            User user = entry.getValue();
            if (user.getUserLogin().equals(userLogin) &&
                    user.getUserPassword().equals(userPassword) &&
                    user.getUserRole().equals(userRole))
            {
                return user.getId();
            }
        }
        return -1; // Возвращаем -1, если пользователь не найден
    }

    /**
     * Метод добавляет нового пользователя в список пользователей
     * @param user объект типа User, новый пользователь
     */
    public Map<Integer, User> addNewUser(User user)
    {
        users.put(user.getId(), user);
        return users;
    }
    /**
     * Метод возвращает пользователя по указанному порядковому номеру id.
     * @param id порядковый номер пользователя, для которого необходимо найти объект User.
     * @return объект User  по указанному номеру id.
     */
    public User getUserById(Integer id, Map<Integer, User> users)
    {
        for (User user: users.values())
        {
            if (user.getId() == id)
            {
                return user;
            }
            else
            {
                throw new IllegalArgumentException("Пользователь с таким именем не найден!");
            }
        }
        return null;
    }

    /**
     * Метод возвращает список пользователя по указанному имени пользователя (логину).
     *
     * @param username имя пользователя, для которого необходимо найти объект User.
     * @return List<User>  список объектов User, соответствующий указанному имени пользователя, или null, если пользователей с таким именем не найдено.
     * @throws IllegalArgumentException если список List<User> равен null, т.е. ни один пользователь с таким именем не был найден
     */
    public List<User> getUsersByUsername(String username)
    {
        List<User> listOfFounUsers = new ArrayList<>();
        for (User user: users.values())
        {
            if (user.getUserLogin().equals(username))
            {
                listOfFounUsers.add(user);
                return listOfFounUsers;
            }
            else
            {
                throw new IllegalArgumentException("Пользователей с таким именем не найден!");
            }
        }
        return listOfFounUsers;
    }

    /**
     * Проверяет, существует ли пользователь с указанным логином в списке пользователей.
     * @param username Логин пользователя, который нужно проверить.
     * @return true, если пользователь с заданным логином существует в списке пользователей;
     * @return false в противном случае.
     */
    public boolean checkUsersByUsername(String username)
    {
        for (User user: users.values())
        {
            if (user.getUserLogin().equals(username))
            {
                return true;
            }
        }
        return false;
    }


    /**
     * Метод обновляет пользователя в списке пользователей
     * @param id  порядковый номер пользователя
     * @param id  пользователя
     * **/
    public void updateUser(Integer id)
    {
            boolean flag = true;
            while (flag)
            {
                User user = getUserById(id, users);
                if (user != null)
                {
                    System.out.println("Найден пользователь, данные которого Вы хотите редактировать: " + user);

                    System.out.println("Выберите параметр пользователя, который вы хотите изменить: ");

                    System.out.println("""
                        1. Логин
                        2. Пароль
                        3. Статус                                             
                        4. Выход на один уровень из меню
                        """);

                    String choise = scanner.nextLine();
                    try
                    {
                        Integer intChoise = Integer.parseInt(choise);
                        switch (intChoise)
                        {
                            case 1 ->
                            {
                                System.out.println("логин пользователя: " + user.getUserLogin());
                                System.out.println("Введите новЫй логин: ");
                                String newLogin = scanner.nextLine();
                                user.setUserLogin(newLogin);
                                System.out.println("Параметр: логин пользователя - был успешно изменён! ");

                            }
                            case 2 ->
                            {
                                System.out.println("пароль пользователя: " + user.getUserLogin());
                                System.out.println("Введите новЫй пароль: ");
                                String newPasswors = scanner.nextLine();
                                user.setUserPassword(newPasswors);
                                System.out.println("Параметр: пароль пользователя - был успешно изменён! ");
                            }
                            case 3 ->
                            {
                                System.out.println("статус пользователя: " + user.getUserLogin());
                                System.out.println("Введите новЫй статус: ");
                                String newStatus = scanner.nextLine();
                                user.setUserRole(newStatus);
                                System.out.println("Параметр: статус пользователя - был успешно изменён! ");
                            }
                            case 4 ->
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
     * Метод удаляет пользователя из списка пользователей
     * @param id  пользователя
     * **/
    public void deleteUser(int id)
    {
        if (users.containsKey(id))
        {
            users.remove(id);
        }
        else
        {
            System.out.println("Пользователя с таким " + id + " в базе не найден ! ");
        }
    }

    /**
     * Метод получает всех пользователей из списка пользователей
     * @return  список всех пользователя
     * **/
    public List<User> getAllUsers(Map<Integer, User> users)
    {
        return new ArrayList<>(users.values());
    }
}
