package de.smahoo.cul;

public class WsDeviceMsg
  extends DeviceMsg
{
  double humidity;
  double temperature;
  
  public WsDeviceMsg(String paramString1, String paramString2, int paramInt, double paramDouble1, double paramDouble2)
  {
    super(paramString1, paramString2, paramInt);
    this.deviceFamily = 1000;
    this.humidity = paramDouble2;
    this.temperature = paramDouble1;
  }
  
  public String toString()
  {
    return "WS_" + getDeviceCode() + " " + this.temperature + "°C | " + this.humidity + "%";
  }
  
  public double getTemperature()
  {
    return this.temperature;
  }
  
  public double getHumidity()
  {
    return this.humidity;
  }
}