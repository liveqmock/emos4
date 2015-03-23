/**
 * ITMISImplFinishService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ultrapower.wfinterface.itms;

public interface ITMISImplFinishService extends javax.xml.rpc.Service {
    public java.lang.String getSOAPEventSourceAddress();

    public com.ultrapower.wfinterface.itms.BusinessProcessSheetService getSOAPEventSource() throws javax.xml.rpc.ServiceException;

    public com.ultrapower.wfinterface.itms.BusinessProcessSheetService getSOAPEventSource(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
