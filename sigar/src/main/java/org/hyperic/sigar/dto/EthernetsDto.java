package org.hyperic.sigar.dto;

import java.util.List;

/**
 * Created by Jerry on 15/12/15.
 */
public class EthernetsDto {
    private int num;//有多少个以太网信息
    private List<EthernetDto> ethernetVos ;//以太网信息详情

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<EthernetDto> getEthernetVos() {
        return ethernetVos;
    }

    public void setEthernetVos(List<EthernetDto> ethernetVos) {
        this.ethernetVos = ethernetVos;
    }

    @Override
    public String toString() {
        return "EthernetsVo{" +
                "num=" + num +
                ", ethernetVos=" + ethernetVos +
                '}';
    }
}
