package de.smahoo.cul;

public class FhttkDeviceMsg
  extends DeviceMsg
{
  private boolean open = false;
  private boolean battery = false;
  
  public FhttkDeviceMsg(String paramString1, String paramString2, boolean paramBoolean)
  {
    super(paramString1, paramString2, 3002);
    this.deviceFamily = 3000;
    this.open = paramBoolean;
  }
  
  public String toString()
  {
    return "FHTTK_" + getDeviceCode() + " " + this.open;
  }
  
  public boolean isOpen()
  {
    return this.open;
  }
}

