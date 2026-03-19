package rc.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Customer {
    private String customerId;
    private String customerName;
    private String email;
    private String phone;
    private String customerType;
    private LocalDate registrationDate;

    // Constructors
    public Customer() {
    }

    public Customer(String customerId, String customerName, String email, String phone,
                    String customerType, LocalDate registrationDate) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.phone = phone;
        this.customerType = customerType;
        this.registrationDate = registrationDate;
    }

    // Getters & Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    // Nhập dữ liệu
    public void inputData(Scanner scanner, List<Customer> customers) {
        // Nhập ID khách hàng
        while (true) {
            System.out.print("Nhập ID khách hàng: ");
            String id = scanner.nextLine().trim();

            if (!validateID(id)) {
                System.out.println("ID không hợp lệ! ID phải bắt đầu bằng C và 3 số!");
                continue;
            }

            boolean exists = customers.stream()
                    .anyMatch(c -> c.getCustomerId().equals(id));

            if (exists) {
                System.out.println("ID đã tồn tại! Vui lòng nhập lại.");
                continue;
            }

            this.customerId = id;
            break;
        }

        // Nhập tên khách hàng
        while (true) {
            System.out.print("Nhập tên khách hàng: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Tên khách hàng không được để trống!");
            } else {
                this.customerName = name;
                break;
            }
        }

        // Nhập email
        while (true) {
            System.out.print("Nhập email: ");
            String email = scanner.nextLine().trim().toLowerCase();

            if (!validateEmail(email)) {
                System.out.println("Email không hợp lệ!");
                continue;
            }

            boolean exists = customers.stream()
                    .anyMatch(c -> c.getEmail().equalsIgnoreCase(email));

            if (exists) {
                System.out.println("Email đã tồn tại! Vui lòng nhập lại.");
                continue;
            }

            this.email = email;
            break;
        }

        // Nhập số điện thoại
        while (true) {
            System.out.print("Nhập số điện thoại: ");
            String phone = scanner.nextLine().trim();

            if (!validatePhone(phone)) {
                System.out.println("SĐT không hợp lệ!");
                continue;
            }

            boolean exists = customers.stream()
                    .anyMatch(c -> c.getPhone().equals(phone));

            if (exists) {
                System.out.println("SĐT đã tồn tại! Nhập lại.");
                continue;
            }

            this.phone = phone;
            break;
        }

        // Nhập loại khách hàng
        while (true) {
            System.out.print("Nhập loại khách hàng (Cá nhân/Doanh nghiệp/Ưu đãi): ");
            String type = scanner.nextLine().trim();
            if (validType(type)) {
                this.customerType =
                        type.substring(0,1).toUpperCase() +
                                type.substring(1).toLowerCase();
                break;
            } else {
                System.out.println("Loại khách hàng không hợp lệ! Vui lòng nhập: Cá nhân, Doanh nghiệp hoặc Ưu đãi");
            }
        }

        // Ngày đăng ký
        LocalDate date = LocalDate.now();
        this.registrationDate = date;
    }

    // Hiển thị
    public void displayData() {
        System.out.printf("| %-10s | %-20s | %-25s | %-15s | %-15s | %-12s |\n",
                customerId, customerName, email, phone, customerType,
                registrationDate);
    }

    // Validate Dữ liệu
    private boolean validateID(String id) {
        if (id == null || id.isEmpty()) {
            return false;
        }
        return id.matches("^C\\d{3}$");
    }
    public boolean validateEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public boolean validatePhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        return phone.matches("^0\\d{9,10}$");
    }

    public boolean validType(String type) {
        return type.equalsIgnoreCase("Cá nhân") ||
                type.equalsIgnoreCase("Doanh nghiệp") ||
                type.equalsIgnoreCase("Ưu đãi");
    }
}