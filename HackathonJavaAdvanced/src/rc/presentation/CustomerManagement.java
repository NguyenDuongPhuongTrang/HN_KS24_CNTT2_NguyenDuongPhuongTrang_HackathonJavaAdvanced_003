package rc.presentation;

import rc.business.CustomerBusiness;
import rc.entity.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class CustomerManagement {
    private static final CustomerBusiness customerBusiness = CustomerBusiness.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                displayMenu();
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        displayAllCustomers();
                        break;
                    case 2:
                        addNewCustomer();
                        break;
                    case 3:
                        updateCustomer();
                        break;
                    case 4:
                        deleteCustomer();
                        break;
                    case 5:
                        searchCustomerByName();
                        break;
                    case 6:
                        filterCustomerByType();
                        break;
                    case 7:
                        sortCustomersByName();
                        break;
                    case 8:
                        System.out.println("THOÁT");
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập lại.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
            } catch (Exception e) {
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n=============== QUẢN LÝ KHÁCH HÀNG ===============");
        System.out.println("1. Hiển thị toàn bộ danh sách khách hàng");
        System.out.println("2. Thêm mới khách hàng");
        System.out.println("3. Cập nhật thông tin khách hàng theo mã");
        System.out.println("4. Xóa khách hàng theo mã");
        System.out.println("5. Tìm kiếm khách hàng theo tên");
        System.out.println("6. Lọc khách hàng theo loại");
        System.out.println("7. Sắp xếp khách hàng theo tên tăng dần");
        System.out.println("8. Thoát");
        System.out.print("Lựa chọn của bạn: ");
    }

    private static void displayAllCustomers() {
        System.out.println("\n--- DANH SÁCH KHÁCH HÀNG ---");
        customerBusiness.displayAllCustomers();
    }

    private static void addNewCustomer() {
        System.out.println("\n--- THÊM MỚI KHÁCH HÀNG ---");
        while (true) {
            try {
                Customer customer = new Customer();
                customer.inputData(scanner, customerBusiness.getAllCustomers());

                customerBusiness.addCustomer(customer);
                System.out.println("Thêm khách hàng thành công!");
                String choice;
                do {
                    System.out.print("Bạn có muốn thêm tiếp không? (Y/N): ");
                    choice = scanner.nextLine().trim();
                } while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N"));

                if (choice.equalsIgnoreCase("N")) {
                    break;
                }

            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
    }

    private static void updateCustomer() {
        System.out.println("\n--- CẬP NHẬT THÔNG TIN KHÁCH HÀNG ---");

        System.out.print("Nhập mã khách hàng cần cập nhật: ");
        String customerId = scanner.nextLine().trim().toUpperCase();

        if (!customerBusiness.isCustomerIdExists(customerId)) {
            System.out.println("Không tồn tại khách hàng");
            return;
        }

        try {
            Customer updatedCustomer = new Customer();
            updatedCustomer.setCustomerId(customerId);

            System.out.println("Nhập thông tin mới (bỏ trống nếu muốn giữ nguyên):");

            System.out.print("Tên khách hàng mới: ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                updatedCustomer.setCustomerName(name);
            }


            Customer temp = new Customer();
            while (true) {
                System.out.print("Email mới: ");
                String email = scanner.nextLine().trim();

                if (email.isEmpty()) break;

                if (!temp.validateEmail(email)) {
                    System.out.println("Email không hợp lệ!");
                    continue;
                }

                if (customerBusiness.isEmailExists(email, customerId)) {
                    System.out.println("Email đã tồn tại!");
                    continue;
                }

                updatedCustomer.setEmail(email);
                break;
            }

            while (true) {
                System.out.print("Số điện thoại mới: ");
                String phone = scanner.nextLine().trim();

                if (phone.isEmpty()) break;

                if (!temp.validatePhone(phone)) {
                    System.out.println("SĐT không hợp lệ!");
                    continue;
                }

                if (customerBusiness.isPhoneExists(phone, customerId)) {
                    System.out.println("SĐT đã tồn tại!");
                    continue;
                }

                updatedCustomer.setPhone(phone);
                break;
            }

            while (true) {
                System.out.print("Loại khách hàng mới (Cá nhân/Doanh nghiệp/Ưu đãi): ");
                String type = scanner.nextLine().trim();

                if (type.isEmpty()) break;

                if (temp.validType(type)) {
                    updatedCustomer.setCustomerType(
                            type.substring(0,1).toUpperCase() + type.substring(1).toLowerCase()
                    );
                    break;
                } else {
                    System.out.println("Loại không hợp lệ! Nhập lại.");
                }
            }

            while (true) {
                System.out.print("Ngày đăng ký mới (yyyy-mm-dd): ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    break;
                }

                try {
                    LocalDate date = LocalDate.parse(input);
                    updatedCustomer.setRegistrationDate(date);
                    break;
                } catch (Exception e) {
                    System.out.println("Ngày không hợp lệ! Nhập lại theo dạng yyyy-mm-dd");
                }
            }

            boolean result = customerBusiness.updateCustomer(customerId, updatedCustomer);
            if (result) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Cập nhật thất bại!");
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật: " + e.getMessage());
        }
    }

    private static void deleteCustomer() {
        System.out.println("\n--- XÓA KHÁCH HÀNG ---");

        System.out.print("Nhập mã khách hàng cần xóa: ");
        String customerId = scanner.nextLine().trim().toUpperCase();

        customerBusiness.deleteCustomer(customerId);
    }

    private static void searchCustomerByName() {
        System.out.println("\n--- TÌM KIẾM KHÁCH HÀNG THEO TÊN ---");

        System.out.print("Nhập tên khách hàng cần tìm: ");
        String name = scanner.nextLine().trim();

        customerBusiness.searchByName(name);
    }

    private static void filterCustomerByType() {
        System.out.println("\n--- LỌC KHÁCH HÀNG THEO LOẠI ---");

        System.out.print("Nhập loại khách hàng cần lọc (Cá nhân/Doanh nghiệp/Ưu đãi): ");
        String type = scanner.nextLine().trim();

        customerBusiness.filterByType(type);
    }

    private static void sortCustomersByName() {
        System.out.println("\n--- SẮP XẾP KHÁCH HÀNG THEO TÊN TĂNG DẦN ---");
        customerBusiness.sortByName();
    }
}