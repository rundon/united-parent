package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DirUsage implements Serializable {
   private static final long serialVersionUID = 9250L;
   long total = 0L;
   long files = 0L;
   long subdirs = 0L;
   long symlinks = 0L;
   long chrdevs = 0L;
   long blkdevs = 0L;
   long sockets = 0L;
   long diskUsage = 0L;

   public native void gather(Sigar var1, String var2) throws SigarException;

   static DirUsage fetch(Sigar sigar, String name) throws SigarException {
      DirUsage dirUsage = new DirUsage();
      dirUsage.gather(sigar, name);
      return dirUsage;
   }

   public long getTotal() {
      return this.total;
   }

   public long getFiles() {
      return this.files;
   }

   public long getSubdirs() {
      return this.subdirs;
   }

   public long getSymlinks() {
      return this.symlinks;
   }

   public long getChrdevs() {
      return this.chrdevs;
   }

   public long getBlkdevs() {
      return this.blkdevs;
   }

   public long getSockets() {
      return this.sockets;
   }

   public long getDiskUsage() {
      return this.diskUsage;
   }

   void copyTo(DirUsage copy) {
      copy.total = this.total;
      copy.files = this.files;
      copy.subdirs = this.subdirs;
      copy.symlinks = this.symlinks;
      copy.chrdevs = this.chrdevs;
      copy.blkdevs = this.blkdevs;
      copy.sockets = this.sockets;
      copy.diskUsage = this.diskUsage;
   }

   public Map toMap() {
      Map map = new HashMap();
      String strtotal = String.valueOf(this.total);
      if (!"-1".equals(strtotal)) {
         map.put("Total", strtotal);
      }

      String strfiles = String.valueOf(this.files);
      if (!"-1".equals(strfiles)) {
         map.put("Files", strfiles);
      }

      String strsubdirs = String.valueOf(this.subdirs);
      if (!"-1".equals(strsubdirs)) {
         map.put("Subdirs", strsubdirs);
      }

      String strsymlinks = String.valueOf(this.symlinks);
      if (!"-1".equals(strsymlinks)) {
         map.put("Symlinks", strsymlinks);
      }

      String strchrdevs = String.valueOf(this.chrdevs);
      if (!"-1".equals(strchrdevs)) {
         map.put("Chrdevs", strchrdevs);
      }

      String strblkdevs = String.valueOf(this.blkdevs);
      if (!"-1".equals(strblkdevs)) {
         map.put("Blkdevs", strblkdevs);
      }

      String strsockets = String.valueOf(this.sockets);
      if (!"-1".equals(strsockets)) {
         map.put("Sockets", strsockets);
      }

      String strdiskUsage = String.valueOf(this.diskUsage);
      if (!"-1".equals(strdiskUsage)) {
         map.put("DiskUsage", strdiskUsage);
      }

      return map;
   }

   public String toString() {
      return this.toMap().toString();
   }
}
