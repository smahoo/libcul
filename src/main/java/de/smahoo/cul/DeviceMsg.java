package de.smahoo.cul;

import java.util.Date;

public abstract class DeviceMsg
{
  public static final int DMSGTYPE_UNKNOWN = -1;
  String rawMsg;
  int deviceType = -1;
  int deviceFamily = -1;
  String deviceCode = "";
  Date timestamp;
  DeviceMsgParameter msgParam = null;
  
  public DeviceMsg(String paramString1, String paramString2, int paramInt)
  {
    this.deviceType = paramInt;
    this.deviceCode = paramString2;
    this.rawMsg = paramString1;
    this.timestamp = new Date();
  }
  
  protected void setDeviceFamily(int paramInt)
  {
    this.deviceFamily = paramInt;
  }
  
  public int getDeviceFamily()
  {
    return this.deviceFamily;
  }
  
  public int getDeviceType()
  {
    return this.deviceType;
  }
  
  public String getRawMsg()
  {
    return this.rawMsg;
  }
  
  protected void setMsgParameter(DeviceMsgParameter paramDeviceMsgParameter)
  {
    this.msgParam = paramDeviceMsgParameter;
  }
  
  public DeviceMsgParameter getDeviceMsgParameter()
  {
    return this.msgParam;
  }
  
  public String getDeviceCode()
  {
    return this.deviceCode;
  }
  
  public Date getTimeStamp()
  {
    return this.timestamp;
  }
}
