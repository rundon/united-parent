package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Uptime implements Serializable {
   private static final long serialVersionUID = 1263L;
   double uptime = 0.0D;

   public native void gather(Sigar var1) throws SigarException;

   static Uptime fetch(Sigar sigar) throws SigarException {
      Uptime uptime = new Uptime();
      uptime.gather(sigar);
      return uptime;
   }

   public double getUptime() {
      return this.uptime;
   }

   void copyTo(Uptime copy) {
      copy.uptime = this.uptime;
   }

   public Map toMap() {
      Map map = new HashMap();
      String struptime = String.valueOf(this.uptime);
      if (!"-1".equals(struptime)) {
         map.put("Uptime", struptime);
      }

      return map;
   }

   public String toString() {
      return this.toMap().toString();
   }
}
