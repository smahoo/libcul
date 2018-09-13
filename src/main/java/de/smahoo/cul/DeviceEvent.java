package de.smahoo.cul;

public class DeviceEvent
{
  public static final int NEW_DEVICE = 1;
  public static final int NEW_DEVICEMESSAGE = 2;
  public static final int PROPERTY_CHANGED = 3;
  public static final int CONIFURATION_STARTED = 4;
  public static final int CONIFURATION_FINISHED = 5;
  protected int eventType = -1;
  protected Device device = null;
  
  public DeviceEvent(int paramInt, Device paramDevice)
  {
    this.eventType = paramInt;
    this.device = paramDevice;
  }
  
  public String toString()
  {
    if (this.device == null) {
      return "currupted DeviceEvent (device is null)";
    }
    switch (this.eventType)
    {
    case 1: 
      return "new Device added (" + this.device.getDeviceId() + ")";
    case 2: 
      String str = "new Message received";
      DeviceMsg localDeviceMsg = this.device.getLastMsg();
      if (localDeviceMsg != null) {
        str = str + " (" + localDeviceMsg.getRawMsg() + ") => " + localDeviceMsg.toString();
      }
      return str;
    }
    return "unknown DeviceEvent (" + this.eventType + ")";
  }
  
  public int getEventType()
  {
    return this.eventType;
  }
  
  public Device getDevice()
  {
    return this.device;
  }
}

