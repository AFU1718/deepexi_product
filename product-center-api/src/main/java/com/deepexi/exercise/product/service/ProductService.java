package com.deepexi.exercise.product.service;

import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.exercise.product.domain.eo.Product;
import com.github.pagehelper.PageHelper;

import java.util.List;

public interface ProductService {

    Boolean createProduct(Product product) throws Exception;
    Boolean deleteProductById(Integer id)throws Exception;
    Boolean updateProduct(Product product)throws Exception;
    Product getProductByIdAndShopId(Integer id,Integer shopId)throws Exception;
    PageBean getProductListByShopId(Integer page, Integer size, Integer shopId)throws Exception;

    void testError();
}
