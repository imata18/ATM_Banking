import java.util.*;

public class BankManager {
    private final Map<String, Customer> customers = new HashMap<>();
    private databaseManager dbm= new databaseManager();


    public BankManager() {
      //dbm.createNewDatabase();
        dbm.InitializeAllTables();
        createAccount("admin", "admin", "0611");
    }

    public Customer createAccount(String customerId, String name, String pin) {
        Customer customer = new Customer(customerId, name, pin);
        customers.put(customer.getCustomerId(), customer);

        // TODO: push to database? Solved?
        //Solution: add a user account to the table
        if(customerId.equals("admin")&&dbm.isUniqueCustomerID(customerId)){
            System.out.println("c");

            dbm.addUserAccount("admin", "admin", "0611", "MANAGER");
            return customer;
        }else if(dbm.isUniqueCustomerID(customerId)){
            System.out.println("d");
            dbm.addUserAccount(customer.getName(), customer.getCustomerId(), customer.getPin(), "CUSTOMER");
            return customer;

        }
        System.out.println("this is not a unique name. please choose another");
        return customer;
    }


    //todo: clean up logic. Sorry was somewhat confused on how you intended to fetch
    public Customer login(String customerId, String pin) {
        Customer customer = customers.get(customerId);
        // TODO: pull from database. Solved?


        //first check in local memory to see if the customer exists. if not then pull from the Database
        if (customer == null) {
            if(dbm.isValidUser(customerId, pin)){
                System.out.println("a");
                Customer c= dbm.constructCustomerFromUsers(customerId);
                dbm.constructCustomerAccounts(c);
                return c;
            }else{
                System.out.println("either invalid login or pin");
            }

            System.out.println("Missing customer ID");
        }

        if (customer != null) {
            if (customer.getPin().equals(pin)) {
                System.out.println("b");

                return customer;
            } else {
                System.out.println("Incorrect pin");
            }
        }


        System.out.println("Invalid customer ID");

        return customer;
    }

    public boolean contains(String customerId, String pin) {
    	Customer customer = customers.get(customerId);

        //if customer is not in array see if it exists in the USERS table and then call constructCustomer
        if (customer == null) {
            // TODO: pull from database. Solved?
            if(dbm.isValidUserName(customerId)){
                if(dbm.isValidUser(customerId, pin)){
                    return true;
                }else{
                    System.out.println("invalid pin");
                }
            }
            return false;
        }

        if (customer != null) {
            if (customer.getPin().equals(pin)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public Customer getCustomer(String customerId) {
        Customer customer = customers.get(customerId);
        return customer;
    }

    public List<Transaction> printReport() {
        List<Transaction> transactions = new ArrayList<>();
        for (Customer customer : customers.values()) {
            transactions.addAll(customer.getAllTransactions());
        }
        Collections.sort(transactions);
        return transactions;
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer.getCustomerId());
    }

    public Map getCustomerList() {
        return customers;
    }
}
