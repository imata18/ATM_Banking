# ATM_Banking

## Student Info
* Names:
    * Cyril Caparanga
    * Patrick Carpanedo 
    * Ivan Mata

* Emails:
    * cyrilc@bu.edu
    * pfcarp21@bu.edu
    * imata@bu.edu
* BU IDs:
    * Cyril - U32526192
    * Patrick - 
    * Ivan - U56073315

## Dependencies
* org.xerial:sqlite-jdbc:3.36.0.3

## Compilation and Execution Instruction
Developed in Windows environment using IntelliJ IDE.

1. Navigate to the ``cs611-banking`` directory
2. Enter the following commands:
```
> javac -cp "lib/*" src/*.java
> java -cp "lib/*;src/" Main
```

## Classes
* Account: abstract class for generic account information
* AccountChecking: checking account extended from account
* AccountFactory: factory for generating accounts
* AccountInterest: interface for accounts that generate interest
* AccountLoans: loans account extended from account
* AccountSavings: savings account extended from account
* AccountSecurities: securities account extended from account
* AccountType: enum for the account types
* Bank: class representing bank, contains bank manager
* BankManager: bank manager for handling customers
* CurrencyType: enum for currencies and conversion rates
* Customer: class for customer with pin and accounts
* Loan: loan object for loan accounts
* Main: entry point for application
* Role: enum for user roles
* Security: security object for securities account, contains stock info
* StockController: class for obtaining stock info
* Transaction: object containing transaction information
* Util: class with utility methods

* AccountsPage: Displays overview of all the bank accounts of the user
* AddNewAccountPage: Create a new account (checking, savings, etc.) on this page
* BankManagerHomePage: The bank manager's (admin's) home page where he can select a specific user to look up information on
* CheckingTransactionPage: Displays all transactions of the checking account
* DepositPage: Deposit money into a bank account on this page
* LoginPage: Login page for all users
* NewUserPage: Create a new user on this page
* SavingsTransactionPage: Displays all transactions of the savings account
* SettingsPage: Logout or delete the user's account/information from database
* StartUpPage: Inital start up page that instantiates the JFrame
* TransferPage: Transfer money to other user's on this page
* UserHomePage: A regular user's homepage
* WithdrawPage: Withdraw money from a bank account on this page

* databaseManager: Handles every operation relating to the database in order to have persistent data 
