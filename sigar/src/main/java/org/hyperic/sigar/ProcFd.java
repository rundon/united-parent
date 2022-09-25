package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProcFd implements Serializable {
   private static final long serialVersionUID = 948L;
   long total = 0L;

   public native void gather(Sigar var1, long var2) throws SigarException;

   static ProcFd fetch(Sigar sigar, long pid) throws SigarException {
      ProcFd procFd = new ProcFd();
      procFd.gather(sigar, pid);
      return procFd;
   }

   public long getTotal() {
      return this.total;
   }

   void copyTo(ProcFd copy) {
      copy.total = this.total;
   }

   public Map toMap() {
      Map map = new HashMap();
      String strtotal = String.valueOf(this.total);
      if (!"-1".equals(strtotal)) {
         map.put("Total", strtotal);
      }

      return map;
   }

   public String toString() {
      return this.toMap().toString();
   }
}
