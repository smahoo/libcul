package de.smahoo.cul;

public class Em1000
  extends Device
{
  private double peak;
  private double current;
  private double total;
  
  public Em1000(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  protected boolean evaluateMsg(DeviceMsg paramDeviceMsg)
  {
    if ((paramDeviceMsg instanceof EmDeviceMessage))
    {
      EmDeviceMessage localEmDeviceMessage = (EmDeviceMessage)paramDeviceMsg;
      int i = 0;
      if (this.peak != localEmDeviceMessage.getPeak())
      {
        this.peak = localEmDeviceMessage.getPeak();
        i = 1;
      }
      if (this.current != localEmDeviceMessage.getCurrent())
      {
        this.current = localEmDeviceMessage.getCurrent();
        i = 1;
      }
      if (this.total != localEmDeviceMessage.getTotal()) {
        this.total = localEmDeviceMessage.getTotal();
      }
      if (i != 0) {
        dispatchDeviceEvent(new DeviceEvent(3, this));
      }
      return true;
    }
    return false;
  }
  
  public double getTotalConsumption()
  {
    return this.total;
  }
  
  public double getCurrentConsumption()
  {
    return this.current;
  }
  
  public double getPeakConsumption()
  {
    return this.peak;
  }
  
  public int getDeviceType()
  {
    return 6001;
  }
  
  public int getDeviceFamily()
  {
    return 6000;
  }
}
