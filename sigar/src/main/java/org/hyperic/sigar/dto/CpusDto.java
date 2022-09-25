package org.hyperic.sigar.dto;

import java.util.List;

/**
 * Created by Jerry on 15/12/14.
 */
public class CpusDto {
    private int num; //cpu 个数
    private List<CpuDto> cpuVos; // cpu具体信息；

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<CpuDto> getCpuVos() {
        return cpuVos;
    }

    public void setCpuVos(List<CpuDto> cpuVos) {
        this.cpuVos = cpuVos;
    }

    @Override
    public String toString() {
        return "CpusVo{" +
                "num=" + num +
                ", cpuVos=" + cpuVos +
                '}';
    }
}
