package com.deepexi.exercise.product.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.deepexi.exercise.product.VO.ProductSearchVO;
import com.deepexi.exercise.product.service.ProductService;
import com.deepexi.exercise.product.domain.eo.Product;
import com.deepexi.exercise.product.extension.ApplicationException;
import com.deepexi.exercise.product.extension.AppRuntimeEnv;
import com.deepexi.exercise.product.mapper.ProductMapper;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import javax.ws.rs.core.Response;

@Component
@Service(version = "${demo.service.version}")
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    @Override
    public Boolean createProduct(Product product) throws Exception{
        Boolean result = false;
        try{
            productMapper.createProduct(product);
            result = true;
        }catch (Exception e){
            throw new Exception();
        }
        return result;
    }
    @Override
    public Boolean deleteProductById(Integer id) throws Exception{
        Boolean result = false;
        try{
            productMapper.deleteProductById(id);
            result = true;
        }catch (Exception e){
            throw new Exception();
        }
        return result;
    }
    @Override
    public Boolean updateProduct(Product product) throws Exception{
        Boolean result = false;
        try{
            productMapper.updateProduct(product);
            result = true;
        }catch (Exception e){
            throw new Exception();
        }
        return result;
    }
    @Override
    public Product getProductByIdAndShopId(Integer id,Integer shopId){
        ProductSearchVO productSearchVO=new ProductSearchVO();
        productSearchVO.setId(id);
        productSearchVO.setShopId(shopId);
        return productMapper.getProductByIdAndShopId(productSearchVO);
    }
    @Override
    public PageBean getProductListByShopId(Integer page, Integer size, Integer shopId){
        ProductSearchVO productSearchVO=new ProductSearchVO();
        productSearchVO.setPage(page);
        productSearchVO.setSize(size);
        productSearchVO.setShopId(shopId);
        PageHelper.startPage(page, size);
        List<Product> list = productMapper.getProductListByShopId(productSearchVO);
        return new PageBean<>(list);
    }
//
//    public PageBean getProductList(Integer page, Integer size, Integer age) {
//        PageHelper.startPage(page, size);
//        List<Product> userTasks = productMapper.selectPageVo(age);
//        return new PageBean<>(userTasks);
//    }
//
//    public Boolean createProduct(Product product) throws Exception {
//        Boolean result = false;
//        try{
//            productMapper.createProduct(product);
//            result = true;
//        }catch (Exception e){
//            throw new Exception();
//        }
//        return result;
//    }
//
//    public Boolean deleteProductById(String id) {
//        productMapper.deleteById(id);
//        return true;
//    }









//    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
//
//    @Autowired
//    private ProductMapper productMapper;
//
//    @Autowired
//    private AppRuntimeEnv appRuntimeEnv;
//
//    @Override
//    public PageBean findPage(Product eo,Integer page, Integer size) {
//        PageHelper.startPage(page, size);
//        List<Product> list = productMapper.findList(eo);
//        return new PageBean<>(list);
//    }
//
//    @Override
//    public List<Product> findAll(Product eo) {
//        List<Product> list = productMapper.findList(eo);
//        return list;
//    }
//    @Override
//    public Product detail(Integer  pk){
//        return productMapper.selectById(pk);
//    }
//
//    @Override
//    public Boolean create(Product eo) {
//        int result = productMapper.insert(eo);
//        if (result > 0) {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public Boolean update(Integer  pk,Product eo) {
//        eo.setId(pk);
//        int result =  productMapper.updateById(eo);
//        if (result > 0) {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public Boolean delete(Integer ... pk) {
//        productMapper.deleteByIds(pk);
//        return true;
//    }






    @SentinelResource(value = "testSentinel", fallback = "doFallback", blockHandler = "exceptionHandler")
    public Product getProductById(String id) {
        // dubbo生产者被消费者调用时，客户端隐式传入的参数
        String tenantId = RpcContext.getContext().getAttachment("tenantId");
        logger.info("获取客户端隐式参数，tenantId：{}", tenantId);
        return productMapper.selectById(id);
    }

    public String doFallback(long i) {
        // Return fallback value.
        return "Oops, degraded";
    }

    /**
     * 熔断降级处理逻辑
     * @param s
     * @param ex
     * @return
     */
    public void exceptionHandler(long s, Exception ex) {
        // Do some log here.
        logger.info("-------------熔断降级处理逻辑---------\n");
        throw new ApplicationException(Response.Status.BAD_REQUEST, "100001", "熔断降级处理!");
    }

    @Override
    public void testError() {
        throw new ApplicationException(Response.Status.BAD_REQUEST, "100002", "这是返回的自定义错误信息!");
    }
}


