
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


//changes: made the program use only one DB file for easier reading


public class databaseManager {
    /**
     * Connect to a sample database
     */

    public databaseManager(){
    }

    /*
        creates a database based on the filename chosen

     */


    public void createNewDatabase() {
        Connection conn=connect();

        try {

            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conn.close();
        }catch(Exception e){

        }

    }


    /*
    connects to an already connected database (i.e file) given a valid name
    did not pair the creation and connection together
     */

    public static Connection connect() {
        String userPath = System.getProperty("user.dir") + "/src/db/";
        Connection starter=null;
        try {
            // db parameters
            String url = "jdbc:sqlite:"+userPath+"Bank.db";
            // create a connection to the database
            starter= DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

            return starter;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return starter;
    }

    /*
    this functions initializes multiple tables within a single database
     */
    //fix this
    public void InitializeAllTables(){
        //just fix the intial 3 lines
        Statement stmt;


        //create tables of
        /*
        users
        transactions
        Stocks
        Accounts
        Loans
         */
        Connection conn=connect();

        try {
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS USERS (" +
                    "ACCOUNTID INTEGER UNIQUE , " +
                    "CUSTOMERID TEXT  NOT NULL UNIQUE," +
                    "NAME TEXT NOT NULL," +
                    "PIN TEXT NOT NULL," +
                    "ROLE TEXT NOT NULL," +
                    "PRIMARY KEY (ACCOUNTID AUTOINCREMENT)" +
                    ")";
            stmt.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS ACCOUNTS (" +
                    "ACCOUNTID INTEGER UNIQUE, " +
                    "CUSTOMERID TEXT NOT NULL ," +
                    "NAME TEXT NOT NULL ," +
                    "TYPE TEXT NOT NULL," +
                    "CURRENCY TEXT NOT NULL," +
                    "BALANCE REAL ," +
                    "FEE REAL," +
                    "PRIMARY KEY(ACCOUNTID AUTOINCREMENT)" +
                    ")";
            stmt.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS TRANSACTIONS(" +
                    "ACCOUNTID INTEGER UNIQUE, " +
                    "TYPE TEXT NOT NULL, " +
                    "CUSTOMERID TEXT NOT NULL UNIQUE," +
                    "AMOUNT REAL, " +
                    "CURRENCY TEXT, " +
                    "TARGETACCOUNTID INTEGER, " +
                    "TARGETCUSTOMERID TEXT, " +
                    "COLLATERAL TEXT, " +
                    "DATE TEXT NOT NULL, " +
                    "PRIMARY KEY(ACCOUNTID AUTOINCREMENT)" +

                    ")";
            stmt.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS STOCKS(" +
                    "SYMBOL TEXT NOT NULL UNIQUE, " +
                    "CUSTOMERID TEXT NOT NULL UNIQUE," +
                    "SHARES INTEGER NOT NULL, " +
                    "AVGCOST REAL NOT NULL, " +
                    "PRIMARY KEY(SYMBOL))";
            stmt.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS LOANS (" +
                    "ACCOUNTID INTEGER UNIQUE, " +
                    "CUSTOMERID TEXT NOT NULL UNIQUE," +
                    "PRINCIPLE REAL NOT NULL," +
                    "INTEREST REAL NOT NULL, "+
                    "BALANCE REAL NOT NULL, "+
                    "CURRENCY TEXT NOT NULL," +
                    "COLLATERAL TEXT NOT NULL," +
                    "PRIMARY KEY(ACCOUNTID AUTOINCREMENT)" +
                    ")";

            stmt.execute(sql);



            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conn.close();
        }catch(Exception e){

        }
    }

    //deleting entire tables. have it set to delete all the tables because one can only initialize all the tables
    public void dropAllTables() {
        Statement stmt = null;
        Connection conn=connect();

        try {

            stmt = conn.createStatement();
            String sql = "DROP TABLE USERS";
            stmt.execute(sql);
            sql = "DROP TABLE ACCOUNTS";
            stmt.execute(sql);
            sql = "DROP TABLE TRANSACTIONS";
            stmt.execute(sql);
            sql = "DROP TABLE LOANS";
            stmt.execute(sql);
            sql = "DROP TABLE STOCKS";
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conn.close();
        }catch(Exception e){

        }
    }


    public boolean addUserAccount(String name, String user, String pin, String role) {
        String sql = "INSERT INTO USERS (CUSTOMERID, NAME ,PIN,ROLE) VALUES (?,?,?,?)";
        Connection conn=connect();

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user);
            stmt.setString(2, name);
            stmt.setString(3, pin);
            stmt.setString(4, role);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        try {
            conn.close();
        }catch(Exception e){

        }
        return true;
    }

    public boolean isUniqueCustomerID(String username) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE CUSTOMERID = ?";
        boolean res = false;
        Connection conn=connect();

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            int count = rs.getInt(1);
            res = count < 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        try {
            conn.close();
        }catch(Exception e){

        }
        return res;
    }

    public static void addSpecificAccountType(Customer c, AccountType type, Double balance, String currency) {
        String sql = "INSERT INTO ACCOUNTS (CUSTOMERID, NAME, TYPE, CURRENCY, BALANCE) VALUES (?,?,?,?,?)";
        Account account = null;
        System.out.println("creating the specific account of "+type.toString());
        Connection conn=connect();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, c.getCustomerId());

            stmt.setString(2, c.getCustomerId());

            stmt.setString(3, type.toString());

            stmt.setString(4, currency);

            stmt.setDouble(5, balance);

            stmt.execute();
            System.out.println("Finishing creating the specific account of "+type.toString());

/*
            sql = "SELECT ACCOUNTID FROM ACCOUNTS WHERE CUSTOMERID = ? AND TYPE = ? AND CURRENCY = ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, c.getCustomerId());
            stmt.setString(2, type.toString());
            stmt.setString(3, currency);

            ResultSet rs = stmt.executeQuery();

            switch (type.toString()) {
                case "CHECKING":
                    account =account = new Account(
                            rs.getString(0),
                            rs.getString(1),
                            rs.getString(2),
                            AccountType.CHECKING,
                            rs.getDouble(5),
                            rs.getDouble(6)
                    );
                    break;
                case "SAVINGS":
                    account =account = new Account(
                            rs.getString(0),
                            rs.getString(1),
                            rs.getString(2),
                            AccountType.SAVINGS,
                            rs.getDouble(5),
                            rs.getDouble(6)
                    );                    break;
                case "SECURITIES":
                    account =account = new Account(
                            rs.getString(0),
                            rs.getString(1),
                            rs.getString(2),
                            AccountType.SECURITIES,
                            rs.getDouble(5),
                            rs.getDouble(6)
                    );
                    break;
            }
*/

            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conn.close();
        }catch(Exception e){

        }
    }

        public  Customer constructCustomerFromUsers(String ID){
            Customer customer=null;//as an intermediate holder
            Connection conn=connect();
            try {

                System.out.println("before customer from user is created");
                String sql = "SELECT CUSTOMERID,NAME,PIN FROM USERS WHERE CUSTOMERID = ?";
                PreparedStatement stmt2 = conn.prepareStatement(sql);
                stmt2.setString(1, ID);
                ResultSet rs = stmt2.executeQuery();

                System.out.println("after customer from user is created");

                String cuid = rs.getString("CUSTOMERID");
                String cname = rs.getString("NAME");
                String ccode = rs.getString("PIN");
                System.out.println(cuid +" "+cname+" "+ccode);
                customer=new Customer(cuid,cname,ccode);


            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
            try {
                conn.close();
            }catch(Exception e){

            }
            return customer;

        }


        public void constructCustomerAccounts(Customer c){

        Connection conn=connect();

        try {

            System.out.println("before customer from user is created");
            String sql = "SELECT ACCOUNTID,CUSTOMERID,NAME,TYPE,CURRENCY,BALANCE,FEE FROM ACCOUNTS WHERE CUSTOMERID = ?";
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, c.getCustomerId());
            ResultSet rs2 = stmt2.executeQuery();
            System.out.println("after customer from user is created");
            while (rs2.next()) {
                String accountId = rs2.getString(1);
                String customerId = rs2.getString(2);
                String name = rs2.getString(3);
                AccountType type = AccountType.valueOf(rs2.getString(4));
                CurrencyType currencyType = CurrencyType.valueOf(rs2.getString(5));
                double balance = rs2.getDouble(6);
                double fee = rs2.getDouble(7);

                Account fetcher=new Account(accountId,customerId,name, type,balance,fee);

                String accType = rs2.getString(4);
                switch (accType) {
                    case "CHECKING":
                        c.setChecking(fetcher);
                        break;
                    case "SAVINGS":
                        c.setSavings(fetcher);
                        break;
                    case "SECURITIES":
                        c.setSecurities(fetcher);
                        break;
                    case "LOANS":
                        c.setLoans(fetcher);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        try {
            conn.close();
        }catch(Exception e){

        }
    }

    public static Account getSpecificAccount(String customerID, String AType){
        Account account = null;
        Connection conn = connect();

        try {
            String sql = "SELECT CUSTOMERID, TYPE, CURRENCY ,BALANCE FROM ACCOUNTS WHERE CUSTOMERID = ? AND TYPE = ?";
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, customerID);
            stmt2.setString(2, AType);
            ResultSet rs = stmt2.executeQuery();

            String accType = rs.getString(3);
            switch (accType) {
                case "CHECKING":
                    account = new Account(
                            rs.getString(0),
                            rs.getString(1),
                            rs.getString(2),
                            AccountType.CHECKING,
                            rs.getDouble(5),
                            rs.getDouble(6)
                    );
                    break;
                case "SAVINGS":
                    account = new Account(
                            rs.getString(0),
                            rs.getString(1),
                            rs.getString(2),
                            AccountType.SAVINGS,
                            rs.getDouble(5),
                            rs.getDouble(6)
                    );
                case "SECURITIES":
                    account = new Account(
                            rs.getString(0),
                            rs.getString(1),
                            rs.getString(2),
                            AccountType.SECURITIES,
                            rs.getDouble(5),
                            rs.getDouble(6)
                    );
                    break;
                case "LOANS":
                    account = new Account(
                            rs.getString(0),
                            rs.getString(1),
                            rs.getString(2),
                            AccountType.LOANS,
                            rs.getDouble(5),
                            rs.getDouble(6)
                    );
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        try {
            conn.close();
        }catch(Exception e){

        }
        return account;
    }

    public Account getAccount(String id) {
        Account account = null;
        Connection conn=connect();

        try {
            System.out.println("a");

            String sql = "SELECT CUSTOMERID, TYPE, CURRENCY ,BALANCE FROM ACCOUNTS WHERE CUSTOMERID = ?";
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, id);
            ResultSet rs2 = stmt2.executeQuery();

            String accType = rs2.getString(3);
            switch (accType) {
                case "CHECKING":
                    account = new Account(
                            rs2.getString(0),
                            rs2.getString(1),
                            rs2.getString(2),
                            AccountType.CHECKING,
                            rs2.getDouble(5),
                            rs2.getDouble(6)
                            );
                    break;
                case "SAVINGS":
                    account = new Account(
                            rs2.getString(0),
                            rs2.getString(1),
                            rs2.getString(2),
                            AccountType.SAVINGS,
                            rs2.getDouble(5),
                            rs2.getDouble(6)
                    );
                case "SECURITIES":
                    account = new Account(
                            rs2.getString(0),
                            rs2.getString(1),
                            rs2.getString(2),
                            AccountType.SECURITIES,
                            rs2.getDouble(5),
                            rs2.getDouble(6)
                    );
                    break;
                case "LOANS":
                    account = new Account(
                            rs2.getString(0),
                            rs2.getString(1),
                            rs2.getString(2),
                            AccountType.LOANS,
                            rs2.getDouble(5),
                            rs2.getDouble(6)
                    );
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        System.out.println("b");
        try {
            conn.close();
        }catch(Exception e){

        }
        return account;
    }

    public boolean isValidUserName(String username) {
        String sql = "SELECT ACCOUNTID, CUSTOMERID FROM USERS WHERE CUSTOMERID = ?";
        Connection conn=connect();

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            int id = rs.getInt(1);
            if (id == 0) {
                try {
                    conn.close();
                }catch(Exception e){

                }
                return false;
            }
            try {
                conn.close();
            }catch(Exception e){

            }
           return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            try {
                conn.close();
            }catch(Exception g){

            }
            return false;
        }

    }
    public boolean isValidUser(String user, String pin) {
        String sql = "SELECT ACCOUNTID, CUSTOMERID FROM USERS WHERE CUSTOMERID = ? AND PIN = ?";
        Connection conn=connect();

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user);
            stmt.setString(2, pin);
            ResultSet rs = stmt.executeQuery();
            int id = rs.getInt(1);
            if (id == 0) {
                try {
                    conn.close();
                }catch(Exception e){

                }
                return false;
            }
            try {
                conn.close();
            }catch(Exception e){

            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            try {
                conn.close();
            }catch(Exception g){

            }
            return false;
        }
    }

    /*
    TODO: add specific account creation logic (i.e the rearange some code I have)
     */

    public boolean update(int accountId, Double amount) {
        String sql = "UPDATE ACCOUNTS SET AMOUNT = ? WHERE CUSTOMERID = ?";
        Connection conn=connect();

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        try {
            conn.close();
        }catch(Exception e){

        }
        return true;
    }
/*
deletes an entry from a table in a specific database
 */
        public void delete ( int id){
            String sql = "DELETE FROM ACCOUNTS WHERE CUSTOMERID = ?";
            Connection conn=connect();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // set the corresponding param
                pstmt.setInt(1, id);
                // execute the delete statement
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try {
                conn.close();
            }catch(Exception e){

            }
        }

        public boolean Transfer ( int fromId, int toId, BigDecimal fromAmount, BigDecimal toAmount){

            String sql = "UPDATE ACCOUNTS SET AMOUNT = ? " +
                    "WHERE CUSTOMERID = ?";
            Connection conn=connect();

            try {

                conn.setAutoCommit(false);
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setBigDecimal(1, fromAmount);
                stmt.setInt(2, fromId);
                stmt.executeUpdate();
                stmt = conn.prepareStatement(sql);
                stmt.setBigDecimal(1, toAmount);
                stmt.setInt(2, toId);
                stmt.executeUpdate();
                conn.commit();
                conn.setAutoCommit(true);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                conn.close();
            }catch(Exception e){

            }
            return true;
        }

    public static void updateAccountAmount(String accountId, Double amount) {
        String sql = "UPDATE ACCOUNTS SET BALANCE = ? WHERE ACCOUNTID = ?";
        Connection conn=connect();

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setString(2, accountId);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conn.close();
        }catch(Exception e){

        }

    }
}
