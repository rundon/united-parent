package org.hyperic.jni;

import org.hyperic.sigar.util.OsCheck;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.StringTokenizer;

public class ArchLoader {
   private Object loadLock = new Object();
   private boolean loaded = false;
   private static final String osName = System.getProperty("os.name");
   public static final boolean IS_WIN32;
   public static final boolean IS_AIX;
   public static final boolean IS_HPUX;
   public static final boolean IS_SOLARIS;
   public static final boolean IS_LINUX;
   public static final boolean IS_DARWIN;
   public static final boolean IS_OSF1;
   public static final boolean IS_FREEBSD;
   public static final boolean IS_NETWARE;
   private String packageName;
   private String name;
   private String resourcePath;
   private Class loaderClass;
   private String jarName;
   private String libName = null;
   private File nativeLibrary;
   private String version;

   public ArchLoader() {
   }

   public ArchLoader(Class loaderClass) {
      this.setLoaderClass(loaderClass);
      String pname = loaderClass.getName();
      int ix = pname.lastIndexOf(".");
      pname = pname.substring(0, ix);
      this.setPackageName(pname);
      ix = pname.lastIndexOf(".");
      this.setName(pname.substring(ix + 1));
      this.setJarName(this.getName() + ".jar");
      this.setResourcePath(this.toResName(pname));
   }

   public Class getLoaderClass() {
      return this.loaderClass;
   }

   public void setLoaderClass(Class value) {
      this.loaderClass = value;
   }

   public ClassLoader getClassLoader() {
      return this.getLoaderClass().getClassLoader();
   }

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public void setPackageName(String value) {
      this.packageName = value;
   }

   public String getResourcePath() {
      return this.resourcePath;
   }

   public void setResourcePath(String value) {
      this.resourcePath = value;
   }

   public String getJarName() {
      return this.jarName;
   }

   public void setJarName(String value) {
      this.jarName = value;
   }

   public String getLibName() {
      return this.libName;
   }

   public void setLibName(String value) {
      this.libName = value;
   }

   public String getArchLibName() throws ArchNotSupportedException {
      return this.getName() + "-" + ArchName.getName();
   }

   public String getDefaultLibName() throws ArchNotSupportedException {
      return System.getProperty(this.getPackageName() + ".libname", "java" + this.getArchLibName());
   }

   public File getNativeLibrary() {
      return this.nativeLibrary;
   }

   private String toResName(String name) {
      StringBuffer sb = new StringBuffer(name);

      for(int i = 0; i < sb.length(); ++i) {
         if (sb.charAt(i) == '.') {
            sb.setCharAt(i, '/');
         }
      }

      return sb.toString();
   }

   public static String getLibraryPrefix() {
      return !IS_WIN32 && !IS_NETWARE ? "lib" : "";
   }

   public static String getLibraryExtension() {
      if (IS_WIN32) {
         return ".dll";
      } else if (IS_NETWARE) {
         return ".nlm";
      } else if (IS_DARWIN) {
         return ".dylib";
      } else {
         return IS_HPUX ? ".sl" : ".so";
      }
   }

   public String getLibraryName() throws ArchNotSupportedException {
      String libName;
      if ((libName = this.getLibName()) == null) {
         libName = this.getDefaultLibName();
         this.setLibName(libName);
      }

      String prefix = getLibraryPrefix();
      String ext = getLibraryExtension();
      return prefix + libName + ext;
   }

   public String getVersionedLibraryName() {
      if (this.version == null) {
         return null;
      } else {
         try {
            this.getLibraryName();
         } catch (ArchNotSupportedException var3) {
            return null;
         }

         String prefix = getLibraryPrefix();
         String ext = getLibraryExtension();
         return prefix + this.libName + '-' + this.version + ext;
      }
   }

   private boolean isJarURL(URL url) {
      if (url == null) {
         return false;
      } else {
         String name = url.getFile();
         String jarName = this.getJarName();
         if (name.indexOf(jarName) != -1) {
            return true;
         } else {
            int ix = jarName.indexOf(".jar");
            if (ix != -1) {
               jarName = jarName.substring(0, ix) + "-";
               ix = name.lastIndexOf(jarName);
               if (ix != -1) {
                  jarName = name.substring(ix);
                  ix = jarName.indexOf(".jar");
                  if (ix == -1) {
                     return false;
                  } else {
                     this.version = jarName.substring(jarName.indexOf(45) + 1, ix);
                     jarName = jarName.substring(0, ix + 4);
                     this.setJarName(jarName);
                     return true;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }
   }

   public String findJarPath(String libName) throws ArchLoaderException {
      return this.findJarPath(libName, true);
   }

   private String findJarPath(String libName, boolean isRequired) throws ArchLoaderException {
      if (this.getJarName() == null) {
         throw new ArchLoaderException("jarName is null");
      } else {
         String path = this.getResourcePath();
         ClassLoader loader = this.getClassLoader();
         URL url = loader.getResource(path);
         if (!this.isJarURL(url)) {
            url = null;
         }

         if (url == null && loader instanceof URLClassLoader) {
            URL[] urls = ((URLClassLoader)loader).getURLs();

            for(int i = 0; i < urls.length; ++i) {
               if (this.isJarURL(urls[i])) {
                  url = urls[i];
                  break;
               }
            }
         }

         if (url == null) {
            if (isRequired) {
               throw new ArchLoaderException("Unable to find " + this.getJarName());
            } else {
               return null;
            }
         } else {
            path = url.getFile();
            if (path.startsWith("file:")) {
               path = path.substring(5);
            }

            File file = new File(path);

            String jarName;
            for(jarName = this.getJarName(); file != null && !file.getName().startsWith(jarName); file = file.getParentFile()) {
            }

            if (libName == null) {
               libName = jarName;
            }

            if (file != null && (file = file.getParentFile()) != null) {
               String dir = URLDecoder.decode(file.toString());
               if (this.findNativeLibrary(dir, libName)) {
                  return dir;
               }
            }

            return null;
         }
      }
   }

   protected void systemLoadLibrary(String name) {
      System.loadLibrary(name);
   }

   protected void systemLoad(String name) {
      System.load(name);
   }

   protected boolean containsNativeLibrary(File dir, String name) {
      if (name == null) {
         return false;
      } else {
         File file = new File(dir, name);
         if (file.exists()) {
            this.nativeLibrary = file;
            return true;
         } else {
            return false;
         }
      }
   }

   protected boolean findNativeLibrary(String dir, String name) {
      File path = (new File(dir)).getAbsoluteFile();
      if (this.containsNativeLibrary(path, name)) {
         return true;
      } else {
         return this.containsNativeLibrary(path, this.getVersionedLibraryName()) ? true : this.containsNativeLibrary(path, getLibraryPrefix() + this.getName() + getLibraryExtension());
      }
   }

   protected boolean findInJavaLibraryPath(String libName) {
      String path = System.getProperty("java.library.path", "");
      StringTokenizer tok = new StringTokenizer(path, File.pathSeparator);

      do {
         if (!tok.hasMoreTokens()) {
            return false;
         }

         path = tok.nextToken();
      } while(!this.findNativeLibrary(path, libName));

      return true;
   }

   protected void loadLibrary(String path) throws ArchNotSupportedException, ArchLoaderException {
      try {
         String libName = this.getLibraryName();
         setlibPath(libName);
         if (path == null) {
            path = System.getProperty(this.getPackageName() + ".path");
         }

         if (path != null) {
            if (path.equals("-")) {
               return;
            }

            this.findJarPath((String)null, false);
            this.findNativeLibrary(path, libName);
         } else if (this.findJarPath(libName, false) == null) {
            this.findInJavaLibraryPath(libName);
         }

         if (this.nativeLibrary != null) {
            this.systemLoad(this.nativeLibrary.toString());
         } else {
            this.systemLoadLibrary(libName);
         }

      } catch (RuntimeException | IOException var5) {
         String reason = var5.getMessage();
         if (reason == null) {
            reason = var5.getClass().getName();
         }

         String msg = "Failed to load " + this.libName + ": " + reason;
         throw new ArchLoaderException(msg);
      }
   }

   private void setlibPath(String lib) throws IOException {
      ResourceLoader resourceLoader = new DefaultResourceLoader();
      Resource resource = resourceLoader.getResource("classpath:/sigar/" + lib);
      if (resource.exists()) {
         InputStream is = resource.getInputStream();
         File tempDir = new File("/var/log");
         if (!tempDir.exists()) {
            tempDir.mkdirs();
         }
         BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(tempDir, lib), false));
         int lentgh = 0;
         while ((lentgh = is.read()) != -1) {
            os.write(lentgh);
         }
         is.close();
         os.close();
         String path = System.getProperty("java.library.path");
         if (OsCheck.getOperatingSystemType() == OsCheck.OSType.Windows) {
            path += ";" + tempDir.getCanonicalPath();
         } else {
            path += ":" + tempDir.getCanonicalPath();
         }
         System.setProperty("java.library.path", path);
      }
   }

   public void load() throws ArchNotSupportedException, ArchLoaderException {
      this.load((String)null);
   }

   public void load(String path) throws ArchNotSupportedException, ArchLoaderException {
      synchronized(this.loadLock) {
         if (!this.loaded) {
            this.loadLibrary(path);
            this.loaded = true;
         }
      }
   }

   static {
      IS_WIN32 = osName.startsWith("Windows");
      IS_AIX = osName.equals("AIX");
      IS_HPUX = osName.equals("HP-UX");
      IS_SOLARIS = osName.equals("SunOS");
      IS_LINUX = osName.equals("Linux");
      IS_DARWIN = osName.equals("Mac OS X") || osName.equals("Darwin");
      IS_OSF1 = osName.equals("OSF1");
      IS_FREEBSD = osName.equals("FreeBSD");
      IS_NETWARE = osName.equals("NetWare");
   }
}
