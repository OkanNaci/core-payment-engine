
package com.deneme.payment;

public class ReceiptGenerator {

    public String generateReceipt(Order order) {
        StringBuilder receipt = new StringBuilder();

        receipt.append("========== RECEIPT ==========\n");
        receipt.append("Order ID: ").append(order.getOrderId()).append("\n");
        receipt.append("-----------------------------\n");

        receipt.append("Status: ")
                .append(order.isPaid() ? "PAID" : "PENDING")
                .append("\n");

        receipt.append("=============================\n");

        return receipt.toString();
    }
}