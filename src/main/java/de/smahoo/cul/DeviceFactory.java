package de.smahoo.cul;

public class DeviceFactory
{
  Controller cntrl = null;
  
  public DeviceFactory(Controller paramCntrl)
  {
    this.cntrl = paramCntrl;
  }
  
  public Device generateDevice(int paramInt, String paramString1, String paramString2)
  {
    Object localObject = null;
    switch (paramInt)
    {
    case 4001: 
      localObject = new Hms100tf(paramString1, paramString2);
      break;
    case 1002: 
      localObject = new S300th(paramString1, paramString2);
      break;
    case 3003: 
      localObject = new Fht80(paramString1, paramString2);
      break;
    case 3001: 
      localObject = new Fht80b(paramString1, paramString2);
      break;
    case 3002: 
      localObject = new Fhttk(paramString1, paramString2);
      break;
    case 5100: 
    case 5108: 
      localObject = new FS20S(paramString1, paramString2);
      break;
    case 5201: 
      localObject = new FS20ST(paramString1, paramString2);
      break;
    case 5202: 
      localObject = new Fs20di(paramString1, paramString2);
      break;
    case 5401: 
      localObject = new FS20irf(paramString1, paramString2);
      break;
    case 5130: 
      localObject = new Fs20s_md(paramString1, paramString2);
      break;
    case 6001: 
      localObject = new Em1000(paramString1, paramString2);
    }
    if (localObject != null)
    {
      ((Device)localObject).cntrl = this.cntrl;
      this.cntrl.addDevice((Device)localObject);
    }
    return (Device)localObject;
  }
}

