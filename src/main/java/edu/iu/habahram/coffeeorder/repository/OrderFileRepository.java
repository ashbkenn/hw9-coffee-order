package edu.iu.habahram.coffeeorder.repository;

import edu.iu.habahram.coffeeorder.model.*;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class OrderFileRepository {
    private static final AtomicInteger idGen = new AtomicInteger(1);
    private static final String DATABASE_NAME = "orders/db.txt";
    private static final String NEW_LINE = System.lineSeparator();

    private static void appendToFile(Path path, String content)
            throws IOException {
        Files.write(path,
                content.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    public Receipt add(OrderData order) throws Exception {
        Beverage beverage = null;
        switch (order.getBeverage().toLowerCase()) {
            case "dark roast":
                beverage = new DarkRoast();
                break;
            case "house blend":
                beverage = new HouseBlend();
                break;
            case "espresso":
                beverage = new Espresso();
                break;
            case "decaf":
                beverage = new Decaf();
                break;
            default:
                throw new Exception("Beverage type '%s' is not valid!".formatted(order.getBeverage()));
        }

        List<String> condiments = order.getCondiments();
        if (condiments != null) {
            for (String condiment : condiments) {
                switch (condiment.toLowerCase()) {
                    case "milk":
                        beverage = new Milk(beverage);
                        break;
                    case "mocha":
                        beverage = new Mocha(beverage);
                        break;
                    case "soy":
                        beverage = new Soy(beverage);
                        break;
                    case "whip":
                        beverage = new Whip(beverage);
                        break;
                    default:
                        throw new Exception("Condiment type '%s' is not valid".formatted(condiment));
                }
            }
        }

        int rId = idGen.getAndIncrement();
        float rCost = beverage.cost();
        String rDescription = beverage.getDescription();

        String orderLine = String.format("%d, %.2f, %s", rId, rCost, rDescription);
        Path path = Path.of(DATABASE_NAME);
        appendToFile(path, orderLine + NEW_LINE);

        return new Receipt(rId, rCost, rDescription);
    }
}