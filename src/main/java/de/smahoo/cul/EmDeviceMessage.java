package de.smahoo.cul;

public class EmDeviceMessage
  extends DeviceMsg
{
  private double total;
  private double current;
  private double peak;
  
  public EmDeviceMessage(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramString1, paramString2, 6001);
    this.deviceFamily = 6000;
    this.total = (paramInt2 / 1000.0D);
    this.current = (paramInt3 / 100.0D);
    this.peak = (paramInt4 / 100.0D);
  }
  
  public double getPeak()
  {
    return this.peak;
  }
  
  public double getCurrent()
  {
    return this.current;
  }
  
  public double getTotal()
  {
    return this.total;
  }
}

