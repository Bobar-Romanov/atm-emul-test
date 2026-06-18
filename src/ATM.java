import java.util.Map;

public class ATM {
    private final BanknoteStorage storage;

    public ATM(BanknoteStorage storage) {
        this.storage = storage;
    }

    public void accept(Map<Denomination, Integer> banknotes) {
        storage.add(banknotes);
    }

    public Map<Denomination, Integer> withdraw(int amount) {
        return storage.withdraw(amount);
    }

    public int getBalance() {
        return storage.total();
    }
}
