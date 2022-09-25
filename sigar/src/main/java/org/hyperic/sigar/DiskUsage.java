package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DiskUsage implements Serializable {
   private static final long serialVersionUID = 8090L;
   long reads = 0L;
   long writes = 0L;
   long readBytes = 0L;
   long writeBytes = 0L;
   double queue = 0.0D;
   double serviceTime = 0.0D;

   public native void gather(Sigar var1, String var2) throws SigarException;

   static DiskUsage fetch(Sigar sigar, String name) throws SigarException {
      DiskUsage diskUsage = new DiskUsage();
      diskUsage.gather(sigar, name);
      return diskUsage;
   }

   public long getReads() {
      return this.reads;
   }

   public long getWrites() {
      return this.writes;
   }

   public long getReadBytes() {
      return this.readBytes;
   }

   public long getWriteBytes() {
      return this.writeBytes;
   }

   public double getQueue() {
      return this.queue;
   }

   public double getServiceTime() {
      return this.serviceTime;
   }

   void copyTo(DiskUsage copy) {
      copy.reads = this.reads;
      copy.writes = this.writes;
      copy.readBytes = this.readBytes;
      copy.writeBytes = this.writeBytes;
      copy.queue = this.queue;
      copy.serviceTime = this.serviceTime;
   }

   public Map toMap() {
      Map map = new HashMap();
      String strreads = String.valueOf(this.reads);
      if (!"-1".equals(strreads)) {
         map.put("Reads", strreads);
      }

      String strwrites = String.valueOf(this.writes);
      if (!"-1".equals(strwrites)) {
         map.put("Writes", strwrites);
      }

      String strreadBytes = String.valueOf(this.readBytes);
      if (!"-1".equals(strreadBytes)) {
         map.put("ReadBytes", strreadBytes);
      }

      String strwriteBytes = String.valueOf(this.writeBytes);
      if (!"-1".equals(strwriteBytes)) {
         map.put("WriteBytes", strwriteBytes);
      }

      String strqueue = String.valueOf(this.queue);
      if (!"-1".equals(strqueue)) {
         map.put("Queue", strqueue);
      }

      String strserviceTime = String.valueOf(this.serviceTime);
      if (!"-1".equals(strserviceTime)) {
         map.put("ServiceTime", strserviceTime);
      }

      return map;
   }

   public String toString() {
      return this.toMap().toString();
   }
}
