package com.yabushan.web.mapper;

import com.yabushan.web.model.entity.YmxCustomerInformationInfo;
import com.yabushan.web.model.entity.YmxGiftInfo;
import com.yabushan.web.model.entity.YmxOrderInfo;
import com.yabushan.web.model.vo.YmxCommentInfoVO;
import com.yabushan.web.model.vo.YmxCustomerGiftInfoVO;
import com.yabushan.web.model.vo.YmxOrderInfoVO;

import java.util.List;

/**
 * @Description LandingPageMapper
 * @Author huihui
 * @Date 2021-03-29 13:49:15
 * @Version 1.0
 */
public interface LandingPageMapper {

    /**
     * 订单号匹配
     * @param infoVO
     * @return
     */
    List<YmxOrderInfo> selectOrderMatching(YmxOrderInfoVO infoVO);

    /**
     * 录入客户个人信息
     * @param infoVo
     * @return
     */
    int insertCustomerInformationInfo(YmxCustomerInformationInfo infoVo);

    /**
     * 更新订单表客户主键
     * @param orderInfoVO
     * @return
     */
    int updateOrderId(YmxOrderInfoVO orderInfoVO);

    /**
     * 根据 ASIN 查询对应礼物
     * @param giftId
     * @return
     */
    List<YmxGiftInfo> selectGiftInfo(String giftId);

    /**
     * 记录客户所选礼物
     * @param giftInfoVO
     * @return
     */
    int recordCustomerGifts(YmxCustomerGiftInfoVO giftInfoVO);

    /**
     * 记录客户所选星级
     * @param commentInfoVO
     * @return
     */
    int recordCustomerStarRating(YmxCommentInfoVO commentInfoVO);

    /**
     * 更新评论或感谢语
     * @param commentInfoVO
     * @return
     */
    int updateCustomerStarRating(YmxCommentInfoVO commentInfoVO);
}
