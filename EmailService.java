public class EmailService {
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
