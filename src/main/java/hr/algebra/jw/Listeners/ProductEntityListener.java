package hr.algebra.jw.Listeners;

import hr.algebra.jw.Model.Product;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;



public class ProductEntityListener {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostPersist
    public void onProductCreated(Product product) {
        publishEvent(product, "CREATE");
    }

    @PostUpdate
    public void onProductUpdated(Product product) {
        publishEvent(product, "UPDATE");

    }

    @PreRemove
    public void onProductRemoved(Product product) {
        publishEvent(product, "DELETE");
    }


    private void publishEvent(Product product, String eventType) {
        ProductEvent productEvent = new ProductEvent(this, product, eventType);
        eventPublisher.publishEvent(productEvent);
    }
}
