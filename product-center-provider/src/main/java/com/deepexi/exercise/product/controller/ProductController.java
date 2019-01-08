package com.deepexi.exercise.product.controller;

import com.deepexi.exercise.product.service.ProductService;
import com.deepexi.exercise.product.domain.eo.Product;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;

@Service
@Path("/api/v1/products")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Api("商品相关api")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductService productService;


//    Boolean createProduct(Product product) throws Exception;
//    Boolean deleteProductById(Integer id)throws Exception;
//    Boolean updateProduct(Product product)throws Exception;
//    Product getProductByIdAndShopId(Integer id,Integer shopId)throws Exception;
//    PageBean getProductListByShopId(Integer page, Integer size, Integer shopId)throws Exception;

    /**
     * 获取商品信息
     * 这是一个集成接入swagger的接口用例
     * 查看http://localhost:8088/helloworld/swagger.json 可获取相应接口描述json文件
     * @param page
     * @param size
     * @param shopId
     * @return
     */
    @GET
    @Path("/")
    @ApiOperation("按价格筛选，分页获取商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path",name="id",dataType="int",required=false,value="第几页",defaultValue="1"),
            @ApiImplicitParam(paramType="path",name="size",dataType="int",required=false,value="每页数据条数",defaultValue="10"),
            @ApiImplicitParam(paramType="path",name="shopId",dataType="int",required=false,value="商店id",defaultValue="0"),
    })
    @ApiResponses({@ApiResponse(code=400,message="请求参数没填好")})
    public Payload getProductListByShopId(@QueryParam("page") @DefaultValue("1")  Integer page,
                                  @QueryParam("size") @DefaultValue("10")  Integer size,
                                  @QueryParam("shopId")  Integer shopId) {

        Payload payload=new Payload(null,"-1","get product by id fails");
        try{
            payload.setPayload(productService.getProductListByShopId(page,size,shopId));
            payload.setCode("0");
            payload.setMsg("ok");
        }catch (Exception e){
            logger.error("service failure");
        }

        return payload;
    }

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload getProductByIdAndShopId(@PathParam("id") Integer id,@QueryParam("shopId")  Integer shopId) {
        Payload payload=new Payload(null,"-1","get product by id fails");
        try{
            payload.setPayload(productService.getProductByIdAndShopId(id,shopId));
            payload.setCode("0");
            payload.setMsg("ok");
        }catch (Exception e){
            logger.error("service failure");
        }

        return payload;
    }



    @POST
    @Path("/")
    public Payload createProduct(Product product) {
        Payload payload=new Payload(null,"-1","create product fails");
        try{
            payload.setPayload(productService.createProduct(product));
            payload.setCode("0");
            payload.setMsg("ok");
        }catch (Exception e){
            logger.error("service failure");
        }

        return payload;
    }

    @DELETE
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload deleteProductById(@PathParam("id") Integer id) {
        Payload payload=new Payload(null,"-1","delete product fails");
        try{
            payload.setPayload(productService.deleteProductById(id));
            payload.setCode("0");
            payload.setMsg("ok");
        }catch (Exception e){
            logger.error("service failure");
        }

        return payload;

    }

    @PUT
    @Path("/")
    public Payload updateProduct( Product product) {
        Payload payload=new Payload(null,"-1","update product fails");
        try{
            payload.setPayload(productService.updateProduct(product));
            payload.setCode("0");
            payload.setMsg("ok");
        }catch (Exception e){
            logger.error("service failure");
        }

        return payload;
    }



    @GET
    @Path("/testError")
    public Payload testError() {
        productService.testError();
        return new Payload(true);
    }
}
