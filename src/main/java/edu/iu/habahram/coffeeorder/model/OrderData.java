package edu.iu.habahram.coffeeorder.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(schema = "coffee", name = "order")
public class OrderData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String beverage;
    private  List<String> condiments;

    public OrderData() {

    }

    public OrderData(String beverage, List<String> condiments) {
        this.beverage = beverage;
        this.condiments = condiments;
    }

    public String getBeverage() {
        return beverage;
    }

    public List<String> getCondiments() {
        return condiments;
    }
}