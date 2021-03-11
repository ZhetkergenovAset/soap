package gexabyte.test.java;

import gexabyte.test.java.config.Constant;
import gexabyte.test.java.service.UserServiceImpl;

import javax.xml.ws.Endpoint;
import java.io.UnsupportedEncodingException;

public class Server {
    public static void main(String[] args) throws UnsupportedEncodingException {
        Endpoint.publish(Constant.URL_SOAP_SERVER,new UserServiceImpl());
        System.out.println("Service soap started.......");
    }
}
