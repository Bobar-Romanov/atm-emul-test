import java.util.*;

public class InMemoryBanknoteStorage implements BanknoteStorage {

    private final Map<Denomination, Integer> cells = new EnumMap<>(Denomination.class);

    public InMemoryBanknoteStorage() {
    }

    @Override
    public void add(Map<Denomination, Integer> banknotes) {
        for (var entry : banknotes.entrySet())
            cells.merge(entry.getKey(), entry.getValue(), Integer::sum);
    }

    @Override
    public Map<Denomination, Integer> withdraw(int amount) {
        Map<Denomination, Integer> result = new LinkedHashMap<>();
        List<Denomination> sorted = EnumSet.allOf(Denomination.class).stream()
                .sorted((d1, d2) -> Integer.compare(d2.value, d1.value))
                .toList();
        for (Denomination denom : sorted) {
            int available = cells.get(denom);
            int need = amount / denom.value;
            int use = Math.min(available, need);
            if (use > 0) {
                result.put(denom, use);
                amount -= use * denom.value;
            }
        }
        if (amount != 0) {
            throw new IllegalStateException("Cannot withdraw requested amount");
        }
        for (var e : result.entrySet()) {
            cells.put(e.getKey(), cells.get(e.getKey()) - e.getValue());
        }
        return result;
    }

    @Override
    public int total() {
        return cells.entrySet().stream()
                .mapToInt(e -> e.getKey().value * e.getValue())
                .sum();
    }
}
