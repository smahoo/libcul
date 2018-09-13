package de.smahoo.cul;

import java.util.Timer;
import java.util.TimerTask;

public class Fs20di
  extends FS20ST
{
  public Fs20di(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  public void dimmUp()
  {
    this.cntrl.sendCmd("F" + this.deviceCode + channel2Hex() + "13");
  }
  
  public void dimmDown()
  {
    this.cntrl.sendCmd("F" + this.deviceCode + channel2Hex() + "14");
  }
  
  public void setOffDimmTime(long paramLong)
  {
    dispatchDeviceEvent(new DeviceEvent(4, this));
    this.cntrl.sendCmd("F" + this.deviceCode + channel2Hex() + "16");
    turnOff();
    Timer localTimer = new Timer();
    localTimer.schedule(new DelayedCmdTask(this, "F" + this.deviceCode + channel2Hex() + "16"), paramLong);
  }
  
  public void setOnDimmTime(long paramLong)
  {
    dispatchDeviceEvent(new DeviceEvent(4, this));
    this.cntrl.sendCmd("F" + this.deviceCode + channel2Hex() + "16");
    turnOn();
    Timer localTimer = new Timer();
    localTimer.schedule(new DelayedCmdTask(this, "F" + this.deviceCode + channel2Hex() + "16"), paramLong);
  }
  
  private class DelayedCmdTask
    extends TimerTask
  {
    Device device = null;
    String command;
    
    public DelayedCmdTask(Device paramDevice, String paramString)
    {
      this.device = paramDevice;
      this.command = paramString;
    }
    
    public void run()
    {
      Fs20di.this.cntrl.sendCmd(this.command);
      this.device.dispatchDeviceEvent(new DeviceEvent(5, this.device));
    }
  }
}
