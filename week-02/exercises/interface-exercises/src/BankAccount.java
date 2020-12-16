public class BankAccount implements MoneyStorage {

    private double balance;
    private String description;

    public BankAccount(double balance, String description) {
        this.balance = balance;
        this.description = description;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean deposit(double amount) {
        if (amount > 0.0) {
            balance += amount;
            return true;
        }
        return false;
    }

    @Override
    public double withdraw(double amount) {
        if (amount <= 0) {
            return 0.0;
        }
        double result = Math.min(amount, balance + 25.0);
        balance -= result;
        return result;
    }
}
