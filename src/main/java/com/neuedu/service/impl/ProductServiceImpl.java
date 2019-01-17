package com.neuedu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.neuedu.common.ResponseCodeCategory;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.CategoryService;
import com.neuedu.service.ProductService;
import com.neuedu.utils.DateUtil;
import com.neuedu.utils.FTPUtil;
import com.neuedu.utils.PropertiesUtil;
import com.neuedu.vo.ProductDetaVo;
import com.neuedu.vo.ProductListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
   CategoryService categoryService;
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

    /**
     * 图片上传
     * @param file
     * @param path
     * @return
     */
    @Override
    public ServerResponse upload(MultipartFile file, String path) {
        //非空判断
        if (file==null){
            return ServerResponse.createServerResponseByError();
        }
        //设置唯一名字
        String originalFilename=file.getOriginalFilename();
        //获取图片的拓展名
        String exName=originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成图片新名字   唯一
        String newFileName=UUID.randomUUID().toString()+exName;
        File pathFile=new File(path);
        if (!pathFile.exists()){   //判断路径是否存在
            pathFile.setWritable(true);              //若存在可操作
            pathFile.mkdirs();                      //若不存在就要生成此目录
        }
        File file1=new File(path,newFileName);
        try {
            file.transferTo(file1);
            //上传到图片服务器
            FTPUtil.uploadFile(Lists.newArrayList(file1));
            //......
            Map<String,String> map=Maps.newHashMap();
            map.put("uri",newFileName);
            map.put("url",PropertiesUtil.readByKey("imageHost")+"/"+newFileName);

            //删除应用服务器上图片
//            file1.delete();
            return  ServerResponse.createServerResponseBySuccess(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 前台接口--商品详细
     * @param productId
     * @return
     */
    @Override
    public ServerResponse detailprotal(Integer productId) {
       //1.参数非空校验
        if (productId==null){
            return ServerResponse.createServerResponseByError("商品的id不能为空");
        }
        //2.查询product
         Product product=productMapper.selectByPrimaryKey(productId);
         //商品的非空判断
         if (product==null){
             return ServerResponse.createServerResponseByError("商品不存在");
         }
       //根据商品id查询
       //3.校验商品状态
        if (product.getStatus()!=ResponseCodeCategory.PRODUCT_ONLINE.getStatus()){
             return ServerResponse.createServerResponseByError("商品已下架或或删除");
        }
       //4.获取  productDataVO

       ProductDetaVo productDetaVo= assembProductDetaVo(product);
           //5.返回结果
        return ServerResponse.createServerResponseBySuccess(productDetaVo);
    }

    @Override
    public ServerResponse list(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderby) {
        //1.参数的校验         categoryId 和            keyword不能同时为空
        if (categoryId==null&&(keyword==null||keyword.equals(""))){
           return ServerResponse.createServerResponseByError("参数错误");
        }
     Set<Integer> integerSet=Sets.newHashSet();
        //2.根据 categoryId查询
        if (categoryId!=null){
            Category category=categoryMapper.selectByPrimaryKey(categoryId);
            if (category==null&&(keyword==null||keyword.equals(""))){
                //说明没有商品数据
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo> productListVoList=Lists.newArrayList();
                PageInfo pageInfo=new PageInfo(productListVoList);
                return ServerResponse.createServerResponseBySuccess(pageInfo);
            }
            //查询类别下所有子类----递归查询
           ServerResponse serverResponse= categoryService.get_deep_category(categoryId);

            if (serverResponse.isSuccess()){
                integerSet=(Set<Integer>)serverResponse.getData();
            }
        }
        //关键字//3.根据keyword查询
        if (keyword!=null&&!keyword.equals("")){
            keyword="%"+keyword+"%";
        }


        if (orderby.equals("")){
            PageHelper.startPage(pageNum,pageSize);
        }else {
           String[] orderByArr= orderby.split("_");
           if (orderByArr.length>1){
               PageHelper.startPage(pageNum,pageSize,orderByArr[0]+" "+orderByArr[1]);
           }else {
                //分页查
               PageHelper.startPage(pageNum,pageSize);
           }

        }


      //4.将 List<Product>转换为List<ProductListVo>
        List<Product> productList=productMapper.searchProduct(integerSet,keyword);
        List<ProductListVo> productListVoList=Lists.newArrayList();
          if (productList!=null&&productList.size()>0){
              for (Product product : productList) {
                 ProductListVo productListVo=assembProductListVo(product);
                 productListVoList.add(productListVo);
              }

          }


        //5.分页
        PageInfo pageInfo=new PageInfo();
          pageInfo.setList(productListVoList);
        //6.返回结果
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }
}
