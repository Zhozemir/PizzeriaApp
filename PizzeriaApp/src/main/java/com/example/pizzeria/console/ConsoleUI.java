package com.example.PizzeriaApp.console;

import com.example.PizzeriaApp.console.validations.*;
import com.example.PizzeriaApp.controllers.requests.OrderCreateRequest;
import com.example.PizzeriaApp.controllers.requests.ProductCreateRequest;
import com.example.PizzeriaApp.controllers.requests.UserLoginRequest;
import com.example.PizzeriaApp.controllers.requests.UserRegisterRequest;
import com.example.PizzeriaApp.dto.*;
import com.example.PizzeriaApp.enumerators.UserRole;
import com.example.PizzeriaApp.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner = new Scanner(System.in);

    public void start() throws InterruptedException, JsonProcessingException {

        Thread.sleep(2000);
        // изчакване за сървъра
        while (true) {

            if (!ConsoleSession.isLoggedIn())
                loginMenu();
            else if (ConsoleSession.getRole() == UserRole.CUSTOMER)
                customerMenu();
            else employeeMenu();

        }
    }

    private void loginMenu() {

        System.out.println("\n--- Добре дошли в Пицарията ---");

        System.out.println("1. Вход\n2. Регистрация\n3. Изход");

        switch (ChoiceValidation.readChoice("Изберете опция: ")) {

            case 1 -> login();
            case 2 -> register();
            case 3 -> System.exit(0);
            default -> System.out.println("Невалиден избор.");

        }
    }

    private void login() {

        System.out.print("Потребителско име: ");
        String username = scanner.nextLine();
        String password = PasswordValidation.readPassword("Парола: ");
        //String response = ConsoleService.post("/login?username=" + username + "&password=" + password);

        UserLoginRequest userLoginRequest = new UserLoginRequest(username, password);
        String response = ConsoleService.postJson("/auth/login", userLoginRequest);

        try {
            UserDTO userDTO = new ObjectMapper().readValue(response, UserDTO.class);
            ConsoleSession.login(new User(userDTO.getId(), userDTO.getUsername(), userDTO.getRole(), userDTO.getName(), userDTO.getPhone()));
        } catch (JsonProcessingException e) {
            System.out.println("Грешно потребителско име или парола.");
        }

    }

    private void register() {

        String username;

        while (true) {

            System.out.print("Потребителско име: ");
            username = scanner.nextLine();

            if (ConsoleService.get("/auth/check/username?username=" + username).trim().equals("exists"))
                System.out.println("Потребител с това име вече съществува.");

            else break;
        }

        String password = PasswordValidation.readPassword("Парола: ");
        String name = NameValidation.readName("Име: ");
        String phone = PhoneValidation.readPhone("Телефон: ");
        UserRole role = RoleValidation.readRole("Роля");

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(username, password, role, name, phone);
        System.out.println(ConsoleService.postJson("/auth/register", userRegisterRequest));

    }

    private void customerMenu() {

        System.out.println("\n1. Създай поръчка\n2. Моите поръчки\n3. Повтори поръчка\n4. Изход");

        switch (ChoiceValidation.readChoice("Избор: ")) {

            case 1 -> createOrder();
            case 2 -> viewMyOrders();
            case 3 -> repeatOrder();
            case 4 -> ConsoleSession.logout();
            default-> System.out.println("Невалиден избор.");

        }

    }

    private void employeeMenu() {

        System.out.println("\n1. Добави продукт\n2. Деактивирай продукт\n3. Активни продукти\n4. Обнови статус\n5. Всички поръчки (таблица)\n6. Справка по период\n7. Изход");

        switch (ChoiceValidation.readChoice("Избор: ")) {

            case 1 -> addProduct();
            case 2 -> deactivateProduct();
            case 3 -> viewProducts();
            case 4 -> updateOrderStatus();
            case 5 -> viewOrders();
            case 6 -> reportOrdersByPeriod();
            case 7 -> ConsoleSession.logout();
            default-> System.out.println("Невалиден избор.");

        }
    }

    private void createOrder() {

        // вместо json parser за parse-ване към реални обекти
        RestTemplate restTemplate = new RestTemplate();
        List<ProductDTO> activeProducts =
                List.of(restTemplate.getForObject("http://localhost:8080/api/products", ProductDTO[].class));

        if (activeProducts.isEmpty()) {

            System.out.println("Няма активни продукти.");
            return;

        }

        System.out.println("Активни продукти:");

        activeProducts.forEach(product ->
                System.out.println(product.getId() + " - " + product.getName() + " (" + product.getPrice() + " лв)"));

        List<Long> validIds = activeProducts.stream()
                .map(ProductDTO::getId)
                .toList();

        List<Long> selectedIds = new ArrayList<>();

        while (true) {

            System.out.print("ID-та на продуктите (разделени със запетая): ");
            String input = scanner.nextLine();

            if (input.trim().isEmpty()) {

                System.out.println("Трябва да въведете поне един продукт.");
                continue;

            }

            try {

                selectedIds = Arrays.stream(input.split(","))
                        .map(String::trim)
                        .map(Long::parseLong)
                        .toList();

                if (!validIds.containsAll(selectedIds)) {

                    System.out.println("Въведохте ID-та, които не са сред активните продукти.");
                    continue;

                }

                break;


            } catch (NumberFormatException e) {
                System.out.println("Въведете валидни числови ID-та.");
            }
        }

        //изпращам JSON заявка вместо URL параметри
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(selectedIds);
        System.out.println(ConsoleService.postJson("/orders/create", orderCreateRequest));

    }

    private void addProduct() {

        System.out.print("Име: ");
        String name = scanner.nextLine();
        BigDecimal price = BigDecimal.valueOf(ProductPriceValidation.readProductPrice("Цена: "));

        //изпращам JSON заявка вместо URL параметри
        ProductCreateRequest productDTO = new ProductCreateRequest(name, price);
        System.out.println(ConsoleService.postJson("/products/add", productDTO));

    }

    private void deactivateProduct() {

        if (!viewProducts())
            return;

        Long id = IdValidation.readId("ID за деактивиране: ");

        System.out.println(ConsoleService.put("/products/" + id + "/deactivate"));

    }

    private boolean viewProducts() {

        String response = ConsoleService.get("/products");

        if (response.trim().equals("[]")) {

            System.out.println("Няма активни продукти.");
            return false;

        }

        System.out.println("Активни продукти:\n" + response);

        return true;
    }

    private boolean viewOrders() {

        String response = ConsoleService.get("/orders/print");

        if (response.trim().equals("Няма поръчки")) {

            System.out.println(response);
            return false;

        }

        System.out.println(response);
        return true;

    }

    private boolean viewMyOrders(){

        User currentUser = ConsoleSession.getCurrentUser();
        String response = ConsoleService.get("/orders/my?userId=" + currentUser.getId());

        if(response.trim().equals("Няма поръчки.")){

            System.out.println(response);
            return false;

        }

        System.out.println("Вашите поръчки:\n" + response);
        return true;

    }

    private void repeatOrder() {

        String orders = ConsoleService.get("/orders/delivered/print");

        if (orders.trim().equals("Няма завършени поръчки")) {

            System.out.println(orders);
            return;

        }

        System.out.println(orders);

        Long id = IdValidation.readId("ID за повторение: ");

        System.out.println(ConsoleService.post("/orders/" + id + "/repeat"));
    }

    private void updateOrderStatus() {

        if (!viewOrders())
            return;

        Long id = IdValidation.readId("ID на поръчка: ");

        System.out.print("Нов статус (IN_PROGRESS, DELIVERED, CANCELLED): ");
        String status = scanner.nextLine().toUpperCase();

        System.out.println(ConsoleService.put("/orders/" + id + "/status?status=" + status));

    }

    private void reportOrdersByPeriod() {

//        if(!viewOrders())
//            return;

        String allOrders = ConsoleService.get("/orders/print");

        if(allOrders.equals("Няма поръчки")){

            System.out.println("Няма никакви поръчки.");
            return;

        }

        String start = DateTimeValidation.readDateTime("Въведете начална дата и час (например 2023-03-01T00:00): ");
        String end = DateTimeValidation.readDateTime("Въведете крайна дата и час (например 2023-03-31T23:59): ");

        System.out.println(ConsoleService.get("/orders/period/print?start=" + start + "&end=" + end));
    }
}
