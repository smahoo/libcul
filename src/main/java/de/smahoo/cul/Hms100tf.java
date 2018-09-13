package de.smahoo.cul;

public class Hms100tf
  extends Device
{
  double temperature;
  double humidity;
  boolean battery;
  private boolean valuesSet = false;
  
  public Hms100tf(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  protected boolean evaluateMsg(DeviceMsg paramDeviceMsg)
  {
    if ((paramDeviceMsg instanceof HmsDeviceMsg))
    {
      HmsDeviceMsg localHmsDeviceMsg = (HmsDeviceMsg)paramDeviceMsg;
      this.humidity = localHmsDeviceMsg.getHumidity();
      this.temperature = localHmsDeviceMsg.getTemperature();
      this.battery = localHmsDeviceMsg.isBatteryOk();
      this.valuesSet = true;
      dispatchDeviceEvent(new DeviceEvent(3, this));
    }
    return true;
  }
  
  public boolean isValuesSet()
  {
    return this.valuesSet;
  }
  
  public int getDeviceType()
  {
    return 4001;
  }
  
  public int getDeviceFamily()
  {
    return 4000;
  }
  
  public double getTemperature()
  {
    return this.temperature;
  }
  
  public double getHumidity()
  {
    return this.humidity;
  }
  
  public boolean isBatteryOk()
  {
    return this.battery;
  }
}
