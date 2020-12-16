public class Vault implements MoneyStorage {

    private double balance;
    private String description;

    // Constructor
    public Vault(double balance, String description) {
        this.balance = balance;
        this.description = description;
    }

    @Override
    public double getBalance() { return balance; }

    @Override
    public String getDescription() { return description; }

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
        double result = 0.0;
        if (balance <= 0.0) {
            return 0.0;
        }
        result = Math.min(amount, balance);
        balance -= result;
        return result;
    }
}
