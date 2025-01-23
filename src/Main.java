import java.util.*;

class Stock {
    private String name;
    private int price;

    public Stock(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " - Cena za 1%: " + price;
    }
}

public class Main {
    private static int balance = 0;
    private static List<String> myStocks = new ArrayList<>();
    private static Map<Integer, Stock> stockMarket = new LinkedHashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeStocks();
        boolean running = true;

        while (running) {
            showMainMenu();
            int choice = getUserInput();
            switch (choice) {
                case 1 -> rechargeBalance();
                case 2 -> showStocksMenu();
                case 3 -> skipDay();
                case 4 -> showMyStocks();
                case 5 -> {
                    System.out.println("Wyjście z programu. Do zobaczenia!");
                    running = false;
                }
                default -> System.out.println("Niepoprawny wybór. Spróbuj ponownie.");
            }
        }
    }

    private static void initializeStocks() {
        stockMarket.put(1, new Stock("Apple", 150));
        stockMarket.put(2, new Stock("Tesla", 200));
        stockMarket.put(3, new Stock("Google", 180));
        stockMarket.put(4, new Stock("Amazon", 170));
        stockMarket.put(5, new Stock("Microsoft", 160));
    }

    private static void showMainMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Doładować saldo");
        System.out.println("2. Zobaczyć dostępne akcje");
        System.out.println("3. Przeskoczyć dzień");
        System.out.println("4. Moje akcje");
        System.out.println("5. Wyjście");
        System.out.print("Wybierz opcję (1-5): ");
    }

    private static int getUserInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Niepoprawny wybór. Wprowadź liczbę: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void rechargeBalance() {
        System.out.println("\nNa twoim koncie: " + balance + "$.");
        System.out.println("1. Doładować o 500");
        System.out.println("2. Doładować o 100");
        System.out.println("3. Wprowadzić własną kwotę");
        System.out.println("4. Wróć");
        System.out.print("Wybierz opcję (1-4): ");

        int choice = getUserInput();
        switch (choice) {
            case 1 -> balance += 500;
            case 2 -> balance += 100;
            case 3 -> {
                System.out.print("Wprowadź kwotę do doładowania: ");
                int customAmount = getUserInput();
                if (customAmount > 0) {
                    balance += customAmount;
                } else {
                    System.out.println("Kwota musi być większa niż zero.");
                }
            }
            case 4 -> System.out.println("Powrót do menu głównego.");
            default -> System.out.println("Niepoprawny wybór.");
        }
        System.out.println("Aktualne saldo: " + balance);
    }

    private static void showStocksMenu() {
        System.out.println("\nDostępne akcje:");
        stockMarket.forEach((key, stock) -> System.out.println(key + ". " + stock));

        System.out.println("\nDziałania:");
        System.out.println("1. Kup akcje");
        System.out.println("2. Wróć");
        System.out.print("Wybierz opcję (1-2): ");

        int choice = getUserInput();
        switch (choice) {
            case 1 -> buyStock();
            case 2 -> System.out.println("Powrót do menu głównego.");
            default -> System.out.println("Niepoprawny wybór.");
        }
    }

    private static void buyStock() {
        System.out.print("Wprowadź numer akcji, którą chcesz kupić: ");
        int stockNumber = getUserInput();

        if (stockMarket.containsKey(stockNumber)) {
            Stock selectedStock = stockMarket.get(stockNumber);
            System.out.println("Wybrałeś: " + selectedStock);
            System.out.print("Ile procent chcesz kupić? ");
            int percent = getUserInput();
            int cost = selectedStock.getPrice() * percent;

            if (percent > 0 && balance >= cost) {
                balance -= cost;
                myStocks.add(percent + "% akcji " + selectedStock.getName());
                System.out.println("Pomyślnie kupiłeś " + percent + "% akcji " + selectedStock.getName() + ". Wydano: " + cost + ". Aktualne saldo: " + balance);
            } else {
                System.out.println("Niewystarczająca ilość środków lub niepoprawny procent.");
            }
        } else {
            System.out.println("Niepoprawny numer akcji.");
        }
    }

    private static void skipDay() {
        System.out.println("Przeskoczenie dnia: aktualizacja cen akcji.");
        Random random = new Random();
        stockMarket.forEach((key, stock) -> {
            int change = random.nextInt(20) + 1;
            if (random.nextBoolean()) {
                stock.setPrice(stock.getPrice() + change);
            } else {
                stock.setPrice(Math.max(1, stock.getPrice() - change));
            }
        });
        System.out.println("Ceny zostały zaktualizowane!");
    }

    private static void showMyStocks() {
        System.out.println("Moje akcje:");
        if (myStocks.isEmpty()) {
            System.out.println("Nie masz żadnych zakupionych akcji.");
        } else {
            myStocks.forEach(stock -> System.out.println("- " + stock));
        }
    }
}