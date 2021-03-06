package com.wuyinlei.bean.invesator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 若兰 on 2016/2/20.
 */
public class InvesatorData implements Serializable{
    //1.投资的公司
    private List<InvestCom> investCom;
    //2.投资的数量
    private String investComCount;
    //3.投资的轮数
    private String[] investPhases;
    //4.用户信息
    private UserModel user;
    public InvesatorData() {
        super();
    }
    public InvesatorData(List<InvestCom> investCom, String investComCount,
                      String[] investPhases, UserModel user) {
        super();
        this.investCom = investCom;
        this.investComCount = investComCount;
        this.investPhases = investPhases;
        this.user = user;
    }
    public List<InvestCom> getInvestCom() {
        return investCom;
    }
    public void setInvestCom(List<InvestCom> investCom) {
        this.investCom = investCom;
    }
    public String getInvestComCount() {
        return investComCount;
    }
    public void setInvestComCount(String investComCount) {
        this.investComCount = investComCount;
    }
    public String[] getInvestPhases() {
        return investPhases;
    }
    public void setInvestPhases(String[] investPhases) {
        this.investPhases = investPhases;
    }
    public UserModel getUser() {
        return user;
    }
    public void setUser(UserModel user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return "InvestData [investCom=" + investCom + ", investComCount="
                + investComCount + ", investPhases="
                + Arrays.toString(investPhases) + ", user=" + user + "]";
    }
}
