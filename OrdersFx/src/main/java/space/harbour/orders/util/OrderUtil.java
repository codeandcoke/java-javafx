package space.harbour.orders.util;

import space.harbour.orders.domain.Order;
import space.harbour.orders.exception.CustomerNotFoundException;

import java.util.List;

public class OrderUtil {

    public static double getTotalPrice(List<Order> orderList, String customerName)
        throws CustomerNotFoundException {
        long count = orderList.stream()
                .filter(order -> order.getCustomerName().equals(customerName))
                .count();
        if (count == 0) {
            throw new CustomerNotFoundException();
        }

        double totalPrice = orderList.stream()
                .filter(order -> order.getCustomerName().equals(customerName))
                .mapToDouble(Order::getPrice)
                .sum();

        return totalPrice;
    }
}
