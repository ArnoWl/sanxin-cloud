package com.sanxin.cloud.service.system.pay;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.entity.CAccount;
import com.sanxin.cloud.entity.CHourDetail;
import com.sanxin.cloud.entity.CMoneyDetail;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.CAccountMapper;
import com.sanxin.cloud.service.CAccountService;
import com.sanxin.cloud.service.CHourDetailService;
import com.sanxin.cloud.service.CMoneyDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 资金、余额操作Service
 * @author xiaoky
 * @date 2019-10-08e
 */
@Service
public class HandleAccountChangeService {
    private static Logger logger = LoggerFactory.getLogger(HandleAccountChangeService.class);
    @Autowired
    private CAccountService cAccountService;
    @Autowired
    private CAccountMapper cAccountMapper;
    @Autowired
    private CMoneyDetailService cMoneyDetailService;
    @Autowired
    private CHourDetailService cHourDetailService;

    /**
     *
     * @param detail cid typeId isOut payCode cost remark targetId
     * @return
     */
    public String insertCMoneyDetail(CMoneyDetail detail) {
        String result="";
        try {
            //行锁数据
            CAccount account= cAccountService.getByCid(detail.getCid());
            BigDecimal originalMoney = account.getMoney();
            BigDecimal money = account.getMoney();
            if(FunctionUtils.isEquals(StaticUtils.PAY_IN, detail.getIsout())){
                money=FunctionUtils.add(money, detail.getCost(), 2);
            }else{
                money=FunctionUtils.sub(money, detail.getCost(), 2);
            }
            if(money.compareTo(BigDecimal.ZERO)<0){
                return "余额不足";
            }
            account.setMoney(money);
            //首先修改余额  根据当前版本号去修改
            int i = cAccountMapper.updateLockVersion(account);
            if(i<1) {
                return "重复提交数据";
            }
            detail.setOriginal(originalMoney);
            detail.setLast(money);
            cMoneyDetailService.save(detail);
        } catch (Exception e) {
            logger.info("操作用户余额明细异常："+e.getMessage());
            throw new ThrowJsonException("保存校验用户余额异常");
        }
        return result;
    }

    public String insertCHourDetail(CHourDetail detail) {
        String result="";
        try {
            //行锁数据
            CAccount account= cAccountService.getByCid(detail.getCid());
            Integer originalHour = account.getHour();
            Integer hour = account.getHour();
            if(FunctionUtils.isEquals(StaticUtils.PAY_IN, detail.getIsout())){
                hour = hour + detail.getCost();
            }else{
                hour= hour - detail.getCost();
            }
            if(hour<0){
                return "时长不足";
            }
            account.setHour(hour);
            //首先修改余额  根据当前版本号去修改
            int i = cAccountMapper.updateLockVersion(account);
            if(i<1) {
                return "重复提交数据";
            }
            detail.setOriginal(originalHour);
            detail.setLast(hour);
            cHourDetailService.save(detail);
        } catch (Exception e) {
            logger.info("操作用户余额明细异常："+e.getMessage());
            throw new ThrowJsonException("保存校验用户余额异常");
        }
        return result;
    }
}
