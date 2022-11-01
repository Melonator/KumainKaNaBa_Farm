package models;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Notification {
    private static Queue<String> notifications = new LinkedList<String>();

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
