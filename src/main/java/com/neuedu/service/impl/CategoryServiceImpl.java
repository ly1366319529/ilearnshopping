package com.neuedu.service.impl;

import com.google.common.collect.Sets;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.pojo.Category;
import com.neuedu.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 获得品类子节点（平级）
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse get_category(Integer categoryId) {
        //1.非空校验
        if (StringUtils.isBlank(String.valueOf(categoryId))){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }
        //2.根据Category查询类别
       Category category= categoryMapper.selectByPrimaryKey(categoryId);
        if (category==null){
            return  ServerResponse.createServerResponseByError("查询类别不存在！");
        }
        //3.查询子类别
        List<Category> categoryList=categoryMapper.findChildCategory(categoryId);

        //4.返回结果
        return ServerResponse.createServerResponseBySuccess(null,categoryList);
    }

    /**
     * 添加节点
     * @param parentId
     * @param categoryName
     * @return
     */
    @Override
    public ServerResponse add_category(Integer parentId, String categoryName) {
        //1.参数校验
        if (categoryName==null||categoryName.equals("")){
            return ServerResponse.createServerResponseByError("类别名称不能为空");
        }
        //2.添加节点
        Category category=new Category();
        category.setUsername(categoryName);
        category.setParentId(parentId);
        category.setStatus(1);
        int i=categoryMapper.insert(category);
        if (i<0){
            return ServerResponse.createServerResponseByError("添加失败");
        }
        //3.返回结果
        return ServerResponse.createServerResponseBySuccess();
    }
    /**
     * 修改节点
     * @param categoryId
     * @param categoryName
     * @return
     */
    @Override
    public ServerResponse update_category(Integer categoryId, String categoryName) {
      //1.参数不能为空
        if (categoryId==null||categoryId.equals("")){
            return ServerResponse.createServerResponseByError("类别不能为空");
        }
        if (categoryName==null||categoryName.equals("")){
            return ServerResponse.createServerResponseByError("类别名不能为空");
        }
      //2.根据categoryId查询
        Category category=categoryMapper.selectByPrimaryKey(categoryId);
        if (category==null){
            return ServerResponse.createServerResponseByError("要修改的类别不存在");
        }

      //3. 修改对象类别
        category.setUsername(categoryName);
        int result=categoryMapper.updateByPrimaryKey(category);
        if (result<0){
            //修改失败
            return ServerResponse.createServerResponseByError("添加失败");
        }
        //修改成功
        return ServerResponse.createServerResponseBySuccess();
    }

    /**
     * 获取当前分类id及递归子节点categoryId
     */

    @Override
    public ServerResponse get_deep_category(Integer categoryId){

        //1.参数的非空校验
        if (categoryId==null){
            return ServerResponse.createServerResponseByError("类别id不能为空");
        }
        //2.查询
        Set<Category> categorySet=Sets.newHashSet();
        categorySet=findAllchildCategory(categorySet,categoryId);//通过类别id
        Set<Integer> integerSet=Sets.newHashSet();
        Iterator<Category> categoryIterator=categorySet.iterator();
        while (categoryIterator.hasNext()){
            Category category=categoryIterator.next();
            integerSet.add(category.getId());
        }
        return ServerResponse.createServerResponseBySuccess(integerSet);
    }

    private Set<Category> findAllchildCategory(Set<Category> categorySet,Integer categoryId){
       Category category= categoryMapper.selectByPrimaryKey(categoryId);
       if (category!=null){
           categorySet.add(category);
       }

       //查找categoryId下的子节点（平级）
        List<Category> categoryList=categoryMapper.findChildCategory(categoryId);
       if (categoryList!=null&&categoryList.size()!=0){
           //遍历集合
           for (Category category1 : categoryList) {
               findAllchildCategory(categorySet, category1.getId());

           }
       }
       return categorySet;
    }
}
