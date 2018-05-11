package com.acerchem.service.customercreditaccount.impl;

import com.acerchem.core.enums.CreditAccountStatusEnum;
import com.acerchem.core.model.CreditTransactionModel;
import com.acerchem.core.model.CustomerCreditAccountModel;
import com.acerchem.service.customercreditaccount.DefaultCustomerCreditAccountService;

import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.user.daos.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("defaultCustomerCreditAccountService")
@Transactional
public class DefaultCustomerCreditAccountServiceImpl implements DefaultCustomerCreditAccountService {

    @Resource
    private UserService userService;

    @Resource
    ModelService modelService;
   
    @Resource
    private UserDao userDao;

    private static final Logger LOG = Logger.getLogger(DefaultCustomerCreditAccountServiceImpl.class);

    private static final Boolean FALSE = false;
    private static final Boolean TRUE = true;


    @Override
    public CustomerCreditAccountModel getCustomerCreditAccount() {

        CustomerModel customerModel = (CustomerModel) userService.getCurrentUser();
        if (customerModel != null) {
            return customerModel.getCreditAccount();
        }
        return null;
    }

    @Override
    public List<CreditTransactionModel> getCreditTransactionModel() {

        CustomerCreditAccountModel customerCreditAccount = this.getCustomerCreditAccount();
        if (customerCreditAccount != null) {
            return customerCreditAccount.getTransactions();
        }
        return null;
    }

    @Override
    public CustomerCreditAccountModel updateCustomerCreditAccountConsume(OrderModel orderModel,BigDecimal money) {

        if (money != null && money.compareTo(BigDecimal.ZERO) > 0) {
        	
        	CustomerModel userModel = (CustomerModel)userService.getUserForUID(orderModel.getUser().getUid());
        	if(userModel != null)
        	{
        		CustomerCreditAccountModel customerCreditAccount = userModel.getCreditAccount();
	            if (customerCreditAccount != null) {
	
	                if (customerCreditAccount.getStatus() != null && customerCreditAccount.getStatus() == CreditAccountStatusEnum.NORMAL) {
	                    BigDecimal creditTotalAmount = customerCreditAccount.getCreditTotalAmount();
	                    BigDecimal creaditRemainedAmount = customerCreditAccount.getCreaditRemainedAmount();
	
	                    if (creditTotalAmount.compareTo(BigDecimal.ZERO) > 0) {
	                        if (creaditRemainedAmount.compareTo(money) >= 0) {
	                            //更新信用账户可用额度
	                            customerCreditAccount.setCreaditRemainedAmount(creaditRemainedAmount.subtract(money));
	                            Integer billingInterval = customerCreditAccount.getBillingInterval();
	                            //新建流水
	                            CreditTransactionModel creditTransaction = (CreditTransactionModel) this.modelService.create(CreditTransactionModel.class);
	                            creditTransaction.setCreaditUsedAmount(money);
	                            creditTransaction.setCreationtime(new Date());
	                            creditTransaction.setIsPayback(FALSE);
	
	                            Calendar c = Calendar.getInstance(); 
	                    		if(orderModel.getPickupDateOfExtended()!=null)
	                            {
	                            	c.setTime(orderModel.getPickupDateOfExtended()); 
	                            }
	                            else if(orderModel.getDeliveryMode().getCode().equals("DELIVERY_MENTION"))
	                            {//自提 DELIVERY_MENTION
	                            	c.setTime(orderModel.getPickUpDate()); 
	                            }
	                            else
	                            {//送货  DELIVERY_GROSS
	                            	c.setTime(orderModel.getWaitDeliveiedDate()); 
	                            }
	                    	    c.set(Calendar.DATE,c.get(Calendar.DATE)+billingInterval); 
	                    	    Date date=c.getTime();
	                            
	                            creditTransaction.setShouldPaybackTime(date);
	                            creditTransaction.setCreditAccount(customerCreditAccount);
	                            
	                            creditTransaction.setOrderCode(orderModel.getCode());
	                            creditTransaction.setProductNumber(orderModel.getEntries().size());
	                    		
	                            this.modelService.save(creditTransaction);
	                            this.modelService.refresh(creditTransaction);
	                            this.modelService.save(customerCreditAccount);
	                            this.modelService.refresh(customerCreditAccount);
	
	                            return customerCreditAccount;
	                        } else {
	                            LOG.info("updateCustomerCreditAccountConsume CreaditAmount ERROR money=" + money + " | creaditRemainedAmount=" + creaditRemainedAmount);
	                        }
	                    } else {
	                        LOG.info("updateCustomerCreditAccountConsume CreaditAmount ERROR creditTotalAmount=" + creditTotalAmount + " | creaditRemainedAmount=" + creaditRemainedAmount);
	                    }
	                } else {
	                    LOG.info("updateCustomerCreditAccountConsume CustomerCreditAccountModel Status ERROR Status=" + customerCreditAccount.getStatus());
	                }
	            }else {
                    LOG.info("updateCustomerCreditAccountConsume CustomerModel is null");
                }
            } else {
                LOG.info("updateCustomerCreditAccountConsume CustomerCreditAccountModel is null");
            }
        } else {
            LOG.info("updateCustomerCreditAccountConsume Parameter ERROR money=" + money);
        }
        return null;
    }

    @Override
    public CustomerCreditAccountModel updateCustomerCreditAccountRepayment(CreditTransactionModel creditTransaction) {
    	
        if (creditTransaction != null) {
        	if (creditTransaction.getCreditAccount()!=null) {
	            if (creditTransaction.getCreaditUsedAmount()!=null && creditTransaction.getCreaditUsedAmount().compareTo(BigDecimal.ZERO) > 0) {
	            	//信用账户：更新信用账户可用额度
		        	CustomerCreditAccountModel customerCreditAccount=creditTransaction.getCreditAccount();
	                BigDecimal creaditRemainedAmount = customerCreditAccount.getCreaditRemainedAmount();
	                customerCreditAccount.setCreaditRemainedAmount(creaditRemainedAmount.add(creditTransaction.getCreaditUsedAmount()));
	                
	                //更新订单状态为completed
	                final String SQL = "SELECT PK FROM {"+OrderModel._TYPECODE+"} WHERE {"+OrderModel.CODE+"} =?orderCode ";
	        		final Map<String, Object> params = new HashMap<String, Object>();
	        		final StringBuilder builder = new StringBuilder(SQL);
	        		params.put("orderCode", creditTransaction.getOrderCode());
	        		final SearchResult<OrderModel> result = flexibleSearchService.search(builder.toString(),params);
	        		if(result.getResult().size()>0)
	        		{
	        			//开始更新流水
	                    if (StringUtils.isNotBlank(creditTransaction.getOrderCode()) && creditTransaction.getIsPayback()==false) {
	                        LOG.info("creditTransaction.getOrderCode()=" + creditTransaction.getOrderCode());
	                        creditTransaction.setPaybackAmount(creditTransaction.getCreaditUsedAmount());
	                        creditTransaction.setPaybackTime(new Date());
	                        creditTransaction.setIsPayback(TRUE);
	                        creditTransaction.setCreditAccount(customerCreditAccount);
	                        
	                        OrderModel order=result.getResult().get(0);
		        			order.setStatus(OrderStatus.COMPLETED);
		        			
	                        this.modelService.saveAll(creditTransaction,customerCreditAccount,order);
	                        

	                        return customerCreditAccount;
	                    }
	        		}else{
	        			LOG.info("No corresponding order found! order code = "+creditTransaction.getOrderCode());
	        		}
	            } else {
	            	LOG.info("updateCustomerCreditAccountRepayment CreaditUsedAmount = "+creditTransaction.getCreaditUsedAmount());
	            }
        	} else {
                LOG.info("updateCustomerCreditAccountRepayment Parameter ERROR CustomerCreditAccountModel is null");
            }
        } else {
            LOG.info("updateCustomerCreditAccountRepayment Parameter ERROR CreditTransaction is null");
        }
        return null;
    }
    
    @Resource
    protected FlexibleSearchService flexibleSearchService;
    
    @Override
    public CustomerCreditAccountModel updateCreditAccountRepaymentByOrder(OrderModel order){
    	
        if (order != null&&order.getCode()!=null) {
    		final String SQL = "SELECT PK FROM {"+CreditTransactionModel._TYPECODE+"} WHERE {"+CreditTransactionModel.ORDERCODE+"} =?orderCode ";
    		final Map<String, Object> params = new HashMap<String, Object>();
    		final StringBuilder builder = new StringBuilder(SQL);
    		params.put("orderCode", order.getCode());
    		final SearchResult<CreditTransactionModel> result = flexibleSearchService.search(builder.toString(),params);
    		if(result.getResult().size()>0)
    		{
    			return updateCustomerCreditAccountRepayment(result.getResult().get(0));
    		
    		}else{
    			LOG.info("updateCreditAccountRepaymentByOrder Parameter ERROR CreditTransaction is null");
    		}
        } else {
            LOG.info("updateCreditAccountRepaymentByOrder Parameter ERROR order is null");
        }
        return null;
    }


}
