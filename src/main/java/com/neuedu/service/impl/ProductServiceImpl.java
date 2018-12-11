package com.neuedu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.ProductService;
import com.neuedu.utils.DateUtil;
import com.neuedu.utils.PropertiesUtil;
import com.neuedu.vo.ProductDetaVo;
import com.neuedu.vo.ProductListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public ServerResponse saveOrUpdate(Product product) {

        //参数不能为空
        if (product==null){
            return ServerResponse.createServerResponseByError("参数为空");
        }
        //2。设置商品的主图subimages-->1.jpg 2.jpg
        String subImages=product.getSubImages();
        if (subImages!=null&&!subImages.equals("")){
            String[] subImageArr=subImages.split("");
            if (subImageArr.length>0){
                //设置商品的主图
                product.setMainImage(subImageArr[0]);
            }
        }
        //3.判断添加或者更新

        if (product.getId()==null){
            //添加
            int result=productMapper.insert(product);
            if (result>0){
                return ServerResponse.createServerResponseBySuccess();
            }else {
                return  ServerResponse.createServerResponseByError("更新失败");
            }

        }else {
            int result=productMapper.updateByPrimaryKey(product);
            if (result>0){
                return ServerResponse.createServerResponseBySuccess();
            }else {
                return  ServerResponse.createServerResponseByError("更新失败");
            }
        }
    }

    /**
     * 商品上下架管理
     * @param productId
     * @param status 商品的状态
     * @return
     */
    @Override
    public ServerResponse set_sale_status(Integer productId, Integer status) {
        //1.参数的非空校验
        if (productId==null){
            return ServerResponse.createServerResponseByError("商品的id不能为空");
        }
        if (status ==null){
            return ServerResponse.createServerResponseByError("商品的状态不能为空");
        }
        //2.更新商品的状态  只更新商品状态
        Product product=new Product();
        product.setId(productId);
        product.setStatus(status);
         int i=productMapper.updateStatusByStatusId(product);
         if (i>0){
             return ServerResponse.createServerResponseBySuccess();
         }
        //3.返回结果
        return ServerResponse.createServerResponseByError("失败");
    }

    /**
     * 查看商品详情
     * @param productId
     * @return
     */
    @Override
    public ServerResponse detail(Integer productId) {
        // 1.参数非空校验
        if (productId==null){
            return ServerResponse.createServerResponseByError("商品的id不能为空");
        }
        //2.根据商品id查询商品-->productid
        Product product=productMapper.selectByPrimaryKey(productId);

        //商品的非空判断
        if (product==null){
            return ServerResponse.createServerResponseByError("商品不存在");
        }
        //3.将商品转换
        ProductDetaVo productDetaVo=assembProductDetaVo(product);
        //4.返回结果
        return ServerResponse.createServerResponseBySuccess(productDetaVo);
    }



    /**
     * 封装商品的工具类
     * @param product
     * @return
     */
    private ProductDetaVo assembProductDetaVo(Product product){
        ProductDetaVo productDetaVo=new ProductDetaVo();
        productDetaVo.setCategoryId(product.getCategoryId());
        productDetaVo.setCreateTime(DateUtil.dateToStr(product.getCreateTime()));
        productDetaVo.setDatail(product.getDatail());
        //设置图片的域名
        productDetaVo.setImageHost(PropertiesUtil.readByKey("imageHost"));
        productDetaVo.setName(product.getName());
        productDetaVo.setMainImage(product.getMainImage());
        productDetaVo.setSubImages(product.getSubImages());
        productDetaVo.setId(product.getId());
        productDetaVo.setPrice(product.getPrice());
        productDetaVo.setStock(product.getStock());
        productDetaVo.setUpdateTime(DateUtil.dateToStr(product.getUpdateTime()));
        productDetaVo.setSubtitle(product.getSubtitle());
        productDetaVo.setDatail(product.getDatail());
        //获取父类商品的id
        Category category=categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category!=null){
            productDetaVo.setParentId(category.getParentId());
        }else {
            //默认根节点
            productDetaVo.setParentId(0);
        }
        return  productDetaVo;
    }
    /**
     * 后台-商品列表-分页查询-利用分页插件
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {

        //调用分页查询的插件方法
        PageHelper.startPage(pageNum,pageSize);//实现原理spring aop
        //1.查询商品数据  select *from product limit
        List<Product> productList=productMapper.selectAll();
        List<ProductListVo> productListVoList= Lists.newArrayList();//返回前端的数据
        //
        if (productList!=null&&productList.size()>0){
            for (Product product : productList) {
               ProductListVo productListVo= assembProductListVo(product);
               productListVoList.add(productListVo);
            }
        }
        PageInfo pageInfo=new PageInfo(productListVoList);

        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }
    private ProductListVo assembProductListVo(Product product){
        ProductListVo productListVo=new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setName(product.getName());
        productListVo.setPrice(product.getPrice());
        productListVo.setStatus(product.getStatus());
        productListVo.setSubtitle(product.getSubtitle());//标题
        return productListVo;
    }

    /**
     * 搜索的实现--->模糊查询
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse search(String productName, Integer productId, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        if (productName!=null&&!productName.equals("")){
            productName="%"+productName+"%";
        }
        List<Product> productList=productMapper.findProductByProductIdProductName(productId,productName);
        List<ProductListVo> productListVoList= Lists.newArrayList();//返回前端的数据
        if (productList!=null&&productList.size()>0){
            for (Product product : productList) {
                ProductListVo productListVo= assembProductListVo(product);
                productListVoList.add(productListVo);
            }
        }
        PageInfo pageInfo=new PageInfo(productListVoList);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }
}
