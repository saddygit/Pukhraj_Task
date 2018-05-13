package com.prizy.pricer.service;


import com.prizy.pricer.dto.ProductPriceDetail;

public interface IdealPriceService {
    ProductPriceDetail getProductPriceDetail(String barcode);
    
}
