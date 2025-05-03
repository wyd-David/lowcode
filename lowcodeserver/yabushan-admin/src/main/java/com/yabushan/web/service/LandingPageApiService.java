package com.yabushan.web.service;

import com.yabushan.web.model.entity.YmxGiftInfo;
import com.yabushan.web.model.entity.YmxOrderInfo;
import com.yabushan.web.model.vo.*;

import java.util.List;

/**
 * @Description BPMTimesExtApiService
 * @Author huihui
 * @Date 2021-03-29 11:30:46
 * @Version 1.0
 */
public interface LandingPageApiService {

    /**
     * 订单表匹配
     * @param orderInfoVO
     * @return
     */
    List<YmxOrderInfo> selectOrderMatching(YmxOrderInfoVO orderInfoVO);

    /**
     * 录入客户个人信息
     * @param infoVo
     * @return
     */
    int insertCustomerInformationInfo(YmxCustomerInformationInfoVo infoVo);

    /**
     * 礼物列表查询
     * @param giftInfoVO
     * @return
     */
    List<YmxGiftInfo> selectGiftInfo(YmxOrderGiftInfoVO giftInfoVO);

    /**
     * 记录客户所选礼物
     * @param giftInfoVO
     * @return
     */
    int recordCustomerGifts(YmxCustomerGiftInfoVO giftInfoVO);

    /**
     * 记录客户所选星级或评论
     *
     * @param commentInfoVO
     * @return
     */
    int recordCustomerStarRating(YmxCommentInfoVO commentInfoVO);
}
