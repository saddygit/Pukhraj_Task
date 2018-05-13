
package com.prizy.pricer.service.repository;

import com.prizy.pricer.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(path = "product")
public interface ProductRepository extends CrudRepository<Product, Long> {

	
    Product findByBarcode(String barcode);

	Page<Product> findByNameContainingAllIgnoringCase(String name, Pageable pageable);

}
