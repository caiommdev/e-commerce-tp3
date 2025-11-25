public class Item {
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
