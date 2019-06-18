package com.ecommerce.order.product;

import com.ecommerce.order.common.utils.PagedResource;
import com.ecommerce.order.product.representation.ProductRepresentationService;
import com.ecommerce.order.product.representation.ProductSummaryRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductApplicationService {

  private final ProductRepository productRepository;
  private final ProductSearchService productSearchService;
  private final ProductRepresentationService productRepresentationService;

  @Autowired
  public ProductApplicationService(ProductRepository productRepository,
      ProductSearchService productSearchService,
      ProductRepresentationService productRepresentationService) {
    this.productRepository = productRepository;
    this.productSearchService = productSearchService;
    this.productRepresentationService = productRepresentationService;
  }

  @Transactional
  public ProductId createProduct(CreateProductCommand command) {
    Product product = Product
        .create(command.getName(), command.getDescription(), command.getPrice());
    productRepository.save(product);
    return product.getId();
  }

  @Transactional(readOnly = true)
  public PagedResource<ProductSummaryRepresentation> pagedProducts(int pageIndex, int pageSize) {
    PagedResource<Product> products = productSearchService
        .pagedProducts(pageIndex, pageSize);
    return productRepresentationService.toSummary(products);
  }

  public Product byId(String id) {
    return productRepository.byId(ProductId.productId(id));
  }
}
