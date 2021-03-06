package javaWebService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class ConnectingToPostgreSQL {

	//if openConnection has an issue, see this: http://alvinalexander.com/java/jdbc-connection-string-mysql-postgresql-sqlserver
	//actually just use syntax from the actual heroku site 
    static String openConnection = "jdbc:postgresql://<ec2-54-227-253-238.compute-1.amazonaws.com>:<5432>/<d20hohh2uq3tib>?user=<lqwgfavqzszazx>&password=<dYfO-uBInG-WGgkqGFpz4GHAjc>&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory"; 
    private static String connectionAnswer; //seems like this should be elsewhere or is just a duplicate in general? static String connectionAnswer= "empty";  	
    
    
    public String getSQLData() {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;
    
    try { 
        Class.forName("org.postgresql.Driver"); //Before you can connect to a database, you need to load the driver. This is one of the 2 standard ways
        connection = DriverManager.getConnection(openConnection); //used to get a Connection instance from JDBC; global JDBC method
        statement = connection.createStatement(); //Any time you want to issue SQL statements to the database, you require a Statement or PreparedStatement instance.
        String querySQL = "SELECT price* FROM apartmentinventory"; //"SELECT attribute * FROM tableName WHERE condition", Example: �SELECT price FROM ApartmentListDatabaseTable WHERE price < 4000�
        result = statement.executeQuery(querySQL); //result is an instance of ResultSet. executeQuery returns a ResultSet instance which contains the entire result
        //note that by default the Driver collects all the results for the query at once. You can make it fetch only a few rows however using cursors 
        connectionAnswer = "";
        while (result.next()) { //before reading any values you must call next() This returns true if there is a result, but more importantly, it prepares the row for processing.
            int id = result.getInt("price");
            connectionAnswer = connectionAnswer + id + " | " +  "\n";
        } 
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getMessage());
        System.err.println("Error: Cant connect!");
        connection = null;
    } finally { 
        if (result != null) {
            try { 
                result.close(); //you must close a ResultSet (i.e. "result")
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            } 
        } 
        if (statement != null) {
            try { 
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            } 
        } 
        if (connection != null) {
            try { 
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            } 
        } 
    }//end of finally 
    System.err.println("----- PostgreSQL query ends correctly!-----");
    return null;  
    
    	}//should match up to end of this method
    
    public String getAnswerSql (){
        //execute(); //>from stackoverflow but not in official oracle docs, caused an error and seems uneccessary just returns true or false
        return connectionAnswer;
    		}
    
    
}//end of ConnectingToPostgreSQL class    
