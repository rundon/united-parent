package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Swap implements Serializable {
   private static final long serialVersionUID = 4974L;
   long total = 0L;
   long used = 0L;
   long free = 0L;
   long pageIn = 0L;
   long pageOut = 0L;

   public native void gather(Sigar var1) throws SigarException;

   static Swap fetch(Sigar sigar) throws SigarException {
      Swap swap = new Swap();
      swap.gather(sigar);
      return swap;
   }

   public long getTotal() {
      return this.total;
   }

   public long getUsed() {
      return this.used;
   }

   public long getFree() {
      return this.free;
   }

   public long getPageIn() {
      return this.pageIn;
   }

   public long getPageOut() {
      return this.pageOut;
   }

   void copyTo(Swap copy) {
      copy.total = this.total;
      copy.used = this.used;
      copy.free = this.free;
      copy.pageIn = this.pageIn;
      copy.pageOut = this.pageOut;
   }

   public String toString() {
      return "Swap: " + this.total / 1024L + "K av, " + this.used / 1024L + "K used, " + this.free / 1024L + "K free";
   }

   public Map toMap() {
      Map map = new HashMap();
      String strtotal = String.valueOf(this.total);
      if (!"-1".equals(strtotal)) {
         map.put("Total", strtotal);
      }

      String strused = String.valueOf(this.used);
      if (!"-1".equals(strused)) {
         map.put("Used", strused);
      }

      String strfree = String.valueOf(this.free);
      if (!"-1".equals(strfree)) {
         map.put("Free", strfree);
      }

      String strpageIn = String.valueOf(this.pageIn);
      if (!"-1".equals(strpageIn)) {
         map.put("PageIn", strpageIn);
      }

      String strpageOut = String.valueOf(this.pageOut);
      if (!"-1".equals(strpageOut)) {
         map.put("PageOut", strpageOut);
      }

      return map;
   }
}
