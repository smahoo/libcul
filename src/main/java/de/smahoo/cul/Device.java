package de.smahoo.cul;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Device
{
  Controller cntrl = null;
  List<DeviceMsg> msgList;
  String deviceId = "";
  String deviceCode = "";
  List<DeviceEventListener> deviceEventListeners;
  
  public Device(String paramString1, String paramString2)
  {
    this.deviceId = paramString1;
    this.msgList = new ArrayList();
    this.deviceEventListeners = new ArrayList();
    this.deviceCode = paramString2;
  }
  
  protected void dispatchDeviceEvent(DeviceEvent paramDeviceEvent)
  {
    if (this.deviceEventListeners.isEmpty()) {
      return;
    }
    Iterator localIterator = this.deviceEventListeners.iterator();
    while (localIterator.hasNext())
    {
      DeviceEventListener localDeviceEventListener = (DeviceEventListener)localIterator.next();
      localDeviceEventListener.onDeviceEvent(paramDeviceEvent);
    }
  }
  
  public void addDeviceListener(DeviceEventListener paramDeviceEventListener)
  {
    if (this.deviceEventListeners.contains(paramDeviceEventListener)) {
      return;
    }
    this.deviceEventListeners.add(paramDeviceEventListener);
  }
  
  protected void addMsg(DeviceMsg paramDeviceMsg)
  {
    if (this.msgList.contains(paramDeviceMsg)) {
      return;
    }
    if (!evaluateMsg(paramDeviceMsg)) {}
  }
  
  protected void assignController(Controller paramCntrl)
  {
    this.cntrl = paramCntrl;
  }
  
  public DeviceMsg getLastMsg()
  {
    if (this.msgList.isEmpty()) {
      return null;
    }
    return (DeviceMsg)this.msgList.get(this.msgList.size() - 1);
  }
  
  public List<DeviceMsg> getDeviceMessages()
  {
    return this.msgList;
  }
  
  public String getDeviceId()
  {
    return this.deviceId;
  }
  
  public String getDeviceCode()
  {
    return this.deviceCode;
  }
  
  protected abstract boolean evaluateMsg(DeviceMsg paramDeviceMsg);
  
  public abstract int getDeviceType();
  
  public abstract int getDeviceFamily();
}

