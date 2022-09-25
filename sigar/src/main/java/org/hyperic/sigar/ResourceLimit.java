package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResourceLimit implements Serializable {
   private static final long serialVersionUID = 32184L;
   long cpuCur = 0L;
   long cpuMax = 0L;
   long fileSizeCur = 0L;
   long fileSizeMax = 0L;
   long pipeSizeMax = 0L;
   long pipeSizeCur = 0L;
   long dataCur = 0L;
   long dataMax = 0L;
   long stackCur = 0L;
   long stackMax = 0L;
   long coreCur = 0L;
   long coreMax = 0L;
   long memoryCur = 0L;
   long memoryMax = 0L;
   long processesCur = 0L;
   long processesMax = 0L;
   long openFilesCur = 0L;
   long openFilesMax = 0L;
   long virtualMemoryCur = 0L;
   long virtualMemoryMax = 0L;

   public native void gather(Sigar var1) throws SigarException;

   static ResourceLimit fetch(Sigar sigar) throws SigarException {
      ResourceLimit resourceLimit = new ResourceLimit();
      resourceLimit.gather(sigar);
      return resourceLimit;
   }

   public long getCpuCur() {
      return this.cpuCur;
   }

   public long getCpuMax() {
      return this.cpuMax;
   }

   public long getFileSizeCur() {
      return this.fileSizeCur;
   }

   public long getFileSizeMax() {
      return this.fileSizeMax;
   }

   public long getPipeSizeMax() {
      return this.pipeSizeMax;
   }

   public long getPipeSizeCur() {
      return this.pipeSizeCur;
   }

   public long getDataCur() {
      return this.dataCur;
   }

   public long getDataMax() {
      return this.dataMax;
   }

   public long getStackCur() {
      return this.stackCur;
   }

   public long getStackMax() {
      return this.stackMax;
   }

   public long getCoreCur() {
      return this.coreCur;
   }

   public long getCoreMax() {
      return this.coreMax;
   }

   public long getMemoryCur() {
      return this.memoryCur;
   }

   public long getMemoryMax() {
      return this.memoryMax;
   }

   public long getProcessesCur() {
      return this.processesCur;
   }

   public long getProcessesMax() {
      return this.processesMax;
   }

   public long getOpenFilesCur() {
      return this.openFilesCur;
   }

   public long getOpenFilesMax() {
      return this.openFilesMax;
   }

   public long getVirtualMemoryCur() {
      return this.virtualMemoryCur;
   }

   public long getVirtualMemoryMax() {
      return this.virtualMemoryMax;
   }

   void copyTo(ResourceLimit copy) {
      copy.cpuCur = this.cpuCur;
      copy.cpuMax = this.cpuMax;
      copy.fileSizeCur = this.fileSizeCur;
      copy.fileSizeMax = this.fileSizeMax;
      copy.pipeSizeMax = this.pipeSizeMax;
      copy.pipeSizeCur = this.pipeSizeCur;
      copy.dataCur = this.dataCur;
      copy.dataMax = this.dataMax;
      copy.stackCur = this.stackCur;
      copy.stackMax = this.stackMax;
      copy.coreCur = this.coreCur;
      copy.coreMax = this.coreMax;
      copy.memoryCur = this.memoryCur;
      copy.memoryMax = this.memoryMax;
      copy.processesCur = this.processesCur;
      copy.processesMax = this.processesMax;
      copy.openFilesCur = this.openFilesCur;
      copy.openFilesMax = this.openFilesMax;
      copy.virtualMemoryCur = this.virtualMemoryCur;
      copy.virtualMemoryMax = this.virtualMemoryMax;
   }

   public static native long INFINITY();

   public Map toMap() {
      Map map = new HashMap();
      String strcpuCur = String.valueOf(this.cpuCur);
      if (!"-1".equals(strcpuCur)) {
         map.put("CpuCur", strcpuCur);
      }

      String strcpuMax = String.valueOf(this.cpuMax);
      if (!"-1".equals(strcpuMax)) {
         map.put("CpuMax", strcpuMax);
      }

      String strfileSizeCur = String.valueOf(this.fileSizeCur);
      if (!"-1".equals(strfileSizeCur)) {
         map.put("FileSizeCur", strfileSizeCur);
      }

      String strfileSizeMax = String.valueOf(this.fileSizeMax);
      if (!"-1".equals(strfileSizeMax)) {
         map.put("FileSizeMax", strfileSizeMax);
      }

      String strpipeSizeMax = String.valueOf(this.pipeSizeMax);
      if (!"-1".equals(strpipeSizeMax)) {
         map.put("PipeSizeMax", strpipeSizeMax);
      }

      String strpipeSizeCur = String.valueOf(this.pipeSizeCur);
      if (!"-1".equals(strpipeSizeCur)) {
         map.put("PipeSizeCur", strpipeSizeCur);
      }

      String strdataCur = String.valueOf(this.dataCur);
      if (!"-1".equals(strdataCur)) {
         map.put("DataCur", strdataCur);
      }

      String strdataMax = String.valueOf(this.dataMax);
      if (!"-1".equals(strdataMax)) {
         map.put("DataMax", strdataMax);
      }

      String strstackCur = String.valueOf(this.stackCur);
      if (!"-1".equals(strstackCur)) {
         map.put("StackCur", strstackCur);
      }

      String strstackMax = String.valueOf(this.stackMax);
      if (!"-1".equals(strstackMax)) {
         map.put("StackMax", strstackMax);
      }

      String strcoreCur = String.valueOf(this.coreCur);
      if (!"-1".equals(strcoreCur)) {
         map.put("CoreCur", strcoreCur);
      }

      String strcoreMax = String.valueOf(this.coreMax);
      if (!"-1".equals(strcoreMax)) {
         map.put("CoreMax", strcoreMax);
      }

      String strmemoryCur = String.valueOf(this.memoryCur);
      if (!"-1".equals(strmemoryCur)) {
         map.put("MemoryCur", strmemoryCur);
      }

      String strmemoryMax = String.valueOf(this.memoryMax);
      if (!"-1".equals(strmemoryMax)) {
         map.put("MemoryMax", strmemoryMax);
      }

      String strprocessesCur = String.valueOf(this.processesCur);
      if (!"-1".equals(strprocessesCur)) {
         map.put("ProcessesCur", strprocessesCur);
      }

      String strprocessesMax = String.valueOf(this.processesMax);
      if (!"-1".equals(strprocessesMax)) {
         map.put("ProcessesMax", strprocessesMax);
      }

      String stropenFilesCur = String.valueOf(this.openFilesCur);
      if (!"-1".equals(stropenFilesCur)) {
         map.put("OpenFilesCur", stropenFilesCur);
      }

      String stropenFilesMax = String.valueOf(this.openFilesMax);
      if (!"-1".equals(stropenFilesMax)) {
         map.put("OpenFilesMax", stropenFilesMax);
      }

      String strvirtualMemoryCur = String.valueOf(this.virtualMemoryCur);
      if (!"-1".equals(strvirtualMemoryCur)) {
         map.put("VirtualMemoryCur", strvirtualMemoryCur);
      }

      String strvirtualMemoryMax = String.valueOf(this.virtualMemoryMax);
      if (!"-1".equals(strvirtualMemoryMax)) {
         map.put("VirtualMemoryMax", strvirtualMemoryMax);
      }

      return map;
   }

   public String toString() {
      return this.toMap().toString();
   }
}
