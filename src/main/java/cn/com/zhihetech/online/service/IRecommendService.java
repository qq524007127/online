package cn.com.zhihetech.online.service;

import cn.com.zhihetech.online.bean.Recommend;
import cn.com.zhihetech.util.hibernate.service.SupportService;

/**
 * Created by ShenYunjie on 2016/1/11.
 */
public interface IRecommendService extends SupportService<Recommend> {
    /**
     * 根据推荐ID删除推荐记录
     *
     * @param entityId
     */
    void deleteRecommendById(String entityId);

    /**
     * 根据推荐ID修改推荐排序与推荐理由
     *
     * @param recommendId
     * @param orderIndex
     * @param reason
     */
    void updateOderAndReasonById(String recommendId, int orderIndex, String reason);
}
