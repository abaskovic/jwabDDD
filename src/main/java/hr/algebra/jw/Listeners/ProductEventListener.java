package hr.algebra.jw.Listeners;

import hr.algebra.jw.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

public class ProductEventListener implements ApplicationListener<ProductEvent> {

    @Autowired
    ProductService productService;
    @Override
    public void onApplicationEvent(ProductEvent event) {
        productService.addEventMessage(event.getType() +": "+ event.getProduct().getName());
    }
}
