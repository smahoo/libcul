package de.smahoo.cul;

import java.util.Date;

public class Fht80b
  extends Fht80
{
  protected double temperature;
  protected double desiredTemperature;
  protected boolean windowOpen;
  protected boolean batteryOk;
  protected int operationMode;
  protected double desiredTemperatureDay;
  protected double desiredTemperatureNight;
  protected double desiredTemperatureWindow;
  protected boolean temperatureConfirmationNeeded = false;
  private Date tempHighStamp = null;
  private Date tempLowStamp = null;
  private int tempHigh;
  private int tempLow;
  private boolean temperatureSet = false;
  private boolean desiredTemperatureSet = false;
  private boolean windowOpenSet = false;
  private boolean batteryOkSet = false;
  private boolean operationModeSet = false;
  private boolean desiredTemperatureDaySet = false;
  private boolean desiredTemperatureNightSet = false;
  private boolean desiredTemperatureWindowSet = false;
  
  public Fht80b(String paramString1, String paramString2)
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
    case 65: 
      applyDesiredTemperature(0.5D * j);
      return true;
    case 66: 
      applyTempLow(j);
      return true;
    case 67: 
      applyTempHigh(j);
      return true;
    case 68: 
      applyWindow((j == 33) || (j == 32));
      applyBattery((j != 1) || (j == 33));
      return true;
    case 62: 
      applyOperationMode(j);
      return true;
    case 130: 
      applyDesiredTempDay(j * 0.5D);
      break;
    case 132: 
      applyDesiredTempNight(j * 0.5D);
    case 138: 
      applyDesiredTempWindow(j * 0.5D);
    }
    return false;
  }
  
  protected void applyDesiredTempWindow(double paramDouble)
  {
    if ((this.desiredTemperatureWindowSet) && (this.desiredTemperatureWindow != paramDouble)) {
      return;
    }
    this.desiredTemperatureWindow = paramDouble;
    this.desiredTemperatureWindowSet = true;
    dispatchDeviceEvent(new DeviceEvent(3, this));
  }
  
  protected void applyDesiredTempNight(double paramDouble)
  {
    if ((this.desiredTemperatureNightSet) && (this.desiredTemperatureNight != paramDouble)) {
      return;
    }
    this.desiredTemperatureNight = paramDouble;
    this.desiredTemperatureNightSet = true;
    dispatchDeviceEvent(new DeviceEvent(3, this));
  }
  
  protected void applyDesiredTempDay(double paramDouble)
  {
    if ((this.desiredTemperatureDaySet) && (this.desiredTemperatureDay != paramDouble)) {
      return;
    }
    this.desiredTemperatureDay = paramDouble;
    this.desiredTemperatureDaySet = true;
    dispatchDeviceEvent(new DeviceEvent(3, this));
  }
  
  protected void applyOperationMode(int paramInt)
  {
    if ((this.operationModeSet) && (this.operationMode == paramInt)) {
      return;
    }
    this.operationMode = paramInt;
    this.operationModeSet = true;
    dispatchDeviceEvent(new DeviceEvent(3, this));
  }
  
  protected void applyBattery(boolean paramBoolean)
  {
    if ((this.batteryOkSet) && (this.batteryOk == paramBoolean)) {
      return;
    }
    this.batteryOk = paramBoolean;
    this.batteryOkSet = true;
    dispatchDeviceEvent(new DeviceEvent(3, this));
  }
  
  protected void applyWindow(boolean paramBoolean)
  {
    if ((this.windowOpenSet) && (this.windowOpen == paramBoolean)) {
      return;
    }
    this.windowOpen = paramBoolean;
    this.windowOpenSet = true;
    dispatchDeviceEvent(new DeviceEvent(3, this));
  }
  
  private void applyTempHigh(int paramInt)
  {
    this.tempHighStamp = new Date();
    this.tempHigh = paramInt;
    if ((this.tempLowStamp != null) && (this.tempHighStamp.getTime() - this.tempLowStamp.getTime() < 20000L))
    {
      applyTemperature(this.tempHigh, this.tempLow);
      this.tempLowStamp = null;
      this.tempHighStamp = null;
    }
  }
  
  private void applyTempLow(int paramInt)
  {
    this.tempLowStamp = new Date();
    this.tempLow = paramInt;
    if ((this.tempHighStamp != null) && (this.tempLowStamp.getTime() - this.tempHighStamp.getTime() < 20000L))
    {
      applyTemperature(this.tempHigh, this.tempLow);
      this.tempLowStamp = null;
      this.tempHighStamp = null;
    }
  }
  
  private void applyTemperature(int paramInt1, int paramInt2)
  {
    applyTemperature(paramInt2 / 10.0D);
  }
  
  protected void applyDesiredTemperature(double paramDouble)
  {
    if (!isDesiredTemperatureSet())
    {
      this.desiredTemperature = paramDouble;
      this.desiredTemperatureSet = true;
      dispatchDeviceEvent(new DeviceEvent(3, this));
    }
    if (this.desiredTemperature != paramDouble)
    {
      this.desiredTemperature = paramDouble;
      this.desiredTemperatureSet = true;
      dispatchDeviceEvent(new DeviceEvent(3, this));
    }
    else if (this.temperatureConfirmationNeeded)
    {
      this.desiredTemperature = paramDouble;
      this.desiredTemperatureSet = true;
      dispatchDeviceEvent(new DeviceEvent(3, this));
    }
  }
  
  protected void applyTemperature(double paramDouble)
  {
    if (this.temperature != paramDouble)
    {
      this.temperature = paramDouble;
      this.temperatureSet = true;
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
  
  public double getTemperature()
  {
    return this.temperature;
  }
  
  public boolean isTemperatureSet()
  {
    return this.temperatureSet;
  }
  
  public boolean isDesiredTemperatureSet()
  {
    return this.desiredTemperatureSet;
  }
  
  public boolean isWindowOpenSet()
  {
    return this.windowOpenSet;
  }
  
  public boolean isBatteryOkSet()
  {
    return this.batteryOkSet;
  }
  
  public boolean isOperationModeSet()
  {
    return this.operationModeSet;
  }
  
  public double getDesiredTemperature()
  {
    return this.desiredTemperature;
  }
  
  public boolean isBatteryOk()
  {
    return this.batteryOk;
  }
  
  public boolean isWindowOpen()
  {
    return this.windowOpen;
  }
  
  public int getOperationMode()
  {
    return this.operationMode;
  }
  
  public void setTemperature(double paramDouble)
  {
    this.desiredTemperature = roundTemperature(paramDouble);
    this.temperatureConfirmationNeeded = true;
    this.cntrl.sendCmd("T" + this.deviceCode + "41" + temperature2Hex(this.desiredTemperature));
  }
  
  public void setTemperatureDay(double paramDouble)
  {
    this.cntrl.sendCmd("T" + this.deviceCode + "82" + temperature2Hex(roundTemperature(paramDouble)));
  }
  
  public void setTemperatureNight(double paramDouble)
  {
    this.cntrl.sendCmd("T" + this.deviceCode + "84" + temperature2Hex(roundTemperature(paramDouble)));
  }
  
  public void setTemperatureWindow(double paramDouble)
  {
    this.cntrl.sendCmd("T" + this.deviceCode + "8a" + temperature2Hex(roundTemperature(paramDouble)));
  }
  
  private double roundTemperature(double paramDouble)
  {
    int i = (int)(paramDouble * 2.0D);
    return i / 2.0D;
  }
}
