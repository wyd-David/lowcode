package com.yabushan.web.service.impl;

import com.yabushan.common.exception.CustomException;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.web.mapper.LandingPageMapper;
import com.yabushan.web.model.entity.YmxCustomerInformationInfo;
import com.yabushan.web.model.entity.YmxGiftInfo;
import com.yabushan.web.model.entity.YmxOrderInfo;
import com.yabushan.web.model.vo.*;
import com.yabushan.web.service.LandingPageApiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description BPMTimesExtApiServiceImpl
 * @Author huihui
 * @Date 2021-03-29 11:30:39
 * @Version 1.0
 */
@Service
public class LandingPageApiServiceImpl implements LandingPageApiService {

    @Resource
    private LandingPageMapper landingPageMapper;


    /**
     * 订单号匹配
     * @param orderInfoVO
     * @return
     */
    @Override
    public List<YmxOrderInfo> selectOrderMatching(YmxOrderInfoVO orderInfoVO) {
        List<YmxOrderInfo> ymxOrderInfoList = landingPageMapper.selectOrderMatching(orderInfoVO);
        return ymxOrderInfoList;
    }

    /**
     * 录入客户个人信息
     * @param infoVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public int insertCustomerInformationInfo(YmxCustomerInformationInfoVo infoVo) {
        int result = 0;
        YmxCustomerInformationInfo informationInfo = new YmxCustomerInformationInfo();
        String uuid = StringUtils.getUUID();
        informationInfo.setCustomerInformationId(uuid);
        informationInfo.setCustomerName(infoVo.getCustomerName());
        informationInfo.setCustomerEmail(infoVo.getCustomerEmail());
        informationInfo.setCustomerFacebookId(infoVo.getCustomerFacebookId());
        result = landingPageMapper.insertCustomerInformationInfo(informationInfo);
        if (result == 0) {
            throw new CustomException("录入客户个人信息出错，请联系管理员！");
        }
        YmxOrderInfoVO orderInfoVO = new YmxOrderInfoVO();
        orderInfoVO.setCustomerInformationId(uuid);
        orderInfoVO.setOrderId(infoVo.getOrderId());
        result = landingPageMapper.updateOrderId(orderInfoVO);
        if (result == 0) {
            throw new CustomException("更新订单表客户信息出错，请联系管理员！");
        }
        return result;
    }

    /**
     * 礼物列表查询
     * @param giftInfoVO
     * @return
     */
    @Override
    public List<YmxGiftInfo> selectGiftInfo(YmxOrderGiftInfoVO giftInfoVO) {
        List<YmxGiftInfo> giftInfoList = landingPageMapper.selectGiftInfo(giftInfoVO.getAsin());
        return giftInfoList;
    }

    /**
     * 记录客户所选礼物
     * @param giftInfoVO
     * @return
     */
    @Override
    public int recordCustomerGifts(YmxCustomerGiftInfoVO giftInfoVO) {
        int result = 0;
        String uuid = StringUtils.getUUID();
        giftInfoVO.setCustomerGiftId(uuid);
        result = landingPageMapper.recordCustomerGifts(giftInfoVO);
        if (result == 0) {
            throw new CustomException("记录客户所选礼物出错，请联系管理员！");
        }
        return result;
    }

    /**
     * 记录客户所选星级或评论
     * @param commentInfoVO
     * @return
     */
    @Override
    public int recordCustomerStarRating(YmxCommentInfoVO commentInfoVO) {
        int result = 0;
        String editCategory = commentInfoVO.getEditCategory();
        if (!StringUtils.isEmpty(editCategory)) {
            if (editCategory.equals("1")) {
                String uuid = StringUtils.getUUID();
                commentInfoVO.setCommentId(uuid);
                result = landingPageMapper.recordCustomerStarRating(commentInfoVO);
            } else {
                result = landingPageMapper.updateCustomerStarRating(commentInfoVO);
            }
        }
        if (result == 0) {
            throw new CustomException("记录客户所选星级或评论出错，请联系管理员！");
        }
        return result;
    }
}
