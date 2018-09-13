package de.smahoo.cul;

public class FS20irf
  extends Device
{
  public FS20irf(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  protected boolean evaluateMsg(DeviceMsg paramDeviceMsg)
  {
    return false;
  }
  
  public int getDeviceType()
  {
    return 5401;
  }
  
  public int getDeviceFamily()
  {
    return 5000;
  }
  
  protected String channel2Hex(int paramInt)
  {
    String str = Integer.toHexString(paramInt);
    if (str.length() < 2) {
      str = "0" + str;
    }
    return str;
  }
  
  public void send(int paramInt)
  {
    this.cntrl.sendCmd("F" + this.deviceCode + channel2Hex(paramInt) + "12");
  }
}

