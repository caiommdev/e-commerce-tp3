import java.util.*;

public class Order {
    private final Client client;
    private final List<Item> items;
    private final double discountRate;
    private final EmailService emailService;

    public Order(Client client, EmailService emailService) {
        if (client == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
        if (emailService == null) {
            throw new IllegalArgumentException("Serviço de e-mail não pode ser nulo");
        }
        this.client = client;
        this.items = new ArrayList<>();
        this.discountRate = 0.1;
        this.emailService = emailService;
    }

    public void addItem(String productName, int quantity, double unitPrice) {
        Item item = new Item(productName, quantity, unitPrice);
        items.add(item);
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    private double calculateSubtotal() {
        double subtotal = 0;
        for (Item item : items) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    private double calculateDiscount() {
        double subtotal = calculateSubtotal();
        return subtotal * discountRate;
    }

    private double calculateTotal() {
        return calculateSubtotal() - calculateDiscount();
    }

    private void printHeader() {
        System.out.println("Cliente: " + client.getName());
    }

    private void printItems() {
        for (Item item : items) {
            System.out.println(item.formatItemLine());
        }
    }

    private void printSummary() {
        double subtotal = calculateSubtotal();
        double discount = calculateDiscount();
        double total = calculateTotal();
        
        System.out.println("Subtotal: R$" + subtotal);
        System.out.println("Desconto: R$" + discount);
        System.out.println("Total final: R$" + total);
    }

    public void printInvoice() {
        printHeader();
        printItems();
        printSummary();
    }

    public void sendConfirmationEmail() {
        String message = generateConfirmationMessage();
        emailService.sendEmail(client.getEmail(), message);
    }

    private String generateConfirmationMessage() {
        return "Pedido recebido! Obrigado pela compra.";
    }
}
