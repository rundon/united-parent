package org.hyperic.sigar.util;

import com.onefly.united.common.utils.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hyperic.sigar.*;
import org.hyperic.sigar.dto.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LicenceUtil {
    /**
     * cpu 硬盘 内存 Hwaddr
     *
     * @return
     * @throws SigarException
     */
    public static String getLicenceKey(Sigar sigar) throws SigarException {
        StringBuilder sb = new StringBuilder();
        //cpu 信息
        CpusDto cpus = cpu(sigar);
        sb.append(cpus.getNum());
        CpuDto cpu = null;
        if (cpus.getCpuVos() != null && cpus.getCpuVos().size() > 1) cpu = cpus.getCpuVos().get(0);
        sb.append(cpu.getVendor());
        sb.append(cpu.getModel());
        sb.append(cpu.getMhz());
        sb.append(cpu.getCache_size());
        //内存信息
        MemoryDto m = memory(sigar);
        sb.append(m.getMem_total());
        //磁盘信息
        FilesDto files = file(sigar);
        sb.append(files.getTotal_number());
        //以太网口
        EthernetsDto eths = ethernet(sigar);
        sb.append(eths.getNum());
        for (int i = 0; i < eths.getEthernetVos().size(); i++) {
            EthernetDto e = eths.getEthernetVos().get(i);
            sb.append(e.getHwaddr());
        }
        return EncryptUtil.getInstance().SHA12HexStr(sb.toString());
    }

    /**
     * cpu信息
     *
     * @return
     * @throws SigarException
     */
    private static CpusDto cpu(Sigar sigar) throws SigarException {
        CpusDto cpusVo = new CpusDto();
        CpuInfo infos[] = sigar.getCpuInfoList();
        CpuPerc cpuList[] = null;
        cpuList = sigar.getCpuPercList();
        cpusVo.setNum(infos.length);
        List<CpuDto> cpuVos = new ArrayList<CpuDto>();
        for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
            CpuInfo info = infos[i];
            CpuDto cpuVo = new CpuDto();
            cpuVo.setIndex(i);//第几块cpu信息
            cpuVo.setMhz(info.getMhz() + "");//CPU的总量MHz
            cpuVo.setVendor(info.getVendor() + "");//CPU生产商 获得CPU的卖主，如：Intel
            cpuVo.setModel(info.getModel() + "");//获得CPU的类别，如：Celeron
            cpuVo.setCache_size(info.getCacheSize() + "");//CPU缓存数量 缓冲存储器数量
            CpuPercDto cpuPercVo = getCpuPerc(cpuList[i]);//获取cpu当前状况
            cpuVo.setCpuPercVo(cpuPercVo);
            cpuVos.add(cpuVo);
        }
        cpusVo.setCpuVos(cpuVos);
        return cpusVo;
    }

    private static CpuPercDto getCpuPerc(CpuPerc cpu) {
        CpuPercDto cpuPercVo = new CpuPercDto();
        cpuPercVo.setUser(CpuPerc.format(cpu.getUser()) + "");//CPU用户使用率
        cpuPercVo.setSys(CpuPerc.format(cpu.getSys()) + "");//CPU系统使用率
        cpuPercVo.setWait(CpuPerc.format(cpu.getWait()) + "");//CPU当前等待率
        cpuPercVo.setNice(CpuPerc.format(cpu.getNice()) + "");//CPU当前错误率
        cpuPercVo.setIdle(CpuPerc.format(cpu.getIdle()) + "");//CPU当前空闲率
        cpuPercVo.setCombined(CpuPerc.format(cpu.getCombined()) + "");//CPU总的使用率
        return cpuPercVo;
    }

    private static MemoryDto memory(Sigar sigar) throws SigarException {
        MemoryDto memoryVo = new MemoryDto();
        Mem mem = sigar.getMem();
        // 内存总量
        memoryVo.setMem_total(stringToLong(mem.getTotal() + ""));//内存总量
        // 当前内存使用量
        memoryVo.setMem_used(stringToLong(mem.getUsed() + ""));//当前内存使用量
        // 当前内存剩余量
        memoryVo.setMem_free(stringToLong(mem.getFree() + ""));//当前内存剩余量
        Swap swap = sigar.getSwap();
        // 交换区总量
        memoryVo.setSwap_total(stringToLong(swap.getTotal() + ""));//交换区总量
        // 当前交换区使用量
        memoryVo.setSwap_used(stringToLong(swap.getUsed() + ""));//当前交换区使用量
        // 当前交换区剩余量
        memoryVo.setSwap_free(stringToLong(swap.getFree() + ""));//当前交换区剩余量
        return memoryVo;
    }

    private static FilesDto file(Sigar sigar) throws SigarException {
        FileSystem fslist[] = sigar.getFileSystemList();
        FilesDto filesVo = new FilesDto();
        List<FileDto> fileVos = new ArrayList<FileDto>();
        filesVo.setNum(fslist.length);
        long total_number = 0;
        for (int i = 0; i < fslist.length; i++) {
            FileDto fileVo = new FileDto();
            fileVo.setIndex(i);//分区的盘符名称 磁盘编号
            FileSystem fs = fslist[i];
            // 分区的盘符名称
            fileVo.setDev_name(fs.getDevName() + "");//盘符名称
            // 分区的盘符名称
            fileVo.setDir_name(fs.getDirName() + "");//盘符路径
            fileVo.setFlags(fs.getFlags() + "");//盘符标志
            // 文件系统类型，比如 FAT32、NTFS
            fileVo.setSys_type_name(fs.getSysTypeName() + "");//盘符类型
            // 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
            fileVo.setType_name(fs.getTypeName() + "");//盘符类型名
            // 文件系统类型
            fileVo.setType(fs.getType() + "");//盘符文件系统类型
            FileSystemUsage usage = null;
            try {
                usage = sigar.getFileSystemUsage(fs.getDirName());
                switch (fs.getType()) {
                    case 0: // TYPE_UNKNOWN ：未知
                        break;
                    case 1: // TYPE_NONE
                        break;
                    case 2: // TYPE_LOCAL_DISK : 本地硬盘
                        // 文件系统总大小
                        long total = stringToLong(usage.getTotal() + "");
                        total_number += total;
                        fileVo.setTotal(total);//总大小 kb
                        // 文件系统剩余大小
                        fileVo.setFree(stringToLong(usage.getFree() + ""));//剩余大小 KB
                        // 文件系统可用大小
                        fileVo.setAvail(stringToLong(usage.getAvail() + ""));//可用大小 KB
                        // 文件系统已经使用量
                        fileVo.setUsed(stringToLong(usage.getUsed() + ""));//已经使用量 kb
                        // 文件系统资源的利用率
                        double usePercent = usage.getUsePercent() * 100D;
                        fileVo.setUse_percent(+usePercent + "%");//资源的利用率
                        break;
                    case 3:// TYPE_NETWORK ：网络
                        break;
                    case 4:// TYPE_RAM_DISK ：闪存
                        break;
                    case 5:// TYPE_CDROM ：光驱
                        break;
                    case 6:// TYPE_SWAP ：页面交换
                        break;
                }
                fileVo.setDisk_reads(stringToLong(usage.getDiskReads() + ""));//读出
                fileVo.setDisk_writes(stringToLong(usage.getDiskWrites() + ""));//写入
            } catch (SigarException e) {
                // 文件系统总大小
                long total = 0;
                total_number += 0;
                fileVo.setTotal(total);//总大小 kb
                // 文件系统剩余大小
                fileVo.setFree(0);//剩余大小 KB
                // 文件系统可用大小
                fileVo.setAvail(0);//可用大小 KB
                // 文件系统已经使用量
                fileVo.setUsed(0);//已经使用量 kb
                // 文件系统资源的利用率
                fileVo.setUse_percent(0 + "%");//资源的利用率
                fileVo.setDisk_reads(0);//读出
                fileVo.setDisk_writes(0);//写入
            }
            fileVos.add(fileVo);
        }
        filesVo.setTotal_number(total_number);
        filesVo.setFileVos(fileVos);
        return filesVo;
    }

    private static EthernetsDto ethernet(Sigar sigar) throws SigarException {
        EthernetsDto ethernetsVo = new EthernetsDto();
        String[] ifaces = sigar.getNetInterfaceList();
        ethernetsVo.setNum(ifaces.length);
        List<EthernetDto> ethernetVos = new ArrayList<EthernetDto>();
        for (int i = 0; i < ifaces.length; i++) {
            NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
            EthernetDto ethernetVo = new EthernetDto();
            ethernetVo.setIndex(i);
            if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
                    || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
                //log.warn("!IFF_UP...skipping getEthernetInfo");
                continue;
            }
            ethernetVo.setAddress(cfg.getAddress() + "");//IP地址
            ethernetVo.setBroadcast(cfg.getBroadcast() + "");//网关广播地址
            ethernetVo.setHwaddr(cfg.getHwaddr() + "");//网卡MAC地址
            ethernetVo.setNetmask(cfg.getNetmask() + "");//子网掩码
            ethernetVo.setDescription(cfg.getDescription() + "");//网卡描述信息
            ethernetVo.setType(cfg.getType() + "");//网卡类型
            ethernetVos.add(ethernetVo);
        }
        ethernetsVo.setEthernetVos(ethernetVos);
        return ethernetsVo;
    }

    private static long stringToLong(String str) throws ClassCastException {
        if (StringUtils.isEmpty(str)) return 0l;
        return Long.parseLong(str);
    }
}
