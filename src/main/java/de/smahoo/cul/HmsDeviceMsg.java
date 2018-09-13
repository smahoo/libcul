package de.smahoo.cul;

public class HmsDeviceMsg
  extends DeviceMsg
{
  double temperature;
  double humidity;
  boolean batteryOk;
  
  public HmsDeviceMsg(String paramString1, String paramString2, int paramInt, double paramDouble1, double paramDouble2, boolean paramBoolean)
  {
    super(paramString1, paramString2, paramInt);
    setDeviceFamily(4000);
    this.temperature = paramDouble1;
    this.humidity = paramDouble2;
    this.batteryOk = paramBoolean;
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
    return this.batteryOk;
  }
  
  public String toString()
  {
    String str = "HMS_" + getDeviceCode() + "   " + this.temperature + " °C | " + this.humidity + " % ";
    if (this.batteryOk) {
      str = str + "| Battery OK";
    } else {
      str = str + " | Low Battery";
    }
    return str;
  }
}

