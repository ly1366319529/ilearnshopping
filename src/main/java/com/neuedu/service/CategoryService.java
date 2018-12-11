package com.neuedu.service;

import com.neuedu.common.ServerResponse;

public interface CategoryService {
    /**
     * 获取类别的子节点
     */
    public ServerResponse get_category(Integer categoryId);

    /**
     *
     *增加节点
     * @param parentId
     * @param categoryName
     * @return
     */
    public ServerResponse add_category(Integer parentId,String categoryName);

    /**
     * 修改节点
     * @param parentId
     * @param categoryName
     * @return
     */

    public ServerResponse update_category(Integer parentId, String categoryName);

    public ServerResponse get_deep_category(Integer categoryId);


}
