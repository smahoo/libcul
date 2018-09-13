package de.smahoo.cul;

public class Fhttk
  extends Device
{
  protected boolean open = false;
  
  public Fhttk(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  protected boolean evaluateMsg(DeviceMsg paramDeviceMsg)
  {
    if ((paramDeviceMsg instanceof FhttkDeviceMsg))
    {
      FhttkDeviceMsg localFhttkDeviceMsg = (FhttkDeviceMsg)paramDeviceMsg;
      if (this.open != localFhttkDeviceMsg.isOpen())
      {
        this.open = localFhttkDeviceMsg.isOpen();
        dispatchDeviceEvent(new DeviceEvent(3, this));
      }
      return true;
    }
    return false;
  }
  
  public boolean isOpen()
  {
    return this.open;
  }
  
  public int getDeviceType()
  {
    return 3002;
  }
  
  public int getDeviceFamily()
  {
    return 3000;
  }
}
