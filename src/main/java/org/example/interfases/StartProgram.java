package org.example.interfases;

/**
 * Функциональный интерфейс StartProgram
 * имеет единственный метод start()
 * создан для передачи в меню пользователей
 * для корректного выхода из меню, что бы избежать зацикливания
 * и передачу в эти классы запускающего Класса RunApplication с его методом choise()
 * **/
@FunctionalInterface
public interface StartProgram
{
    void start();
}
