package com.deepexi.exercise.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.exercise.product.VO.ProductSearchVO;
import com.deepexi.exercise.product.domain.eo.Product;
import com.deepexi.util.pageHelper.PageBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by donh on 2018/7/24.
 */
//@Service
public interface ProductMapper extends BaseMapper<Product> {

    void createProduct(@Param("product")Product product);
    void deleteProductById(@Param("id")Integer id);
    void updateProduct(@Param("product")Product product);
    Product getProductByIdAndShopId(@Param("productSearchVO")ProductSearchVO productSearchVO);
    List<Product> getProductListByShopId(@Param("productSearchVO")ProductSearchVO productSearchVO);
}
