package gexabyte.test.java;

import gexabyte.test.java.client.UserClient;
import gexabyte.test.java.model.Auth;
import gexabyte.test.java.model.User;

import javax.xml.bind.JAXBException;
import javax.xml.soap.*;
import java.io.IOException;

public class Client {
    public static void main(String[] args) throws SOAPException, IOException, JAXBException {
        User user=new User();
        user.setLogin("azhetkegenov@bk.ru");
        user.setPassword("aset");
        user.setUserName("Aset");
        user.setIinNumber("921117323625");
        user.setLastName("Zhetkergenov");
        UserClient userClient =new UserClient();

        //метод регистрации пользователя
        user = userClient.save(user);
        System.out.println("Результат метода сохранения");
        System.out.println(user.toString());
        System.out.println("___________________________________________");


        //метод авторизации
        String auth = userClient.auth(new Auth(user.getLogin(), user.getPassword()));
        System.out.println("Результат метода авторизации");
        System.out.println(auth);
        System.out.println("______________________________________________");

        //получение информации о пользователе по base code
        user = userClient.getUser(auth);
        System.out.println("Результат метода по получению пользователя");
        System.out.println(user.toString());

    }

}
