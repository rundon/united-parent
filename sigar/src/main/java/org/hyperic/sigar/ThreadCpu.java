package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ThreadCpu implements Serializable {
   private static final long serialVersionUID = 2546L;
   long user = 0L;
   long sys = 0L;
   long total = 0L;

   public native void gather(Sigar var1, long var2) throws SigarException;

   static ThreadCpu fetch(Sigar sigar, long pid) throws SigarException {
      ThreadCpu threadCpu = new ThreadCpu();
      threadCpu.gather(sigar, pid);
      return threadCpu;
   }

   public long getUser() {
      return this.user;
   }

   public long getSys() {
      return this.sys;
   }

   public long getTotal() {
      return this.total;
   }

   void copyTo(ThreadCpu copy) {
      copy.user = this.user;
      copy.sys = this.sys;
      copy.total = this.total;
   }

   public Map toMap() {
      Map map = new HashMap();
      String struser = String.valueOf(this.user);
      if (!"-1".equals(struser)) {
         map.put("User", struser);
      }

      String strsys = String.valueOf(this.sys);
      if (!"-1".equals(strsys)) {
         map.put("Sys", strsys);
      }

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
