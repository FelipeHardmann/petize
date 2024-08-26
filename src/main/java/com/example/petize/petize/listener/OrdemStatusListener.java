package com.example.petize.petize.listener;

import com.example.petize.petize.config.RabbitMqConfig;
import com.example.petize.petize.listener.dto.OrderChangeStatus;
import com.example.petize.petize.services.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrdemStatusListener {

    private final Logger logger = LoggerFactory.getLogger(OrdemStatusListener.class);

    private final PedidoService pedidoService;

    public OrdemStatusListener(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @RabbitListener(queues = RabbitMqConfig.ORDER_CREATED_QUEUE)
    public void listen(Message<OrderChangeStatus> message) {
        OrderChangeStatus orderChangeStatus = message.getPayload();
        logger.info("Message consumed: {}", orderChangeStatus);

        pedidoService.save(orderChangeStatus);

        if (orderChangeStatus.id() != null) {
            pedidoService.processUpdateOrder(orderChangeStatus.id(), orderChangeStatus);
        } else {
            pedidoService.processNewOrder(orderChangeStatus);
        }
    }

}
