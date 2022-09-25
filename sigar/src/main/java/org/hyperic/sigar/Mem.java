package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Mem implements Serializable {
   private static final long serialVersionUID = 10181L;
   long total = 0L;
   long ram = 0L;
   long used = 0L;
   long free = 0L;
   long actualUsed = 0L;
   long actualFree = 0L;
   double usedPercent = 0.0D;
   double freePercent = 0.0D;

   public native void gather(Sigar var1) throws SigarException;

   static Mem fetch(Sigar sigar) throws SigarException {
      Mem mem = new Mem();
      mem.gather(sigar);
      return mem;
   }

   public long getTotal() {
      return this.total;
   }

   public long getRam() {
      return this.ram;
   }

   public long getUsed() {
      return this.used;
   }

   public long getFree() {
      return this.free;
   }

   public long getActualUsed() {
      return this.actualUsed;
   }

   public long getActualFree() {
      return this.actualFree;
   }

   public double getUsedPercent() {
      return this.usedPercent;
   }

   public double getFreePercent() {
      return this.freePercent;
   }

   void copyTo(Mem copy) {
      copy.total = this.total;
      copy.ram = this.ram;
      copy.used = this.used;
      copy.free = this.free;
      copy.actualUsed = this.actualUsed;
      copy.actualFree = this.actualFree;
      copy.usedPercent = this.usedPercent;
      copy.freePercent = this.freePercent;
   }

   public String toString() {
      return "Mem: " + this.total / 1024L + "K av, " + this.used / 1024L + "K used, " + this.free / 1024L + "K free";
   }

   public Map toMap() {
      Map map = new HashMap();
      String strtotal = String.valueOf(this.total);
      if (!"-1".equals(strtotal)) {
         map.put("Total", strtotal);
      }

      String strram = String.valueOf(this.ram);
      if (!"-1".equals(strram)) {
         map.put("Ram", strram);
      }

      String strused = String.valueOf(this.used);
      if (!"-1".equals(strused)) {
         map.put("Used", strused);
      }

      String strfree = String.valueOf(this.free);
      if (!"-1".equals(strfree)) {
         map.put("Free", strfree);
      }

      String stractualUsed = String.valueOf(this.actualUsed);
      if (!"-1".equals(stractualUsed)) {
         map.put("ActualUsed", stractualUsed);
      }

      String stractualFree = String.valueOf(this.actualFree);
      if (!"-1".equals(stractualFree)) {
         map.put("ActualFree", stractualFree);
      }

      String strusedPercent = String.valueOf(this.usedPercent);
      if (!"-1".equals(strusedPercent)) {
         map.put("UsedPercent", strusedPercent);
      }

      String strfreePercent = String.valueOf(this.freePercent);
      if (!"-1".equals(strfreePercent)) {
         map.put("FreePercent", strfreePercent);
      }

      return map;
   }
}
