package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProcStat implements Serializable {
   private static final long serialVersionUID = 7546L;
   long total = 0L;
   long idle = 0L;
   long running = 0L;
   long sleeping = 0L;
   long stopped = 0L;
   long zombie = 0L;
   long threads = 0L;

   public native void gather(Sigar var1) throws SigarException;

   static ProcStat fetch(Sigar sigar) throws SigarException {
      ProcStat procStat = new ProcStat();
      procStat.gather(sigar);
      return procStat;
   }

   public long getTotal() {
      return this.total;
   }

   public long getIdle() {
      return this.idle;
   }

   public long getRunning() {
      return this.running;
   }

   public long getSleeping() {
      return this.sleeping;
   }

   public long getStopped() {
      return this.stopped;
   }

   public long getZombie() {
      return this.zombie;
   }

   public long getThreads() {
      return this.threads;
   }

   void copyTo(ProcStat copy) {
      copy.total = this.total;
      copy.idle = this.idle;
      copy.running = this.running;
      copy.sleeping = this.sleeping;
      copy.stopped = this.stopped;
      copy.zombie = this.zombie;
      copy.threads = this.threads;
   }

   public Map toMap() {
      Map map = new HashMap();
      String strtotal = String.valueOf(this.total);
      if (!"-1".equals(strtotal)) {
         map.put("Total", strtotal);
      }

      String stridle = String.valueOf(this.idle);
      if (!"-1".equals(stridle)) {
         map.put("Idle", stridle);
      }

      String strrunning = String.valueOf(this.running);
      if (!"-1".equals(strrunning)) {
         map.put("Running", strrunning);
      }

      String strsleeping = String.valueOf(this.sleeping);
      if (!"-1".equals(strsleeping)) {
         map.put("Sleeping", strsleeping);
      }

      String strstopped = String.valueOf(this.stopped);
      if (!"-1".equals(strstopped)) {
         map.put("Stopped", strstopped);
      }

      String strzombie = String.valueOf(this.zombie);
      if (!"-1".equals(strzombie)) {
         map.put("Zombie", strzombie);
      }

      String strthreads = String.valueOf(this.threads);
      if (!"-1".equals(strthreads)) {
         map.put("Threads", strthreads);
      }

      return map;
   }

   public String toString() {
      return this.toMap().toString();
   }
}
