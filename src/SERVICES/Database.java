package SERVICES;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/internlink?useTimezone=true&serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "IAmTheKingBoboWab";
    private static Connection conn = null;

    private static Connection getConnection(){
        if(conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            }
            catch (ClassNotFoundException e) { e.printStackTrace(); }
            catch (SQLException ee) { printSQLException(ee); }
        }
        return conn;
    }

    public static PreparedStatement prepareNewStatement(String parameterizedQuery) throws SQLException {
        Connection connection = getConnection();
        if(connection == null) { System.out.println("Could not connect to the database!"); return null; }
        return connection.prepareStatement(parameterizedQuery);
    }

    public static PreparedStatement prepareStatementAndReturnKey(String query) throws SQLException {
        Connection connection = getConnection();
        if(connection == null) { System.out.println("Could not connect to the database!"); return null; }
        return connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }

    public static void executeNewUpdate(PreparedStatement prepStmt) {
        Connection connection = getConnection();
        if(connection == null) { System.out.println("Could not connect to the database!"); return; }
        try { prepStmt.executeUpdate(); }
        catch (SQLException ee) { printSQLException(ee); }
    }

    public static ResultSet executeSelect(PreparedStatement prepStmt) {
        Connection connection = getConnection();
        if(connection == null) { System.out.println("Could not connect to the database!"); return null; }
        try { return prepStmt.executeQuery(); }
        catch (SQLException ee) { printSQLException(ee); }
        return null;
    }

    /** track connection errors - from "javaguides.net" */
    private static void printSQLException(SQLException ex) {
        for (Throwable error : ex) {
            if (error instanceof SQLException) {
                error.printStackTrace(System.err);
                System.err.println("SQL State: " + ((SQLException) error).getSQLState());
                System.err.println("Error Code: " + ((SQLException) error).getErrorCode());
                System.err.println("Message: " + error.getMessage());

                Throwable thr = error.getCause();
                while (thr != null) {
                    System.out.println("Cause: " + thr);
                    thr = thr.getCause();
                }
            }
        }
    }

}
