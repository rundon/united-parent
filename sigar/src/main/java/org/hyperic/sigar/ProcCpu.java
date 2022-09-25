package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProcCpu implements Serializable {
   private static final long serialVersionUID = 6748L;
   double percent = 0.0D;
   long lastTime = 0L;
   long startTime = 0L;
   long user = 0L;
   long sys = 0L;
   long total = 0L;

   public native void gather(Sigar var1, long var2) throws SigarException;

   static ProcCpu fetch(Sigar sigar, long pid) throws SigarException {
      ProcCpu procCpu = new ProcCpu();
      procCpu.gather(sigar, pid);
      return procCpu;
   }

   public double getPercent() {
      return this.percent;
   }

   public long getLastTime() {
      return this.lastTime;
   }

   public long getStartTime() {
      return this.startTime;
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

   void copyTo(ProcCpu copy) {
      copy.percent = this.percent;
      copy.lastTime = this.lastTime;
      copy.startTime = this.startTime;
      copy.user = this.user;
      copy.sys = this.sys;
      copy.total = this.total;
   }

   public Map toMap() {
      Map map = new HashMap();
      String strpercent = String.valueOf(this.percent);
      if (!"-1".equals(strpercent)) {
         map.put("Percent", strpercent);
      }

      String strlastTime = String.valueOf(this.lastTime);
      if (!"-1".equals(strlastTime)) {
         map.put("LastTime", strlastTime);
      }

      String strstartTime = String.valueOf(this.startTime);
      if (!"-1".equals(strstartTime)) {
         map.put("StartTime", strstartTime);
      }

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
