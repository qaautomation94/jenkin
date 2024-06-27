package com.mscs.emr.automation.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

public class MySqlDbUtils {
    private  String mySqlDriverObj = Config.getMySqlDbConnectionDriverClass();
    private  String hostName = Config.getMySqlDbHost();
    public static  String CATS = Config.getMySqlDbName();
    public static  String OLDB = Config.getMySqlDbNameOLDB();
    private  String dbUser = Config.getMySqlDbUser();
    private  String password = Config.getMySqlDbPassword();
    private  JSONObject JSONData;
    private  static MySqlDbUtils single_instance = null;
    private  Connection connection = null;
    static ResultSet queryResult;
    public static String dbName=null;
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException  {
    }
    private Connection establishMySqlDbConnection() throws ClassNotFoundException, SQLException{  
        String connectionUrl = this.hostName+";" + "database=" + dbName + ";" + "user=" + dbUser+";" + "password=" + this.password + ";";
        Class.forName(this.mySqlDriverObj);
        	 connection = DriverManager.getConnection(connectionUrl); 
        return connection;
    }  
    /**
     * @param dbname in is the database name
     * @return This method returns the name of database
     */
    public static String setDatabase(String dbname)
    {
    return dbName=dbname;
    }

    public Connection getDBConnection() throws SQLException, ClassNotFoundException {
        return establishMySqlDbConnection();
    }
    public  ResultSet getQueryResult(String dbQuery) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, InterruptedException
    {
       queryResult = getDBConnection().createStatement().executeQuery(dbQuery);
        closeDbConn();
        return queryResult;
    }
    
    public static String getColumnValue(String colName) throws SQLException {
    	String  result=null;
    	  while (queryResult.next()) {
              System.out.println("resultSet "+ queryResult);
              result =queryResult.getString(colName);
              }
            	return result;
          
    }
    
    public MySqlDbUtils() throws SQLException, ClassNotFoundException {

    }

    public static MySqlDbUtils getInstance() {
        if (single_instance == null) {
            try {
                single_instance = new MySqlDbUtils();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return single_instance;
    }

    public  void closeDbConn() throws SQLException, ClassNotFoundException { establishMySqlDbConnection().close();}
}
