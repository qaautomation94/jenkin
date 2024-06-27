package com.mscs.emr.automation.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OracleDbUtils {
	public static String oracleDriver = Config.getOracleDbConnectionDriverClass();
	public static String hostName = Config.getOracleDbHost();
	public static int portNumber = Config.getOracleDbPort();
	public static String serviceName = Config.getOracleDbService();
	public static String userName = Config.getOracleDbUser();
	public static String password = Config.getOracleDbPassword();
	
	
//	 public static String oracleDriver ="oracle.jdbc.driver.OracleDriver";
//	    public static String hostName = "192.168.90.10";
//	    public static int portNumber = 1521;
//	    public static String serviceName = "searoadtest";
//	    public static String userName = "SEAROAD_QA";
//	    public static String password = "SEAROAD_QA";
	    
	    public static String CLGhostName = "192.168.90.10";
	    public static int CLGportNumber = 1521;
	    public static String CLGserviceName = "searoadtest";
	    public static String CLGuserName = "COLORLINE_MASTER";
	    public static String CLGpassword = "COLORLINE_MASTER";
	     Connection conn=null;
	     public static ResultSet resultSet;
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		System.out.println(getRandomActiveLocation("Test-AccountID"));
	}

	private static Connection establishOracleDbConnection() throws ClassNotFoundException, SQLException {

		String dbUrl = null;
		String host = null;
		int port = 0;
		String service = null;
		String dbUser = null;
		String passWord = null;
		host = hostName;
		port = portNumber;
		service = serviceName;
		dbUser = userName;
		passWord = password;

		dbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=" + host + ")(PORT=" + port
				+ ")))(CONNECT_DATA=(SERVER =DEDICATED)(SERVICE_NAME=" + service + ")))";

		Class.forName(oracleDriver);

		return DriverManager.getConnection(dbUrl, dbUser, passWord);
	}


	/* A sample method to retrieve data from the DataBase */
	public static List<String> getRandomActiveLocation(String userName) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Statement stmt = null;

		String practiceSql = "Select LOCATIONNAME from HI_MD_PRSNTN.USER_SECURITY_DIV_LOC where USEREID = '" + userName
				+ "' order by LOCATIONNAME";

		String locations = null;
		List<String> locationNames = new ArrayList<String>();

		try {
			conn = establishOracleDbConnection();
			stmt = conn.createStatement();

			ResultSet address = stmt.executeQuery("");

			while (address.next()) {
				locations = address.getString(1);
				locations = locations.trim();
				locationNames.add(locations);
			}

		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return locationNames;
	}
	 /* A sample method to retrieve data from the DataBase */
    public  void executeQuery(String userName) throws ClassNotFoundException, SQLException {

    			createDBConnection("sds");

                System.out.println("Connected with connection #1");
                Statement stmt = null;
                LocalDateTime now = null;
                stmt = conn.createStatement();
                	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
                	 for(int day=0; day<=100; day++) {
                     now = LocalDateTime.now().plusDays(day);
                     System.out.println(dtf.format(now));
                     String date=dtf.format(now);
                     System.out.println("SELECT * from MSP_SAILING WHERE code like '"+"MELDPO"+date+"%'");
                     ResultSet resultSet = stmt.executeQuery("SELECT * from MSP_SAILING WHERE code like '"+"MELDPO"+date+"%'");
                     System.out.println(resultSet);
                     Boolean str=resultSet.next();
                     		if(!str) {
                     				break;
                     		}
                     
                }
//                	 System.out.println( now.getYear());
//                	 TestData.dateForSailingCreation=dtf.format(now);
//                	 System.out.println(TestData.dateForSailingCreation);
//                	 System.out.println(now);
//                	 System.out.println(now.getDayOfMonth()); //11, 12, 13
//                	 TestData.date= String.valueOf(now.getDayOfMonth());
//                	 System.out.println(now.getMonth()); // Month Name
//                	 TestData.month= String.valueOf(now.getMonth());
//                	 TestData.month=(String) TestData.month.subSequence(0, 3);
//                	 System.out.println(TestData.month.subSequence(0, 3)); // Month Name


 }
    public  void executeCalmacQuery(String query) throws ClassNotFoundException, SQLException {
    	
    	if(conn==null) {
        	createCalmacDBConnection();

    	}
//    	   Connection conn=null;
   	    Statement stmt = null;
//           LocalDateTime now = null;
           stmt = conn.createStatement();
           System.out.println(query);
//           resultSet = stmt.executeQuery(query);
        
          resultSet = stmt.executeQuery(query);

    }
    
    /* A sample method to retrieve data from the DataBase */
    public  void executeCLGQuery(String userName) throws ClassNotFoundException, SQLException {

    	createCLGDBConnection("sds");

                System.out.println("Connected with connection #1");
                Statement stmt = null;
                LocalDateTime now = null;
                stmt = conn.createStatement();
                	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
                	 for(int day=0; day<=100; day++) {
                     now = LocalDateTime.now().plusDays(day);
                     System.out.println(dtf.format(now));
                     String date=dtf.format(now);
                     System.out.println("SELECT * from MSP_SAILING WHERE code like '"+"OSLKEL"+date+"%'");
                     ResultSet resultSet = stmt.executeQuery("SELECT * from MSP_SAILING WHERE code like '"+"OSLKEL"+date+"%'");
                     System.out.println(resultSet);
                     Boolean str=resultSet.next();
                     		if(!str) {
                     				break;
                     		}
                     
                }
//                	 System.out.println( now.getYear());
//                	 TestData.dateForSailingCreation=dtf.format(now);
//                	 System.out.println(TestData.dateForSailingCreation);
//                	 System.out.println(now);
//                	 System.out.println(now.getDayOfMonth()); //11, 12, 13
//                	 TestData.date= String.valueOf(now.getDayOfMonth());
//                	 System.out.println(now.getMonth()); // Month Name
//                	 TestData.month= String.valueOf(now.getMonth());
//                	 TestData.month=(String) TestData.month.subSequence(0, 3);
//                	 System.out.println(TestData.month.subSequence(0, 3)); // Month Name


 }
    /* A sample method to retrieve data from the DataBase */
    public  void VerifyStatus(String userName) throws ClassNotFoundException, SQLException {

    	createDBConnection("sds");
        List<String> list = new ArrayList<String>();

                System.out.println("Connected with connection #1");
                Statement stmt = null;
                stmt = conn.createStatement();
                	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
                	 for(int day=0; day<=100; day++) {
                		 
                
                     LocalDateTime now = LocalDateTime.now().plusDays(day);
                     System.out.println(dtf.format(now));
                     String date=dtf.format(now);
                     System.out.println("SELECT * from MSP_SAILING WHERE code like '"+"MELDPO"+date+"%'");
                     ResultSet resultSet = stmt.executeQuery("SELECT * from MSP_SAILING WHERE code like '"+"MELDPO"+date+"%'");
//                     System.out.println("ResultSet"+ resultSet.getString("CODE"));
                     System.out.println(resultSet);
                     Boolean str=resultSet.next();
                     		if(!str) {
                     				break;
                     		}
                     else {
                    	
                        resultSet = stmt.executeQuery("SELECT * from MSP_SAILINGSTATES WHERE SAILINGCODE like '"+"MELDPO"+date+"%'");
                     
                        while(resultSet.next()) {
                        		System.out.println("ResultSet"+ resultSet);
                        		resultSet.getString("STATENAME");
                        		System.out.println("ResultSet"+ resultSet.getString("STATENAME"));
                        		list.add(resultSet.getString("STATENAME"));
                        	}
                        
                        String[] str1 = { "OPEN PRE CHECK-IN", "OPEN GATE", "CLOSE CHECK-IN", "CLOSE GATE", "CLOSE GATE", "CLOSE SAILING" };
                       int len= str1.length;
                       int len2=list.size();
                       System.out.println(len);
                       System.out.println(len2);
                        	for(int a=0; a<len2; a++) {
                        			for(int b=0; b<len; b++) {
                        				System.out.println(list.get(a) +"=="+str1[b]);
                        				if(list.get(a) ==str1[b]) {
                        					break;
                        				}
                        			}
                        		
                        	}
                        break;
                     }
                }
           
               System.out.println(list.get(0));
 }

    /* A sample method to retrieve data from the DataBase */
    public  OracleDbUtils createDBConnection(String userName) throws ClassNotFoundException, SQLException {
      
      
        try {
            // registers Oracle JDBC driver - though this is no longer required
            // since JDBC 4.0, but added here for backward compatibility
            Class.forName("oracleDriver");
           

            // METHOD #1
         //   String dbURL1 = "jdbc:oracle:thin:SEAROAD_QA/SEAROAD_QA@192.168.90.10:1521:searoadtest";

            String dbURL1 = "jdbc:oracle:thin:SEAROAD_QA/SEAROAD_QA@192.168.90.10:1521:SEAROAD_QA";
            conn = DriverManager.getConnection(dbURL1);
            if (conn != null) {
            	 System.out.println("Connected with connection #1");
            }
          
          
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } 
        return this;
    }
    
    /* A sample method to retrieve data from the DataBase */
    public  OracleDbUtils createCalmacDBConnection() throws ClassNotFoundException, SQLException {
      
      
        try {

            Class.forName(oracleDriver);

//            String dbURL1 = "jdbc:oracle:thin:CALMAC_RC/CALMAC_RC@192.168.90.20:1521:EDEA19C";
            String dbURL1 = "jdbc:oracle:thin:"+userName+"/"+password+"@" + hostName + ":" + portNumber + ":"+serviceName;

            conn = DriverManager.getConnection(dbURL1);
            if (conn != null) {
            	 System.out.println("Connected with connection #1");
            }
          
          
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } 
        return this;
    }
    public  OracleDbUtils createCLGDBConnection(String userName) throws ClassNotFoundException, SQLException {
        
        
        try {
            // registers Oracle JDBC driver - though this is no longer required
            // since JDBC 4.0, but added here for backward compatibility
            Class.forName("oracleDriver");
            List<String> list = new ArrayList<String>();

            // METHOD #1
            String dbURL1 = "jdbc:oracle:thin:COLORLINE_MASTER/COLORLINE_MASTER@192.168.90.10:1521:COLORLINE_MASTER";
            conn = DriverManager.getConnection(dbURL1);
            if (conn != null) {
            	 System.out.println("Connected with connection #1");
            }
          
          
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } 
        return this;
    }

}
