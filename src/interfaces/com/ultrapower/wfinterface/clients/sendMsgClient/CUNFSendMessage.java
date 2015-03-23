/**
 * CUNFSendMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ultrapower.wfinterface.clients.sendMsgClient;

public interface CUNFSendMessage extends javax.xml.rpc.Service {
    public java.lang.String getSOAPEventSourceAddress();

    public com.ultrapower.wfinterface.clients.sendMsgClient.MessageServicePortType getSOAPEventSource() throws javax.xml.rpc.ServiceException;

    public com.ultrapower.wfinterface.clients.sendMsgClient.MessageServicePortType getSOAPEventSource(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
