package org.example;

import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
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

            //1. Найдите ID сотрудника с именем Ann. Если такой сотрудник только один, то установите его
            //департамент в HR.
            //Получение метаданных из таблицы
//            ResultSet rs1 = statement.executeQuery("Select * from Employee");
//            ResultSetMetaData rsmd = rs1.getMetaData();
//            int cols = rsmd.getColumnCount();
//            for (int i = 1; i <= cols; i++) {
//                System.out.println(rsmd.getColumnName(i));
//            }
            ResultSet employee = statement.executeQuery("Select id,DEPARTMENTID from EMPLOYEE e  Where NAME  = 'Ann';");
            employee.last();
            int countAnn = employee.getRow();
            System.out.println("Колличество сотрудников с именем Ann: " + countAnn);
            employee.beforeFirst();
            int employeeID = employee.next() ? employee.getInt(1) : 0;
            int DEPARTMENTID = employee.getInt(2);
            System.out.println("ID сотрудника Ann: " + employeeID);
            //При повторном запуске id поменяется
            System.out.println("ID департамента Ann: " + DEPARTMENTID);

            ResultSet rs1 = statement.executeQuery("Select id from DEPARTMENT e  Where NAME  = 'HR';");
            int targetDepartmentID = rs1.next() ? rs1.getInt(1) : 0;
            System.out.println("ID Департамента HR: " + targetDepartmentID);

            int rs3 = statement.executeUpdate("UPDATE EMPLOYEE SET DEPARTMENTID=" + targetDepartmentID + " Where id  =" + employeeID + ";");

            //2. Проверьте имена всех сотрудников. Если чьё-то имя написано с маленькой буквы, исправьте её на большую. Выведите на экран количество исправленных имён.
            ResultSet rs2 = statement.executeQuery("Select id, name from EMPLOYEE e");
            int count = 0; // Счётчик исправленных имён

            //Перебираем все записи
            while (rs2.next()) {
                int id = rs2.getInt("id");
                String name = rs2.getString("name");
                // - Проверяем, начинается ли имя с маленькой буквы
                if (name != null && !name.isEmpty() && Character.isLowerCase(name.charAt(0))) {
                    // Делаем первую букву заглавной
                    String fixedName = name.substring(0, 1).toUpperCase() + name.substring(1);

                    // Обновляем записи в базе
                    PreparedStatement pStmt = con.prepareStatement("UPDATE EMPLOYEE SET name = ? WHERE id = ?");
                    // ПРАВИЛЬНО:
                    pStmt.setString(1,fixedName);
                    pStmt.setInt(2, id);
                    pStmt.executeUpdate();
                    count++;
                }
            }
            // Выводим результат
            System.out.println("\nВсего исправлено имён: " + count);
            System.out.println();
            //3. Выведите на экран количество сотрудников в IT-отделе
            // Выполнение SQL-запроса и получение результата в виде ResultSet
            ResultSet rs = statement.executeQuery("Select count(id) from EMPLOYEE e  Where DEPARTMENTID  = 2;");
            int employeeCount = rs.next() ? rs.getInt(1) : 0;
            System.out.println("количество сотрудников в IT-отделе: " + employeeCount);
            statement.close();
            //sampocus k 6aue данных выполнять здесь
        } catch (
                SQLException ex) {
            System.out.println(ex);
        }
    }
}