package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NetInterfaceStat implements Serializable {
   private static final long serialVersionUID = 20008L;
   long rxBytes = 0L;
   long rxPackets = 0L;
   long rxErrors = 0L;
   long rxDropped = 0L;
   long rxOverruns = 0L;
   long rxFrame = 0L;
   long txBytes = 0L;
   long txPackets = 0L;
   long txErrors = 0L;
   long txDropped = 0L;
   long txOverruns = 0L;
   long txCollisions = 0L;
   long txCarrier = 0L;
   long speed = 0L;

   public native void gather(Sigar var1, String var2) throws SigarException;

   static NetInterfaceStat fetch(Sigar sigar, String name) throws SigarException {
      NetInterfaceStat netInterfaceStat = new NetInterfaceStat();
      netInterfaceStat.gather(sigar, name);
      return netInterfaceStat;
   }

   public long getRxBytes() {
      return this.rxBytes;
   }

   public long getRxPackets() {
      return this.rxPackets;
   }

   public long getRxErrors() {
      return this.rxErrors;
   }

   public long getRxDropped() {
      return this.rxDropped;
   }

   public long getRxOverruns() {
      return this.rxOverruns;
   }

   public long getRxFrame() {
      return this.rxFrame;
   }

   public long getTxBytes() {
      return this.txBytes;
   }

   public long getTxPackets() {
      return this.txPackets;
   }

   public long getTxErrors() {
      return this.txErrors;
   }

   public long getTxDropped() {
      return this.txDropped;
   }

   public long getTxOverruns() {
      return this.txOverruns;
   }

   public long getTxCollisions() {
      return this.txCollisions;
   }

   public long getTxCarrier() {
      return this.txCarrier;
   }

   public long getSpeed() {
      return this.speed;
   }

   void copyTo(NetInterfaceStat copy) {
      copy.rxBytes = this.rxBytes;
      copy.rxPackets = this.rxPackets;
      copy.rxErrors = this.rxErrors;
      copy.rxDropped = this.rxDropped;
      copy.rxOverruns = this.rxOverruns;
      copy.rxFrame = this.rxFrame;
      copy.txBytes = this.txBytes;
      copy.txPackets = this.txPackets;
      copy.txErrors = this.txErrors;
      copy.txDropped = this.txDropped;
      copy.txOverruns = this.txOverruns;
      copy.txCollisions = this.txCollisions;
      copy.txCarrier = this.txCarrier;
      copy.speed = this.speed;
   }

   public Map toMap() {
      Map map = new HashMap();
      String strrxBytes = String.valueOf(this.rxBytes);
      if (!"-1".equals(strrxBytes)) {
         map.put("RxBytes", strrxBytes);
      }

      String strrxPackets = String.valueOf(this.rxPackets);
      if (!"-1".equals(strrxPackets)) {
         map.put("RxPackets", strrxPackets);
      }

      String strrxErrors = String.valueOf(this.rxErrors);
      if (!"-1".equals(strrxErrors)) {
         map.put("RxErrors", strrxErrors);
      }

      String strrxDropped = String.valueOf(this.rxDropped);
      if (!"-1".equals(strrxDropped)) {
         map.put("RxDropped", strrxDropped);
      }

      String strrxOverruns = String.valueOf(this.rxOverruns);
      if (!"-1".equals(strrxOverruns)) {
         map.put("RxOverruns", strrxOverruns);
      }

      String strrxFrame = String.valueOf(this.rxFrame);
      if (!"-1".equals(strrxFrame)) {
         map.put("RxFrame", strrxFrame);
      }

      String strtxBytes = String.valueOf(this.txBytes);
      if (!"-1".equals(strtxBytes)) {
         map.put("TxBytes", strtxBytes);
      }

      String strtxPackets = String.valueOf(this.txPackets);
      if (!"-1".equals(strtxPackets)) {
         map.put("TxPackets", strtxPackets);
      }

      String strtxErrors = String.valueOf(this.txErrors);
      if (!"-1".equals(strtxErrors)) {
         map.put("TxErrors", strtxErrors);
      }

      String strtxDropped = String.valueOf(this.txDropped);
      if (!"-1".equals(strtxDropped)) {
         map.put("TxDropped", strtxDropped);
      }

      String strtxOverruns = String.valueOf(this.txOverruns);
      if (!"-1".equals(strtxOverruns)) {
         map.put("TxOverruns", strtxOverruns);
      }

      String strtxCollisions = String.valueOf(this.txCollisions);
      if (!"-1".equals(strtxCollisions)) {
         map.put("TxCollisions", strtxCollisions);
      }

      String strtxCarrier = String.valueOf(this.txCarrier);
      if (!"-1".equals(strtxCarrier)) {
         map.put("TxCarrier", strtxCarrier);
      }

      String strspeed = String.valueOf(this.speed);
      if (!"-1".equals(strspeed)) {
         map.put("Speed", strspeed);
      }

      return map;
   }

   public String toString() {
      return this.toMap().toString();
   }
}
