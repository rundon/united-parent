package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProcCred implements Serializable {
   private static final long serialVersionUID = 3062L;
   long uid = 0L;
   long gid = 0L;
   long euid = 0L;
   long egid = 0L;

   public native void gather(Sigar var1, long var2) throws SigarException;

   static ProcCred fetch(Sigar sigar, long pid) throws SigarException {
      ProcCred procCred = new ProcCred();
      procCred.gather(sigar, pid);
      return procCred;
   }

   public long getUid() {
      return this.uid;
   }

   public long getGid() {
      return this.gid;
   }

   public long getEuid() {
      return this.euid;
   }

   public long getEgid() {
      return this.egid;
   }

   void copyTo(ProcCred copy) {
      copy.uid = this.uid;
      copy.gid = this.gid;
      copy.euid = this.euid;
      copy.egid = this.egid;
   }

   public Map toMap() {
      Map map = new HashMap();
      String struid = String.valueOf(this.uid);
      if (!"-1".equals(struid)) {
         map.put("Uid", struid);
      }

      String strgid = String.valueOf(this.gid);
      if (!"-1".equals(strgid)) {
         map.put("Gid", strgid);
      }

      String streuid = String.valueOf(this.euid);
      if (!"-1".equals(streuid)) {
         map.put("Euid", streuid);
      }

      String stregid = String.valueOf(this.egid);
      if (!"-1".equals(stregid)) {
         map.put("Egid", stregid);
      }

      return map;
   }

   public String toString() {
      return this.toMap().toString();
   }
}
