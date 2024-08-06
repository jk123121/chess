package ui;

import websocket.Notification;
import websocket.NotificationHandler;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl implements NotificationHandler
{
    private final ChessClient client;

    public Repl(String serverUrl)
    {
        client = new ChessClient(serverUrl, this);
    }

    public void run()
    {
        System.out.println(SET_TEXT_COLOR_BLUE + "Welcome to Chess! Sign in or register to start.");
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        String result = "";

        while (!result.equals("quit"))
        {
            printPrompt();
            String line = scanner.nextLine();

            try
            {
                result = client.eval(line);
                System.out.print(result);
            } catch (Throwable e)
            {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    @Override
    public void notify(Notification notification)
    {
        System.out.println(SET_TEXT_COLOR_RED + notification.message());
        printPrompt();
    }

    private void printPrompt()
    {
        System.out.print("\n [" + client.getState() + "] >>> ");
    }
}
