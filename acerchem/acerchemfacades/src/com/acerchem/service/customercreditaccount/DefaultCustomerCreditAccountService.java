package com.acerchem.service.customercreditaccount;

import com.acerchem.core.model.CreditTransactionModel;
import com.acerchem.core.model.CustomerCreditAccountModel;

import java.math.BigDecimal;
import java.util.List;

public interface DefaultCustomerCreditAccountService {
    /**
     * 获取当前用户的信用账户
     * @return
     */
    CustomerCreditAccountModel getCustomerCreditAccount();

    /**
     * 获取当前用户的流水
     * @return
     */
    List<CreditTransactionModel> getCreditTransactionModel();

    /**
     * 更新信用账户方法的消费接口
     * @param money
     * @return
     */
    CustomerCreditAccountModel updateCustomerCreditAccountConsume(BigDecimal money);


    /**
     * 更新信用账户方法的每笔消费的还款接口;
     * @param money
     * @return
     */
    CustomerCreditAccountModel updateCustomerCreditAccountRepayment(String cransactionId ,BigDecimal money);




}
