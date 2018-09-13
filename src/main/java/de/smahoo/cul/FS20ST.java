package de.smahoo.cul;

public class FS20ST
  extends Device
{
  protected int channel = -1;
  
  public FS20ST(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  public void setChannel(int paramInt)
  {
    this.channel = paramInt;
  }
  
  public int getChannel()
  {
    return this.channel;
  }
  
  public String getDeviceId()
  {
    return this.deviceId;
  }
  
  public String getDeviceCode()
  {
    return this.deviceCode;
  }
  
  protected boolean evaluateMsg(DeviceMsg paramDeviceMsg)
  {
    return true;
  }
  
  public int getDeviceType()
  {
    return 5201;
  }
  
  public int getDeviceFamily()
  {
    return 5000;
  }
  
  protected String channel2Hex()
  {
    String str = Integer.toHexString(this.channel);
    if (str.length() < 2) {
      str = "0" + str;
    }
    return str;
  }
  
  public void turnOn()
  {
    this.cntrl.sendCmd("F" + this.deviceCode + channel2Hex() + "11");
  }
  
  public void turnOff()
  {
    this.cntrl.sendCmd("F" + this.deviceCode + channel2Hex() + "00");
  }
  
  public void switchState()
  {
    this.cntrl.sendCmd("F" + this.deviceCode + channel2Hex() + "12");
  }
}

