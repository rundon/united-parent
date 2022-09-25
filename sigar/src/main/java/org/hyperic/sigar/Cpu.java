package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cpu implements Serializable {
   private static final long serialVersionUID = 8076L;
   long user = 0L;
   long sys = 0L;
   long nice = 0L;
   long idle = 0L;
   long wait = 0L;
   long irq = 0L;
   long softIrq = 0L;
   long stolen = 0L;
   long total = 0L;

   public native void gather(Sigar var1) throws SigarException;

   static Cpu fetch(Sigar sigar) throws SigarException {
      Cpu cpu = new Cpu();
      cpu.gather(sigar);
      return cpu;
   }

   public long getUser() {
      return this.user;
   }

   public long getSys() {
      return this.sys;
   }

   public long getNice() {
      return this.nice;
   }

   public long getIdle() {
      return this.idle;
   }

   public long getWait() {
      return this.wait;
   }

   public long getIrq() {
      return this.irq;
   }

   public long getSoftIrq() {
      return this.softIrq;
   }

   public long getStolen() {
      return this.stolen;
   }

   public long getTotal() {
      return this.total;
   }

   void copyTo(Cpu copy) {
      copy.user = this.user;
      copy.sys = this.sys;
      copy.nice = this.nice;
      copy.idle = this.idle;
      copy.wait = this.wait;
      copy.irq = this.irq;
      copy.softIrq = this.softIrq;
      copy.stolen = this.stolen;
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

      String strnice = String.valueOf(this.nice);
      if (!"-1".equals(strnice)) {
         map.put("Nice", strnice);
      }

      String stridle = String.valueOf(this.idle);
      if (!"-1".equals(stridle)) {
         map.put("Idle", stridle);
      }

      String strwait = String.valueOf(this.wait);
      if (!"-1".equals(strwait)) {
         map.put("Wait", strwait);
      }

      String strirq = String.valueOf(this.irq);
      if (!"-1".equals(strirq)) {
         map.put("Irq", strirq);
      }

      String strsoftIrq = String.valueOf(this.softIrq);
      if (!"-1".equals(strsoftIrq)) {
         map.put("SoftIrq", strsoftIrq);
      }

      String strstolen = String.valueOf(this.stolen);
      if (!"-1".equals(strstolen)) {
         map.put("Stolen", strstolen);
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
