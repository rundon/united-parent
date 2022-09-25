package org.hyperic.sigar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NetConnection implements Serializable {
   private static final long serialVersionUID = 12776L;
   long localPort = 0L;
   String localAddress = null;
   long remotePort = 0L;
   String remoteAddress = null;
   int type = 0;
   int state = 0;
   long sendQueue = 0L;
   long receiveQueue = 0L;

   public native void gather(Sigar var1) throws SigarException;

   static NetConnection fetch(Sigar sigar) throws SigarException {
      NetConnection netConnection = new NetConnection();
      netConnection.gather(sigar);
      return netConnection;
   }

   public long getLocalPort() {
      return this.localPort;
   }

   public String getLocalAddress() {
      return this.localAddress;
   }

   public long getRemotePort() {
      return this.remotePort;
   }

   public String getRemoteAddress() {
      return this.remoteAddress;
   }

   public int getType() {
      return this.type;
   }

   public int getState() {
      return this.state;
   }

   public long getSendQueue() {
      return this.sendQueue;
   }

   public long getReceiveQueue() {
      return this.receiveQueue;
   }

   void copyTo(NetConnection copy) {
      copy.localPort = this.localPort;
      copy.localAddress = this.localAddress;
      copy.remotePort = this.remotePort;
      copy.remoteAddress = this.remoteAddress;
      copy.type = this.type;
      copy.state = this.state;
      copy.sendQueue = this.sendQueue;
      copy.receiveQueue = this.receiveQueue;
   }

   public native String getTypeString();

   public static native String getStateString(int var0);

   public String getStateString() {
      return getStateString(this.state);
   }

   public Map toMap() {
      Map map = new HashMap();
      String strlocalPort = String.valueOf(this.localPort);
      if (!"-1".equals(strlocalPort)) {
         map.put("LocalPort", strlocalPort);
      }

      String strlocalAddress = String.valueOf(this.localAddress);
      if (!"-1".equals(strlocalAddress)) {
         map.put("LocalAddress", strlocalAddress);
      }

      String strremotePort = String.valueOf(this.remotePort);
      if (!"-1".equals(strremotePort)) {
         map.put("RemotePort", strremotePort);
      }

      String strremoteAddress = String.valueOf(this.remoteAddress);
      if (!"-1".equals(strremoteAddress)) {
         map.put("RemoteAddress", strremoteAddress);
      }

      String strtype = String.valueOf(this.type);
      if (!"-1".equals(strtype)) {
         map.put("Type", strtype);
      }

      String strstate = String.valueOf(this.state);
      if (!"-1".equals(strstate)) {
         map.put("State", strstate);
      }

      String strsendQueue = String.valueOf(this.sendQueue);
      if (!"-1".equals(strsendQueue)) {
         map.put("SendQueue", strsendQueue);
      }

      String strreceiveQueue = String.valueOf(this.receiveQueue);
      if (!"-1".equals(strreceiveQueue)) {
         map.put("ReceiveQueue", strreceiveQueue);
      }

      return map;
   }

   public String toString() {
      return this.toMap().toString();
   }
}
