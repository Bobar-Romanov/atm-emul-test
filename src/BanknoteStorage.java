import java.util.Map;

public interface BanknoteStorage {
    void add(Map<Denomination, Integer> banknotes);

    Map<Denomination, Integer> withdraw(int amount);

    int total();
}
