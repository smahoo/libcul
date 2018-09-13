package de.smahoo.cul;

public class Fht80
  extends Device
{
  protected double actuator = -1.0D;
  private boolean actuatorSet = false;
  
  public Fht80(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  protected boolean evaluateMsg(DeviceMsg paramDeviceMsg)
  {
    int i = paramDeviceMsg.getDeviceMsgParameter().getParamType();
    int j = paramDeviceMsg.getDeviceMsgParameter().getParam();
    switch (i)
    {
    case 1: 
      applyActuator(100.0D * (j / 255.0D));
      return true;
    }
    return false;
  }
  
  protected void applyActuator(double paramDouble)
  {
    if (this.actuator != paramDouble)
    {
      int i = (int)(paramDouble * 10.0D);
      this.actuator = (i / 10.0D);
      this.actuatorSet = true;
      dispatchDeviceEvent(new DeviceEvent(3, this));
    }
  }
  
  public int getDeviceType()
  {
    return 3001;
  }
  
  public int getDeviceFamily()
  {
    return 3000;
  }
  
  public void turnOn()
  {
    this.cntrl.sendCmd("T" + this.deviceCode + "3e014100");
  }
  
  public void turnOff()
  {
    this.cntrl.sendCmd("T" + this.deviceCode + "3e014100");
  }
  
  protected String temperature2Hex(double paramDouble)
  {
    int i = (int)(paramDouble * 2.0D);
    String str = Integer.toHexString(i);
    if (str.length() == 1) {
      str = "0" + str;
    }
    return str.toUpperCase();
  }
  
  public void setMode(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > 2)) {
      return;
    }
    String str = "0" + paramInt;
    this.cntrl.sendCmd("T" + this.deviceCode + "3e" + str);
  }
  
  public boolean isActuatorSet()
  {
    return this.actuatorSet;
  }
  
  public double getActuator()
  {
    return this.actuator;
  }
}
