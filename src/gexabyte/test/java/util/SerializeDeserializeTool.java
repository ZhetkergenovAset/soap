package gexabyte.test.java.util;

import gexabyte.test.java.model.Auth;
import gexabyte.test.java.model.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class SerializeDeserializeTool {

    public static User toUser(String xmlString) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(User.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        User user = (User) unmarshaller.unmarshal(new StringReader(xmlString));
        return user;
    }

    public static String toXml(User user) throws JAXBException {
        StringWriter writer = new StringWriter();
        JAXBContext jc = JAXBContext.newInstance(User.class);
        jc.createMarshaller().marshal(user, writer);
        return writer.toString();
    }

    public static Auth toAuth(String xmlString) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Auth.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Auth auth = (Auth) unmarshaller.unmarshal(new StringReader(xmlString));
        return auth;
    }

    public static String toXml(Auth auth) throws JAXBException {
        StringWriter writer = new StringWriter();
        JAXBContext jc = JAXBContext.newInstance(Auth.class);
        jc.createMarshaller().marshal(auth, writer);
        return writer.toString();
    }


}
