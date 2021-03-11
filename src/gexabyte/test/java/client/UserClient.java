package gexabyte.test.java.client;

import gexabyte.test.java.config.Constant;
import gexabyte.test.java.model.Auth;
import gexabyte.test.java.model.User;
import gexabyte.test.java.util.SerializeDeserializeTool;

import javax.xml.bind.JAXBException;
import javax.xml.soap.*;

public class UserClient {

    public User save(User user) throws SOAPException, JAXBException {
        SOAPMessage soapMessage = createSOAPMessage();
        SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
        soapEnvelope.addNamespaceDeclaration(Constant.PREFIX, Constant.TARGETNAMESPACE_WSDL);
        SOAPBody soapBody = soapEnvelope.getBody();
        SOAPElement soapElement = soapBody.addChildElement(Constant.METHOD_SAVE, Constant.PREFIX);
        SOAPElement elementContent = soapElement.addChildElement(Constant.CONTENT);
        String xml = SerializeDeserializeTool.toXml(user);
        elementContent.addTextNode(xml);
        SOAPConnection connection = createConnection();
        SOAPMessage resultMessage = connection.call(soapMessage, Constant.URL_SOAP_SERVER);
        connection.close();
        return SerializeDeserializeTool.toUser(resultMessage.getSOAPPart().getEnvelope().getBody().getTextContent());
    }

    public String auth(Auth auth) throws SOAPException, JAXBException {
        SOAPMessage soapMessage = createSOAPMessage();
        SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
        soapEnvelope.addNamespaceDeclaration(Constant.PREFIX, Constant.TARGETNAMESPACE_WSDL);
        SOAPBody soapBody = soapEnvelope.getBody();
        SOAPElement soapElement = soapBody.addChildElement(Constant.METHOD_AUTH, Constant.PREFIX);
        SOAPElement elementContent = soapElement.addChildElement(Constant.CONTENT);
        String xml = SerializeDeserializeTool.toXml(auth);
        elementContent.addTextNode(xml);
        SOAPConnection connection = createConnection();
        System.out.println(soapMessage.getSOAPPart().getEnvelope().getBody().getTextContent());
        SOAPMessage resultMessage = connection.call(soapMessage, Constant.URL_SOAP_SERVER);
        connection.close();
        return resultMessage.getSOAPPart().getEnvelope().getBody().getTextContent();
    }

    public User getUser(String baseCode) throws SOAPException, JAXBException {
        SOAPMessage soapMessage = createSOAPMessage();
        MimeHeaders mimeHeaders = soapMessage.getMimeHeaders();
        mimeHeaders.addHeader("Authorization", "Basic " + baseCode);
        SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
        soapEnvelope.addNamespaceDeclaration(Constant.PREFIX, Constant.TARGETNAMESPACE_WSDL);
        SOAPBody soapBody = soapEnvelope.getBody();
        soapBody.addChildElement("getUser", Constant.PREFIX);
        SOAPConnection connection = createConnection();
        SOAPMessage call = connection.call(soapMessage, Constant.URL_SOAP_SERVER);
        connection.close();
        return SerializeDeserializeTool.toUser(call.getSOAPPart().getEnvelope().getBody().getTextContent());
    }

    private SOAPConnection createConnection() throws SOAPException {
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        return soapConnectionFactory.createConnection();
    }

    private SOAPMessage createSOAPMessage() throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        return messageFactory.createMessage();
    }
}
