package br.com.upper.product.api.modules.product.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.upper.product.api.modules.product.dto.ProductStockDto;
import br.com.upper.product.api.modules.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductStockListener {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void reciveProductStockMessage(ProductStockDto product) throws JsonProcessingException {

        log.info("Recebendo mensagem: {}", new ObjectMapper().writeValueAsString(product));
        productService.updateProductStock(product);
    }

}
