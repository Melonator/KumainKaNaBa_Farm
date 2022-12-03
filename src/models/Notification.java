package models;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The Notification class is reponsible for displaying notifications to the player.
 * Its members and atritbutes are static so that it can be accessed from anywhere.
 */
public class Notification {
    private static Queue<String> notifications = new LinkedList();

    /**
     * Pushes a notification to the queue.
     * @param message   Message to be pushed
     */
    public static void push(String message)
    {
        notifications.add(message);
    }

    /**
     * Displays all notification from the queue.
     */
    public static void display()
    {
        while(notifications.peek() != null)
        {
            System.out.println(notifications.remove());
        }
    }
}
