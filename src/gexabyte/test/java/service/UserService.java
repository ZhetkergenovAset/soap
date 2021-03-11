
package gexabyte.test.java.service;

import gexabyte.test.java.exception.DaoException;
import gexabyte.test.java.exception.ServerSoapException;
import gexabyte.test.java.model.User;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.JAXBException;

@WebService
public interface UserService {

    @WebResult(name = "content")
    String save(@WebParam(name = "content") String content) throws ServerSoapException;

    String auth(@WebParam(name = "content") String content) throws ServerSoapException;

    @WebResult(name = "content")
    String getUser() throws ServerSoapException;
}
