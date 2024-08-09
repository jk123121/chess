package websocket;

import com.google.gson.Gson;
import exception.ResponseException;
import websocket.Action;
import websocket.Notification;

import javax.websocket.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class WebSocketFacade extends Endpoint {

    Session session;
    NotificationHandler notificationHandler;


    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException
    {
        try
        {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>()
            {
                @Override
                public void onMessage(String message) {
                    Notification notification = new Gson().fromJson(message, Notification.class);
                    notificationHandler.notify(notification);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex)
        {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public static void get(String msg) throws IOException
    {
        var url = new URL("http://localhost:8080/echo/" + msg);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        conn.connect();

        try (InputStream respBody = conn.getInputStream())
        {
            byte[] bytes = new byte[respBody.available()];
            respBody.read(bytes);
            System.out.println(new String(bytes));
        }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {}

    public void register(String username, String password, String email) throws ResponseException
    {

    }

    public void enterPetShop(String visitorName) throws ResponseException
    {
        try
        {
            var action = new Action(Action.Type.ENTER, visitorName);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex)
        {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void leavePetShop(String visitorName) throws ResponseException
    {
        try
        {
            var action = new Action(Action.Type.EXIT, visitorName);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
            this.session.close();
        } catch (IOException ex)
        {
            throw new ResponseException(500, ex.getMessage());
        }
    }
}
