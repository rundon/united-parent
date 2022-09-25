package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProcExe implements Serializable {
   private static final long serialVersionUID = 1997L;
   String name = null;
   String cwd = null;

   public native void gather(Sigar var1, long var2) throws SigarException;

   static ProcExe fetch(Sigar sigar, long pid) throws SigarException {
      ProcExe procExe = new ProcExe();
      procExe.gather(sigar, pid);
      return procExe;
   }

   public String getName() {
      return this.name;
   }

   public String getCwd() {
      return this.cwd;
   }

   void copyTo(ProcExe copy) {
      copy.name = this.name;
      copy.cwd = this.cwd;
   }

   public Map toMap() {
      Map map = new HashMap();
      String strname = String.valueOf(this.name);
      if (!"-1".equals(strname)) {
         map.put("Name", strname);
      }

      String strcwd = String.valueOf(this.cwd);
      if (!"-1".equals(strcwd)) {
         map.put("Cwd", strcwd);
      }

      return map;
   }

   public String toString() {
      return this.toMap().toString();
   }
}
