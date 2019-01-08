package com.deepexi.exercise.product.controller;

import com.deepexi.exercise.product.service.ProductService;
import com.deepexi.exercise.product.domain.eo.Product;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
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

    /**
     * 获取商品信息
     * 这是一个集成接入swagger的接口用例
     * 查看http://localhost:8088/helloworld/swagger.json 可获取相应接口描述json文件
     * @param page
     * @param size
     * @param price
     * @return
     */
    @GET
    @Path("/")
    @ApiOperation("按价格筛选，分页获取商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path",name="id",dataType="int",required=false,value="第几页",defaultValue="1"),
            @ApiImplicitParam(paramType="path",name="size",dataType="int",required=false,value="每页数据条数",defaultValue="10"),
            @ApiImplicitParam(paramType="path",name="age",dataType="int",required=false,value="价格",defaultValue="0"),
    })
    @ApiResponses({@ApiResponse(code=400,message="请求参数没填好")})
    public Payload getProductList(@QueryParam("page") @DefaultValue("1")  Integer page,
                                  @QueryParam("size") @DefaultValue("10")  Integer size,
                                  @QueryParam("age") @DefaultValue("0")  Integer price) {
        return new Payload(productService.getProductList(page, size, price));
    }

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload getProductById(@PathParam("id") String id) {
        return new Payload(productService.getProductById(id));
    }

    @POST
    @Path("/")
    public Payload createProduct(Product product) {
        return new Payload(productService.createProduct(product));
    }

    @PUT
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload updateProductById(@PathParam("id") String id, Product product) {
        return new Payload(null);
    }

    @DELETE
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload deleteProductById(@PathParam("id") String id) {
        return new Payload(productService.deleteProductById(id));
    }

    @GET
    @Path("/testError")
    public Payload testError() {
        productService.testError();
        return new Payload(true);
    }
}
