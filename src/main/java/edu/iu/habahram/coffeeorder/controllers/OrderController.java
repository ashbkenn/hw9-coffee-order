package edu.iu.habahram.coffeeorder.controllers;

import edu.iu.habahram.coffeeorder.model.OrderData;
import edu.iu.habahram.coffeeorder.model.Receipt;
import edu.iu.habahram.coffeeorder.repository.OrderFileRepository;
import edu.iu.habahram.coffeeorder.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderFileRepository orderFileRepository;

    public OrderController(OrderFileRepository orderFileRepository) {
        this.orderFileRepository = orderFileRepository;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody OrderData order) {
        try {
            Receipt receipt = orderFileRepository.add(order);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(receipt);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}