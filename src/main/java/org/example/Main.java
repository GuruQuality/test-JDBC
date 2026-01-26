package org.example;

import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String currentDir = System.getProperty("user.dir");
        String url = "jdbc:h2:.\\Office\\Office";
        try {
            Connection con = DriverManager.getConnection(url);
            if (con != null) {
                System.out.println("Connection opened");
            } else {
                System.out.println("Failed to make connection");
            }
            // Создание оператора для выполнения SQL-запросов
            Statement statement = con.createStatement();

            //3. Выведите на экран количество сотрудников в IT-отделе
            // Выполнение SQL-запроса и получение результата в виде ResultSet
            ResultSet rs = statement.executeQuery("Select count(id) from EMPLOYEE e  Where DEPARTMENTID  = 2;");
            int employeeCount = rs.next() ? rs.getInt(1) : 0;
            System.out.println("количество сотрудников в IT-отделе: " + employeeCount);
            con.close();
            //sampocus k 6aue данных выполнять здесь
        } catch (
                SQLException ex) {
            System.out.println(ex);
        }
    }
    //Задание #1. JDBC Тесты. Сотрудники
    //Подключитесь программно к базе данных и выполните следующие операции:
    //
    //Найдите ID сотрудника с именем Ann. Если такой сотрудник только один, то установите его
    //департамент в HR.
    //Проверьте имена всех сотрудников. Если чьё-то имя написано с маленькой буквы, исправьте её на большую. Выведите на экран количество исправленных имён.
    //Каждая задача
    //оценивается в один балл. Ответ оформите в виде Java-метода c кодом запроса. Максимум 3 балла.
}