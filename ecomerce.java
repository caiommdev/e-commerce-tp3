import java.util.*;

public class App {
    public static void main(String[] args) {
        Client client = new Client("João", "joao@email.com");
        Order order = new Order(client, new EmailService());
        
        order.addItem("Notebook", 1, 3500.0);
        order.addItem("Mouse", 2, 80.0);
        
        order.printInvoice();
        order.sendConfirmationEmail();
    }
}

/**
 * Classe que encapsula as informações do cliente
 * Refatoração 5: Reposicionar campos em classes apropriadas
 */
class Client {
    private final String name;
    private final String email;

    public Client(String name, String email) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido");
        }
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

/**
 * Classe que representa um item do pedido
 * Refatoração 2: Substituir primitivos por objetos com responsabilidade
 */
class Item {
    private final String productName;
    private final int quantity;
    private final double unitPrice;

    public Item(String productName, int quantity, double unitPrice) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotalPrice() {
        return quantity * unitPrice;
    }

    public String formatItemLine() {
        return quantity + "x " + productName + " - R$" + unitPrice;
    }
}

/**
 * Classe que representa um pedido
 * Refatoração 1: Encapsular registros e coleções
 * Refatoração 3: Ocultar delegados para reduzir acoplamento
 */
class Order {
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

    /**
     * Adiciona um item ao pedido
     * Refatoração 1: Encapsular coleções
     */
    public void addItem(String productName, int quantity, double unitPrice) {
        Item item = new Item(productName, quantity, unitPrice);
        items.add(item);
    }

    /**
     * Retorna uma cópia imutável da lista de itens
     */
    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Refatoração 6: Extrair método para cálculo do subtotal
     */
    private double calculateSubtotal() {
        double subtotal = 0;
        for (Item item : items) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    /**
     * Refatoração 6: Extrair método para cálculo do desconto
     * Refatoração 4: Mover função de cálculo de desconto para Order
     */
    private double calculateDiscount() {
        double subtotal = calculateSubtotal();
        return subtotal * discountRate;
    }

    /**
     * Refatoração 6: Extrair método para cálculo do total final
     */
    private double calculateTotal() {
        return calculateSubtotal() - calculateDiscount();
    }

    /**
     * Refatoração 6: Extrair método para impressão do cabeçalho
     */
    private void printHeader() {
        System.out.println("Cliente: " + client.getName());
    }

    /**
     * Refatoração 6: Extrair método para impressão dos itens
     */
    private void printItems() {
        for (Item item : items) {
            System.out.println(item.formatItemLine());
        }
    }

    /**
     * Refatoração 6: Extrair método para impressão do resumo financeiro
     */
    private void printSummary() {
        double subtotal = calculateSubtotal();
        double discount = calculateDiscount();
        double total = calculateTotal();
        
        System.out.println("Subtotal: R$" + subtotal);
        System.out.println("Desconto: R$" + discount);
        System.out.println("Total final: R$" + total);
    }

    /**
     * Refatoração 4: Reorganizar responsabilidades - método principal de impressão
     */
    public void printInvoice() {
        printHeader();
        printItems();
        printSummary();
    }

    /**
     * Refatoração 3: Ocultar delegado - encapsular chamada ao serviço de e-mail
     * Refatoração 6: Extrair método para gerar mensagem
     */
    public void sendConfirmationEmail() {
        String message = generateConfirmationMessage();
        emailService.sendEmail(client.getEmail(), message);
    }

    /**
     * Refatoração 6: Extrair método para geração de mensagem
     */
    private String generateConfirmationMessage() {
        return "Pedido recebido! Obrigado pela compra.";
    }
}

/**
 * Serviço responsável pelo envio de e-mails
 * Refatoração 7: Remover método estático para melhor testabilidade
 */
class EmailService {
    public void sendEmail(String to, String message) {
        if (to == null || to.trim().isEmpty()) {
            throw new IllegalArgumentException("Destinatário não pode ser vazio");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Mensagem não pode ser vazia");
        }
        System.out.println("Enviando e-mail para " + to + ": " + message);
    }
}