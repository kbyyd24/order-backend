package com.ecommerce.order.order;

import static java.util.stream.Collectors.toList;

import com.ecommerce.order.order.command.CreateOrderCommand;
import com.ecommerce.order.order.model.Order;
import com.ecommerce.order.order.model.OrderId;
import com.ecommerce.order.order.model.OrderIdGenerator;
import com.ecommerce.order.order.model.OrderItem;
import com.ecommerce.order.product.ProductId;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCreateService {

  private final OrderIdGenerator idGenerator;

  @Autowired
  public OrderCreateService(OrderIdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public Order create(CreateOrderCommand command) {
    List<OrderItem> orderItems = command.getItems().stream()
        .map(itemCommand -> OrderItem
            .create(ProductId.productId(itemCommand.getProductId()), itemCommand.getCount(),
                itemCommand.getItemPrice()))
        .collect(toList());
    OrderId orderId = idGenerator.generate();
    return Order.create(orderId, orderItems);
  }

}
