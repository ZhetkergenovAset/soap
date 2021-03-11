package gexabyte.test.java.service;


import gexabyte.test.java.dao.UserDao;
import gexabyte.test.java.dao.UserDaoImpl;
import gexabyte.test.java.exception.DaoException;
import gexabyte.test.java.exception.ServerSoapException;
import gexabyte.test.java.model.User;
import gexabyte.test.java.util.EncodeDecodeTool;
import gexabyte.test.java.util.SerializeDeserializeTool;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.JAXBException;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Map;

@WebService(endpointInterface = "gexabyte.test.java.service.UserService")
public class UserServiceImpl implements UserService {
    @Resource
    private WebServiceContext webServiceContext;
    private UserDao userDao;

    @PostConstruct
    public void init() {
        userDao = new UserDaoImpl();
    }

    @Override
    public String save(String content)throws ServerSoapException{
        String result=null;
        try {
            User user = userDao.save(content);
            result=SerializeDeserializeTool.toXml(user);
        } catch (DaoException |JAXBException e) {
            throw new ServerSoapException(e.getMessage());
        }
        return result;
    }

    @Override
    public String auth(String content) throws ServerSoapException {
        String auth;
        try {
            auth = userDao.auth(content);
        } catch (DaoException e) {
            throw new ServerSoapException(e.getMessage());
        }
        return auth;
    }

    @Override
    public String getUser() throws ServerSoapException {
        MessageContext messageContext = webServiceContext.getMessageContext();
        Map headers=(Map)messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);
        List<String> authorization =(List) headers.get("Authorization");
        if (authorization==null || authorization.isEmpty()) {
            throw new ServerSoapException("У вас нет права доступа");
        }
        String loginPassword=EncodeDecodeTool.decodeString(authorization.get(0).substring(6));
        String[] strings = loginPassword.split(":");
        String result = null;
        try {
            User user = userDao.getUser(strings[0], strings[1]);
            result=SerializeDeserializeTool.toXml(user);
        } catch (DaoException |JAXBException e) {
            throw new ServerSoapException();
        }
        return result;
    }


}
