package com.ecommerce.eshop.order.controllers;

import com.ecommerce.eshop.order.exceptions.OrderNotFoundException;
import com.ecommerce.eshop.order.models.CustomerOrder;
import com.ecommerce.eshop.order.models.OrderStatus;
import com.ecommerce.eshop.order.service.OrderService;
import com.ecommerce.eshop.utils.ControllersUtils.DateRange;
import com.ecommerce.eshop.utils.excepctions.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerOrder> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerOrder getById(@PathVariable Long id) {
        return orderService.getByID(id);
    }

    @GetMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerOrder> getAllContainsProductById(@RequestParam Long id) {
        return orderService.getAllThatIncludesProduct(id);
    }

    @GetMapping("/newest")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerOrder> getAllNewest() {
        return orderService.getAllByNewest();
    }

    //TODO: validacja w razie niepoprawnego formatu daty
    @GetMapping("/date")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerOrder> getAllBetweenDates(@RequestBody DateRange dateRange) {
        return orderService.getAllBetweenDates(dateRange);
    }

    @GetMapping("/byAmount")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerOrder> getAllByAmountDescending() {
        return orderService.getAllByTotalAmountDesc();
    }

    @GetMapping("/byQuantity")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerOrder> getAllByQuantityDescending() {
        return orderService.getAllByTotalQuantityDesc();
    }

    @GetMapping("/status/{inputStatus}")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerOrder> getAllByOrderStatus(@PathVariable String inputStatus) {
        String statusString = inputStatus.toUpperCase();
        return orderService.getAllByStatus(statusString);
    }



}
