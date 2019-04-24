package com.ecommerce.order.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductApplicationService {

  private final ProductRepository productRepository;

  @Autowired
  public ProductApplicationService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Transactional
  public ProductId createProduct(CreateProductCommand command) {
    Product product = Product
        .create(command.getName(), command.getDescription(), command.getPrice());
    productRepository.save(product);
    return product.getId();
  }

}
