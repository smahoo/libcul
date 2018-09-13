package de.smahoo.cul;

public class S300th
  extends Device
{
  double temperature;
  double humidity;
  
  public S300th(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  protected boolean evaluateMsg(DeviceMsg paramDeviceMsg)
  {
    if ((paramDeviceMsg instanceof WsDeviceMsg))
    {
      WsDeviceMsg localWsDeviceMsg = (WsDeviceMsg)paramDeviceMsg;
      if ((this.temperature != localWsDeviceMsg.getTemperature()) || (this.humidity != localWsDeviceMsg.getHumidity()))
      {
        this.temperature = localWsDeviceMsg.getTemperature();
        this.humidity = localWsDeviceMsg.getHumidity();
        dispatchDeviceEvent(new DeviceEvent(3, this));
      }
    }
    return true;
  }
  
  public int getDeviceType()
  {
    return 1002;
  }
  
  public int getDeviceFamily()
  {
    return 1000;
  }
  
  public double getHumidity()
  {
    return this.humidity;
  }
  
  public double getTemperature()
  {
    return this.temperature;
  }
}

