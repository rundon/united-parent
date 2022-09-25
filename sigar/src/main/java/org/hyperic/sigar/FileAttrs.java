package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FileAttrs implements Serializable {
   private static final long serialVersionUID = 10323L;
   long permissions = 0L;
   int type = 0;
   long uid = 0L;
   long gid = 0L;
   long inode = 0L;
   long device = 0L;
   long nlink = 0L;
   long size = 0L;
   long atime = 0L;
   long ctime = 0L;
   long mtime = 0L;

   public native void gather(Sigar var1, String var2) throws SigarException;

   static FileAttrs fetch(Sigar sigar, String name) throws SigarException {
      FileAttrs fileAttrs = new FileAttrs();
      fileAttrs.gather(sigar, name);
      return fileAttrs;
   }

   public long getPermissions() {
      return this.permissions;
   }

   public int getType() {
      return this.type;
   }

   public long getUid() {
      return this.uid;
   }

   public long getGid() {
      return this.gid;
   }

   public long getInode() {
      return this.inode;
   }

   public long getDevice() {
      return this.device;
   }

   public long getNlink() {
      return this.nlink;
   }

   public long getSize() {
      return this.size;
   }

   public long getAtime() {
      return this.atime;
   }

   public long getCtime() {
      return this.ctime;
   }

   public long getMtime() {
      return this.mtime;
   }

   void copyTo(FileAttrs copy) {
      copy.permissions = this.permissions;
      copy.type = this.type;
      copy.uid = this.uid;
      copy.gid = this.gid;
      copy.inode = this.inode;
      copy.device = this.device;
      copy.nlink = this.nlink;
      copy.size = this.size;
      copy.atime = this.atime;
      copy.ctime = this.ctime;
      copy.mtime = this.mtime;
   }

   public Map toMap() {
      Map map = new HashMap();
      String strpermissions = String.valueOf(this.permissions);
      if (!"-1".equals(strpermissions)) {
         map.put("Permissions", strpermissions);
      }

      String strtype = String.valueOf(this.type);
      if (!"-1".equals(strtype)) {
         map.put("Type", strtype);
      }

      String struid = String.valueOf(this.uid);
      if (!"-1".equals(struid)) {
         map.put("Uid", struid);
      }

      String strgid = String.valueOf(this.gid);
      if (!"-1".equals(strgid)) {
         map.put("Gid", strgid);
      }

      String strinode = String.valueOf(this.inode);
      if (!"-1".equals(strinode)) {
         map.put("Inode", strinode);
      }

      String strdevice = String.valueOf(this.device);
      if (!"-1".equals(strdevice)) {
         map.put("Device", strdevice);
      }

      String strnlink = String.valueOf(this.nlink);
      if (!"-1".equals(strnlink)) {
         map.put("Nlink", strnlink);
      }

      String strsize = String.valueOf(this.size);
      if (!"-1".equals(strsize)) {
         map.put("Size", strsize);
      }

      String stratime = String.valueOf(this.atime);
      if (!"-1".equals(stratime)) {
         map.put("Atime", stratime);
      }

      String strctime = String.valueOf(this.ctime);
      if (!"-1".equals(strctime)) {
         map.put("Ctime", strctime);
      }

      String strmtime = String.valueOf(this.mtime);
      if (!"-1".equals(strmtime)) {
         map.put("Mtime", strmtime);
      }

      return map;
   }

   public String toString() {
      return this.toMap().toString();
   }
}
