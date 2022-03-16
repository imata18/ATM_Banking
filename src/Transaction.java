import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Comparable<Transaction> {
    private final String customerId;
    private final String accountId;
    private final double amount;
    private final String message;
    private final String date;

    public Transaction(String customerId, String accountId, double amount, String message, String date) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.amount = amount;
        this.message = message;
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getAmount() {
    	return amount;
    }
    
    public String getMessage() {
    	return message;
    }
    
    public String getDate() {
    	return date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "customerId='" + customerId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", amount=" + amount +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public int compareTo(Transaction o) {
        DateTimeFormatter formatter = Util.formatter;
        return LocalDateTime.parse(date, formatter).compareTo(LocalDateTime.parse(o.getDate(), formatter));
    }
}