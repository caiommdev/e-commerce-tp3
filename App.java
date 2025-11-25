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