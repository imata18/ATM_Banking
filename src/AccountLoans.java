import java.util.ArrayList;
import java.util.List;

public class AccountLoans extends Account implements AccountInterest {
    private final List<Loan> loans = new ArrayList<>();

    public AccountLoans(String accountId, String customerId, String name, double balance, double fee) {
        super(accountId, customerId, name, AccountType.LOANS, balance, fee);
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void requestLoan(double principal, double interest) {
        loans.add(new Loan(principal, interest));
    }

    public void payLoan(int index, double amount) {
        getStatement().add(new Transaction(getCustomerId(), getAccountId(), amount, "Paid loan", Util.currentDate()));
        loans.get(index).pay(amount);

        // TODO: close loans?
        if (loans.get(index).getBalance() == 0) {
            loans.remove(index);
        }
    }

    @Override
    public void applyInterest(long days) {
        for (Loan loan : loans) {
            loan.applyInterest(days);
        }
    }
}
