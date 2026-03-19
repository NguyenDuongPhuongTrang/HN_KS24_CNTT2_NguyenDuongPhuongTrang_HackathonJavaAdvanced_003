package rc.business;

import rc.entity.Customer;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerBusiness {
    private static CustomerBusiness instance;
    private List<Customer> customers = new ArrayList<>();

    public static CustomerBusiness getInstance() {
        if (instance == null) {
            instance = new CustomerBusiness();
        }
        return instance;
    }

    // Hiển thị danh sách
    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("Danh sách khách hàng trống!");
            return;
        }
        printTableHeader();
        customers.forEach(Customer::displayData);
    }

    private void printTableHeader() {
        System.out.printf("| %-10s | %-20s | %-25s | %-15s | %-15s | %-12s |\n",
                "Mã KH", "Tên khách hàng", "Email", "Số điện thoại", "Loại KH", "Ngày đăng ký");
    }

    // Thêm khách hàng mới
    public boolean addCustomer(Customer customer) {
        customers.add(customer);
        return true;
    }


    // Cập nhật thông tin khách hàng
    public boolean updateCustomer(String customerId, Customer updatedCustomer) {
        Optional<Customer> existing = customers.stream()
                .filter(c -> c.getCustomerId().equals(customerId))
                .findFirst();

        if (existing.isEmpty()) {
            return false;
        }

        Customer customer = existing.get();

        if (updatedCustomer.getCustomerName() != null) {
            customer.setCustomerName(updatedCustomer.getCustomerName());
        }

        if (updatedCustomer.getEmail() != null) {
            customer.setEmail(updatedCustomer.getEmail());
        }

        if (updatedCustomer.getPhone() != null) {
            customer.setPhone(updatedCustomer.getPhone());
        }

        if (updatedCustomer.getCustomerType() != null) {
            customer.setCustomerType(updatedCustomer.getCustomerType());
        }

        if (updatedCustomer.getRegistrationDate() != null) {
            customer.setRegistrationDate(updatedCustomer.getRegistrationDate());
        }

        return true;
    }

    // Xóa khách hàng
    public boolean deleteCustomer(String customerId) {
        Optional<Customer> customerToDelete = customers.stream()
                .filter(c -> c.getCustomerId().equals(customerId))
                .findFirst();

        if (customerToDelete.isEmpty()) {
            System.out.println("Không tìm thấy khách hàng");
            return false;
        }

        customers.remove(customerToDelete.get());
        System.out.println("Xóa khách hàng thành công!");
        return true;
    }

    // Tìm kiếm theo tên
    public List<Customer> searchByName(String name) {
        List<Customer> result = customers.stream()
                .filter(c -> c.getCustomerName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("Không tìm thấy khách hàng nào!");
        } else {
            System.out.println("Tìm thấy " + result.size() + " khách hàng:");
            printTableHeader();
            result.forEach(Customer::displayData);
        }

        return result;
    }

    // Sắp xếp theo tên tăng dần
    public List<Customer> sortByName() {
        List<Customer> sortedList = customers.stream()
                .sorted(Comparator.comparing(Customer::getCustomerName))
                .collect(Collectors.toList());

        System.out.println("Danh sách khách hàng sắp xếp theo tên tăng dần:");
        printTableHeader();
        sortedList.forEach(Customer::displayData);

        return sortedList;
    }

    // Lọc theo loại khách hàng
    public List<Customer> filterByType(String customerType) {
        List<Customer> filteredList = customers.stream()
                .filter(c -> c.getCustomerType().equalsIgnoreCase(customerType))
                .collect(Collectors.toList());

        if (filteredList.isEmpty()) {
            System.out.println("Không tìm thấy khách hàng");
        } else {
            System.out.println("Danh sách khách hàng loại " + customerType + ":");
            printTableHeader();
            filteredList.forEach(Customer::displayData);
            System.out.println("Tổng số: " + filteredList.size());
        }

        return filteredList;
    }

    // Check tồn tại
    public boolean isCustomerIdExists(String customerId) {
        return customers.stream()
                .anyMatch(c -> c.getCustomerId().equals(customerId));
    }

    public boolean isEmailExists(String email, String currentId) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(email)
                        && !c.getCustomerId().equals(currentId));
    }

    public boolean isPhoneExists(String phone, String currentId) {
        return customers.stream()
                .anyMatch(c -> c.getPhone().equals(phone)
                        && !c.getCustomerId().equals(currentId));
    }

    private boolean isIdExists(String id) {
        return customers.stream()
                .anyMatch(c -> c.getCustomerId().equals(id));
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }
}