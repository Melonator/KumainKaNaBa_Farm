package models;

import java.util.LinkedList;
import java.util.Queue;

public class Notification {
    private static Queue<String> notifications = new LinkedList();

    public static void push(String message)
    {
        notifications.add(message);
    }

    public static void display()
    {
        while(notifications.peek() != null)
        {
            System.out.println(notifications.remove());
        }
    }
}
