package learn.battle.fighters;

public class Fighter {

    private final String name;
    private int balance = 100;
    private int power;

    public Fighter(String name, int power) {
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public int getPower() { return power/50; }

    public boolean isOut() {
        return balance <= 0;
    }

    public void reduceBalance(int amount) {
        balance -= amount;
    }

    public void restoreBalance(int amount) {
        if (balance + amount > 100) {
            balance = 100;
        } else {
            balance += amount;
        }
    }
}
