package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        int departmentId = Integer.parseInt(args[0]);
        String url = "jdbc:h2:.\\Office\\Office";
        try (Connection con = DriverManager.getConnection(url)) {
            if (con != null) {
                System.out.println("Connection opened");
            } else {
                System.out.println("Failed to make connection");
            }
            // Создание оператора для выполнения SQL-запросов
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            // «При удалении отдела (Department)
            //информация о всех сотрудниках, работающих в этом отделе, должна быть удалена»

            PreparedStatement ps = con.prepareStatement("Select count(id) from EMPLOYEE e  Where DEPARTMENTID  = ?;");
            ps.setInt(1, departmentId);
            ResultSet rs = ps.executeQuery();
            int employeeCount = rs.next() ? rs.getInt(1) : 0;
            System.out.println("количество сотрудников в отделе(id#" + departmentId + "): " + employeeCount);
            statement.close();


        } catch (
                SQLException ex) {
            System.out.println(ex);
        }
    }
}
//Выполните действия по порядку:
//
//Запустите приложение.
//Удалите один из отделов.
//Выполните проверку содержимого базы.