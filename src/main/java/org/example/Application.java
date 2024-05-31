package org.example;

import org.example.entities.Customer;
import org.example.entities.Order;
import org.example.entities.Product;

import java.util.*;
import java.util.stream.Collectors;

public class Application {

    static List<Product> warehouse = new ArrayList<>();
    static List<Customer> customersList = new ArrayList<>();
    static List<Order> ordersList = new ArrayList<>();

    public static void main(String[] args) {
        initializeWarehouse();
        createCustomers();
        placeOrders();

        System.out.println("--------------------------------- PRODUCTS ----------------------------------");
        warehouse.forEach(System.out::println);
        System.out.println("\n--------------------------------- CUSTOMERS ----------------------------------");
        customersList.forEach(System.out::println);
        System.out.println("\n--------------------------------- ORDERS ----------------------------------");
        ordersList.forEach(System.out::println);


        System.out.println("\n-------------------------- ORDINI PER OGNI CLIENTE --------------------------");
        Map<Customer, List<Order>> ordersByCustomer = ordersList.stream()
                .collect(Collectors.groupingBy(order -> order.getCustomer()));

        ordersByCustomer.forEach((customer, ordersList) -> {
            System.out.println("Customer: " + customer.getName() + "  Orderine: " + ordersList);
        });


        System.out.println("\n-------------------------- TOTALE VENDITE PER OGNI CLIENTE --------------------------");
        Map<Customer, Double> totaleVenditePerCliente = ordersList.stream()
                .collect(Collectors.groupingBy(order -> order.getCustomer(), Collectors.summingDouble(order -> order.getProducts().stream()
                        .mapToDouble(Product::getPrice)
                        .sum())));


        totaleVenditePerCliente.forEach((customer, venditeTot) -> {
            System.out.println("Customer: " + customer.getName() + ", Totale vendite: " + venditeTot);
        });

        System.out.println("\n-------------------------- PRODOTTO PIU COSTOSO --------------------------");
        Product prodottoPiuCostoso = warehouse.stream()
                .max(Comparator.comparing(Product::getPrice))
                .orElse(null);
        System.out.println("Il prodotto più costoso è: " + prodottoPiuCostoso);

        System.out.println("\n-------------------------- MEDIA DEGLI IMPORTI DEGLI ORDINI --------------------------");

        Map<Customer, Double> mediaOrdiniPerOrdine = ordersList.stream()
                .collect(Collectors.groupingBy(order -> order.getCustomer(), Collectors.averagingDouble(order -> order.getProducts().stream()
                        .mapToDouble(Product::getPrice)
                        .average().getAsDouble())));
        mediaOrdiniPerOrdine.forEach((ordersList, media) -> {
            System.out.println("Ordine " + ordersList + " con media di spesa: " + media);
        });
    }


    public static void initializeWarehouse() {
        Product iPhone = new Product("IPhone", "Smartphones", 2000.0);
        Product lotrBook = new Product("LOTR", "Books", 101);
        Product itBook = new Product("IT", "Books", 2);
        Product davinciBook = new Product("Da Vinci's Code", "Books", 2);
        Product diapers = new Product("Pampers", "Baby", 3);
        Product toyCar = new Product("Car", "Boys", 15);
        Product toyPlane = new Product("Plane", "Boys", 25);
        Product lego = new Product("Lego Star Wars", "Boys", 500);

        warehouse.addAll(Arrays.asList(iPhone, lotrBook, itBook, davinciBook, diapers, toyCar, toyPlane, lego));
    }

    public static void createCustomers() {
        Customer aldo = new Customer("Aldo Baglio", 1);
        Customer giovanni = new Customer("Giovanni Storti", 2);
        Customer giacomo = new Customer("Giacomo Poretti", 3);
        Customer marina = new Customer("Marina Massironi", 2);

        customersList.add(aldo);
        customersList.add(giovanni);
        customersList.add(giacomo);
        customersList.add(marina);
    }

    public static void placeOrders() {
        Order aldoOrder = new Order(customersList.get(0));
        Order giovanniOrder = new Order(customersList.get(1));
        Order giacomoOrder = new Order(customersList.get(2));
        Order marinaOrder = new Order(customersList.get(3));
        Order giacomoOrder2 = new Order(customersList.get(2));

        Product iPhone = warehouse.get(0);
        Product lotrBook = warehouse.get(1);
        Product itBook = warehouse.get(2);
        Product davinciBook = warehouse.get(3);
        Product diaper = warehouse.get(4);

        aldoOrder.addProduct(iPhone);
        aldoOrder.addProduct(lotrBook);
        aldoOrder.addProduct(diaper);

        giovanniOrder.addProduct(itBook);
        giovanniOrder.addProduct(davinciBook);
        giovanniOrder.addProduct(iPhone);

        giacomoOrder.addProduct(lotrBook);
        giacomoOrder.addProduct(diaper);

        marinaOrder.addProduct(diaper);

        giacomoOrder2.addProduct(iPhone);

        ordersList.add(aldoOrder);
        ordersList.add(giovanniOrder);
        ordersList.add(giacomoOrder);
        ordersList.add(marinaOrder);
        ordersList.add(giacomoOrder2);

    }
}