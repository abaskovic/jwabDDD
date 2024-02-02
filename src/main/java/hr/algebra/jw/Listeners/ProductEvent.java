package hr.algebra.jw.Listeners;

import hr.algebra.jw.Model.Product;
import org.springframework.context.ApplicationEvent;

public class ProductEvent extends ApplicationEvent {

    private final Product product;
    private final String type;

    public ProductEvent(Object source, Product product, String type) {
        super(source);
        this.product = product;
        this.type = type;
    }

    public Product getProduct() {
        return product;
    }

    public String getType() {
        return type;
    }
}
