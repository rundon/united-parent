package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NfsClientV3 implements Serializable {
   private static final long serialVersionUID = 23335L;
   long _null = 0L;
   long getattr = 0L;
   long setattr = 0L;
   long lookup = 0L;
   long access = 0L;
   long readlink = 0L;
   long read = 0L;
   long write = 0L;
   long create = 0L;
   long mkdir = 0L;
   long symlink = 0L;
   long mknod = 0L;
   long remove = 0L;
   long rmdir = 0L;
   long rename = 0L;
   long link = 0L;
   long readdir = 0L;
   long readdirplus = 0L;
   long fsstat = 0L;
   long fsinfo = 0L;
   long pathconf = 0L;
   long commit = 0L;

   public native void gather(Sigar var1) throws SigarException;

   static NfsClientV3 fetch(Sigar sigar) throws SigarException {
      NfsClientV3 nfsClientV3 = new NfsClientV3();
      nfsClientV3.gather(sigar);
      return nfsClientV3;
   }

   public long getNull() {
      return this._null;
   }

   public long getGetattr() {
      return this.getattr;
   }

   public long getSetattr() {
      return this.setattr;
   }

   public long getLookup() {
      return this.lookup;
   }

   public long getAccess() {
      return this.access;
   }

   public long getReadlink() {
      return this.readlink;
   }

   public long getRead() {
      return this.read;
   }

   public long getWrite() {
      return this.write;
   }

   public long getCreate() {
      return this.create;
   }

   public long getMkdir() {
      return this.mkdir;
   }

   public long getSymlink() {
      return this.symlink;
   }

   public long getMknod() {
      return this.mknod;
   }

   public long getRemove() {
      return this.remove;
   }

   public long getRmdir() {
      return this.rmdir;
   }

   public long getRename() {
      return this.rename;
   }

   public long getLink() {
      return this.link;
   }

   public long getReaddir() {
      return this.readdir;
   }

   public long getReaddirplus() {
      return this.readdirplus;
   }

   public long getFsstat() {
      return this.fsstat;
   }

   public long getFsinfo() {
      return this.fsinfo;
   }

   public long getPathconf() {
      return this.pathconf;
   }

   public long getCommit() {
      return this.commit;
   }

   void copyTo(NfsClientV3 copy) {
      copy._null = this._null;
      copy.getattr = this.getattr;
      copy.setattr = this.setattr;
      copy.lookup = this.lookup;
      copy.access = this.access;
      copy.readlink = this.readlink;
      copy.read = this.read;
      copy.write = this.write;
      copy.create = this.create;
      copy.mkdir = this.mkdir;
      copy.symlink = this.symlink;
      copy.mknod = this.mknod;
      copy.remove = this.remove;
      copy.rmdir = this.rmdir;
      copy.rename = this.rename;
      copy.link = this.link;
      copy.readdir = this.readdir;
      copy.readdirplus = this.readdirplus;
      copy.fsstat = this.fsstat;
      copy.fsinfo = this.fsinfo;
      copy.pathconf = this.pathconf;
      copy.commit = this.commit;
   }

   public Map toMap() {
      Map map = new HashMap();
      String str_null = String.valueOf(this._null);
      if (!"-1".equals(str_null)) {
         map.put("_null", str_null);
      }

      String strgetattr = String.valueOf(this.getattr);
      if (!"-1".equals(strgetattr)) {
         map.put("Getattr", strgetattr);
      }

      String strsetattr = String.valueOf(this.setattr);
      if (!"-1".equals(strsetattr)) {
         map.put("Setattr", strsetattr);
      }

      String strlookup = String.valueOf(this.lookup);
      if (!"-1".equals(strlookup)) {
         map.put("Lookup", strlookup);
      }

      String straccess = String.valueOf(this.access);
      if (!"-1".equals(straccess)) {
         map.put("Access", straccess);
      }

      String strreadlink = String.valueOf(this.readlink);
      if (!"-1".equals(strreadlink)) {
         map.put("Readlink", strreadlink);
      }

      String strread = String.valueOf(this.read);
      if (!"-1".equals(strread)) {
         map.put("Read", strread);
      }

      String strwrite = String.valueOf(this.write);
      if (!"-1".equals(strwrite)) {
         map.put("Write", strwrite);
      }

      String strcreate = String.valueOf(this.create);
      if (!"-1".equals(strcreate)) {
         map.put("Create", strcreate);
      }

      String strmkdir = String.valueOf(this.mkdir);
      if (!"-1".equals(strmkdir)) {
         map.put("Mkdir", strmkdir);
      }

      String strsymlink = String.valueOf(this.symlink);
      if (!"-1".equals(strsymlink)) {
         map.put("Symlink", strsymlink);
      }

      String strmknod = String.valueOf(this.mknod);
      if (!"-1".equals(strmknod)) {
         map.put("Mknod", strmknod);
      }

      String strremove = String.valueOf(this.remove);
      if (!"-1".equals(strremove)) {
         map.put("Remove", strremove);
      }

      String strrmdir = String.valueOf(this.rmdir);
      if (!"-1".equals(strrmdir)) {
         map.put("Rmdir", strrmdir);
      }

      String strrename = String.valueOf(this.rename);
      if (!"-1".equals(strrename)) {
         map.put("Rename", strrename);
      }

      String strlink = String.valueOf(this.link);
      if (!"-1".equals(strlink)) {
         map.put("Link", strlink);
      }

      String strreaddir = String.valueOf(this.readdir);
      if (!"-1".equals(strreaddir)) {
         map.put("Readdir", strreaddir);
      }

      String strreaddirplus = String.valueOf(this.readdirplus);
      if (!"-1".equals(strreaddirplus)) {
         map.put("Readdirplus", strreaddirplus);
      }

      String strfsstat = String.valueOf(this.fsstat);
      if (!"-1".equals(strfsstat)) {
         map.put("Fsstat", strfsstat);
      }

      String strfsinfo = String.valueOf(this.fsinfo);
      if (!"-1".equals(strfsinfo)) {
         map.put("Fsinfo", strfsinfo);
      }

      String strpathconf = String.valueOf(this.pathconf);
      if (!"-1".equals(strpathconf)) {
         map.put("Pathconf", strpathconf);
      }

      String strcommit = String.valueOf(this.commit);
      if (!"-1".equals(strcommit)) {
         map.put("Commit", strcommit);
      }

      return map;
   }

   public String toString() {
      return this.toMap().toString();
   }
}
