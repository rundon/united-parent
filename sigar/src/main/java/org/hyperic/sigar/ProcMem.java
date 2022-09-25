package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProcMem implements Serializable {
   private static final long serialVersionUID = 7985L;
   long size = 0L;
   long resident = 0L;
   long share = 0L;
   long minorFaults = 0L;
   long majorFaults = 0L;
   long pageFaults = 0L;

   public native void gather(Sigar var1, long var2) throws SigarException;

   static ProcMem fetch(Sigar sigar, long pid) throws SigarException {
      ProcMem procMem = new ProcMem();
      procMem.gather(sigar, pid);
      return procMem;
   }

   public long getSize() {
      return this.size;
   }

   public long getResident() {
      return this.resident;
   }

   public long getShare() {
      return this.share;
   }

   public long getMinorFaults() {
      return this.minorFaults;
   }

   public long getMajorFaults() {
      return this.majorFaults;
   }

   public long getPageFaults() {
      return this.pageFaults;
   }

   void copyTo(ProcMem copy) {
      copy.size = this.size;
      copy.resident = this.resident;
      copy.share = this.share;
      copy.minorFaults = this.minorFaults;
      copy.majorFaults = this.majorFaults;
      copy.pageFaults = this.pageFaults;
   }

   /** @deprecated */
   public long getRss() {
      return this.getResident();
   }

   /** @deprecated */
   public long getVsize() {
      return this.getSize();
   }

   public Map toMap() {
      Map map = new HashMap();
      String strsize = String.valueOf(this.size);
      if (!"-1".equals(strsize)) {
         map.put("Size", strsize);
      }

      String strresident = String.valueOf(this.resident);
      if (!"-1".equals(strresident)) {
         map.put("Resident", strresident);
      }

      String strshare = String.valueOf(this.share);
      if (!"-1".equals(strshare)) {
         map.put("Share", strshare);
      }

      String strminorFaults = String.valueOf(this.minorFaults);
      if (!"-1".equals(strminorFaults)) {
         map.put("MinorFaults", strminorFaults);
      }

      String strmajorFaults = String.valueOf(this.majorFaults);
      if (!"-1".equals(strmajorFaults)) {
         map.put("MajorFaults", strmajorFaults);
      }

      String strpageFaults = String.valueOf(this.pageFaults);
      if (!"-1".equals(strpageFaults)) {
         map.put("PageFaults", strpageFaults);
      }

      return map;
   }

   public String toString() {
      return this.toMap().toString();
   }
}
