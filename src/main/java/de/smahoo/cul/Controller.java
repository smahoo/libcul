package de.smahoo.cul;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controller {

  public static final int DEVICEFAMILY_WS = 1000;
  public static final int DEVICETYPE_WS300_PS50 = 1001;
  public static final int DEVICETYPE_WS300_S300TH = 1002;
  public static final int DEVICEFAMILY_FHT = 3000;
  public static final int DEVICETYPE_FHT_80B = 3001;
  public static final int DEVICETYPE_FHT_TK = 3002;
  public static final int DEVICETYPE_FHT_80 = 3003;
  public static final int DEVICEFAMILY_HMS = 4000;
  public static final int DEVICETYPE_HMS_100TF = 4001;
  public static final int DEVICEFAMILY_FS20 = 5000;
  public static final int DEVICETYPE_FS20_S = 5100;
  public static final int DEVICETYPE_FS20_S4 = 5104;
  public static final int DEVICETYPE_FS20_S4A = 5105;
  public static final int DEVICETYPE_FS20_S4U = 5106;
  public static final int DEVICETYPE_FS20_S8 = 5108;
  public static final int DEVICETYPE_FS20_S16 = 5116;
  public static final int DEVICETYPE_FS20_S20 = 5120;
  public static final int DEVICETYPE_FS20_S_MD = 5130;
  public static final int DEVICETYPE_FS20_ST = 5201;
  public static final int DEVICETYPE_FS20_DI = 5202;
  public static final int DEVICETYPE_FS20_TK = 5301;
  public static final int DEVICETYPE_FS20_IRF = 5401;
  public static final int DEVICEFAMILY_EM = 6000;
  public static final int DEVICETYPE_EM_1000 = 6001;
  DataConnection dataConnection = null;
  DataEncoder cid = new DataEncoder();
  String controllerVersion = "";
  private static final String VERSION = "0.2.1";
  protected List<Device> deviceList = new ArrayList();
  protected List<DeviceEventListener> deviceEventListeners = new ArrayList();
  protected List<ControllerEventListener> controllerEventListeners = new ArrayList();
  protected DeviceFactory deviceFactory = new DeviceFactory(this);

  
  public boolean isConnected() {
    if (this.dataConnection != null) {
      return this.dataConnection.isConnected();
    }
    return false;
  }
  
  public String getControllerVersion()
  {
    return this.controllerVersion;
  }
  

  public DeviceFactory getDeviceFactory()
  {
    return this.deviceFactory;
  }

  
  public void init(InputStream in, OutputStream out, int paramInt) throws Exception {
    this.dataConnection.connect(in, out);
    this.dataConnection.sendLine("V");
    this.controllerVersion = evaluateVersionMsg(this.dataConnection.readLine());
    this.dataConnection.sendLine("X21");
    this.dataConnection.startListening();
    dispatchControllerEvent(new ControllerEvent(301, "Connected with " + this.controllerVersion));
  }
  
  protected String evaluateVersionMsg(String paramString) {
    if (paramString.charAt(0) == 'V') {
      return paramString.substring(2, paramString.length());
    }
    return "";
  }
  
  protected synchronized void onDataReceived(String paramString)  {
    if (paramString.length() == 0) {
      return;
    }
    DeviceMsg localDeviceMsg = null;
    localDeviceMsg = this.cid.evaluateLine(paramString);
    if (localDeviceMsg == null)
    {
      System.out.println(paramString + " => can't be intepretated (yet)");
      return;
    }
    Device localDevice = null;
    String str;
    if (localDeviceMsg.getDeviceType() == 3001)
    {
      str = getDeviceFamilyName(localDeviceMsg.getDeviceFamily()) + "_" + getDeviceName(localDeviceMsg.getDeviceType()) + "_" + localDeviceMsg.getDeviceCode();
      localDevice = getDevice(str);
      if (localDevice == null)
      {
        str = getDeviceFamilyName(localDeviceMsg.getDeviceFamily()) + "_" + getDeviceName(3003) + "_" + localDeviceMsg.getDeviceCode();
        localDevice = getDevice(str);
      }
    }
    else
    {
      str = getDeviceFamilyName(localDeviceMsg.getDeviceFamily()) + "_" + getDeviceName(localDeviceMsg.getDeviceType()) + "_" + localDeviceMsg.getDeviceCode();
      localDevice = getDevice(str);
    }
    if (localDevice == null)
    {
      localDevice = this.deviceFactory.generateDevice(localDeviceMsg.getDeviceType(), str, localDeviceMsg.getDeviceCode());
      if (localDevice == null)
      {
        System.out.println("Unknown Device ->");
      }
      else
      {
        localDevice.evaluateMsg(localDeviceMsg);
        dispatchDeviceEvent(new DeviceEvent(1, localDevice));
      }
    }
    else
    {
      localDevice.addMsg(localDeviceMsg);
      dispatchDeviceEvent(new DeviceEvent(2, localDevice));
    }
  }
  
  public void addControllerEventListener(ControllerEventListener paramControllerEventListener) {
    if (this.controllerEventListeners.contains(paramControllerEventListener)) {
      return;
    }
    this.controllerEventListeners.add(paramControllerEventListener);
  }
  
  public void addDeviceEventListener(DeviceEventListener paramDeviceEventListener)  {
    if (this.deviceEventListeners.contains(paramDeviceEventListener)) {
      return;
    }
    this.deviceEventListeners.add(paramDeviceEventListener);
  }
  
  protected void dispatchDeviceEvent(DeviceEvent paramDeviceEvent) {
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
  
  protected void dispatchControllerEvent(ControllerEvent paramControllerEvent) {
    if (this.controllerEventListeners.isEmpty()) {
      return;
    }
    Iterator localIterator = this.controllerEventListeners.iterator();
    while (localIterator.hasNext())
    {
      ControllerEventListener localControllerEventListener = (ControllerEventListener)localIterator.next();
      localControllerEventListener.onControllerEvent(paramControllerEvent);
    }
  }
  
  public synchronized List<Device> getDevices(int paramInt) {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.deviceList.iterator();
    while (localIterator.hasNext())
    {
      Device localDevice = (Device)localIterator.next();
      if ((localDevice != null) && (localDevice.getDeviceFamily() == paramInt)) {
        localArrayList.add(localDevice);
      }
    }
    return localArrayList;
  }
  
  public synchronized List<Integer> getAvailableDeviceFamilies() {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.deviceList.iterator();
    while (localIterator.hasNext())
    {
      Device localDevice = (Device)localIterator.next();
      if ((localDevice != null) && (!localArrayList.contains(Integer.valueOf(localDevice.getDeviceFamily())))) {
        localArrayList.add(Integer.valueOf(localDevice.getDeviceFamily()));
      }
    }
    return localArrayList;
  }
  
  protected synchronized void addDevice(Device paramDevice)  {
    if (paramDevice == null) {
      return;
    }
    if (this.deviceList.contains(paramDevice)) {
      return;
    }
    this.deviceList.add(paramDevice);
    paramDevice.assignController(this);
  }
  
  public synchronized Device getDevice(String paramString) {
    if (paramString == null) {
      return null;
    }
    Iterator localIterator = this.deviceList.iterator();
    while (localIterator.hasNext())
    {
      Device localDevice = (Device)localIterator.next();
      if ((localDevice != null) && (localDevice.getDeviceId().toUpperCase().equals(paramString.toUpperCase()))) {
        return localDevice;
      }
    }
    return null;
  }
  
  public static String getVersion()
  {
    return "0.2.1";
  }
  
  public static String getDeviceName(int paramInt) {
    switch (paramInt)   {
    case 3001: 
      return "80b";
    case 3003: 
      return "80";
    case 3002: 
      return "TK";
    case 4001: 
      return "100TF";
    case 1002: 
      return "S300TH";
    case 1001: 
      return "PS50";
    case 5100: 
      return "S";
    case 5108: 
      return "S8";
    case 5401: 
      return "IRF";
    case 5130: 
      return "MD";
    case 6001: 
      return "1000";
    }
    return "???";
  }
  
  public static String getDeviceFamilyName(int paramInt)  {
    switch (paramInt)   {
    case 1000: 
      return "WS";
    case 3000: 
      return "FHT";
    case 4000: 
      return "HMS";
    case 5000: 
      return "FS20";
    case 6000: 
      return "EM";
    }
    return "???";
  }
  
  public synchronized void sendCmd(String paramString)  {
     if (this.dataConnection == null)      {
        dispatchControllerEvent(new ControllerEvent(-1, "Can't send command! Data Connection is not established"));
        return;
      }
      if (this.dataConnection.isConnected()) {
        this.dataConnection.sendLine(paramString);
      } else {
        dispatchControllerEvent(new ControllerEvent(-1, "Can't send command! Data Connection is not established"));
      }

  }

  public class DataConnection  {
    SerialReader serialReader = null;
    InputStream in;
    OutputStream out;
    boolean connected = false;
    Controller cntrl = null;
    
    public DataConnection(Controller paramCntrl)
    {
      this.cntrl = paramCntrl;
    }
    
    public boolean isConnected()
    {
      return this.connected;
    }
    
    void connect(InputStream in, OutputStream out) throws Exception {

          this.in = in;
          this.out = out;
          this.serialReader = new SerialReader(this.in, this.cntrl);
    }
    
    public void startListening() {
      new Thread(this.serialReader).start();
    }

    
    public void sendLine(String paramString)    {
      BufferedWriter localBufferedWriter = new BufferedWriter(new OutputStreamWriter(this.out));
      try
      {
        localBufferedWriter.write(paramString + "\r\n");
        localBufferedWriter.flush();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    
    protected String readLine() throws Exception {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(this.in));
      return localBufferedReader.readLine();
    }
    
    public class SerialReader implements Runnable {
      InputStream in;
      Controller cntrl;
      boolean terminate = false;
      
      public SerialReader(InputStream paramInputStream, Controller paramCntrl)
      {
        this.in = paramInputStream;
        this.cntrl = paramCntrl;
      }
      
      public void stop()
      {
        this.terminate = true;
        try
        {
          this.in.close();
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
      
      public void run()
      {
        try
        {
          String str = "";
          BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(this.in));
          while (!this.terminate)
          {
            try
            {
              str = localBufferedReader.readLine();
            }
            catch (Exception localException2) {}
            this.cntrl.onDataReceived(str);
          }
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
          this.cntrl.dispatchControllerEvent(new ControllerEvent(-1, "Problems in SerialReader (" + localException1.getMessage() + ""));
        }
      }
    }
  }

}
