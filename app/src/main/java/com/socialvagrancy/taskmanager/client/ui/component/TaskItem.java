/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.socialvagrancy.taskmanager.client.ui.component;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author seans
 */
public class TaskItem extends JLabel
{
    private String id;
    private String text;
    private String time;
    private String summary;
    private Status task_status;
    private ActionListener task_clicked_listener;
    private MouseListener clickable_listener;

    private static DateTimeFormatter task_format = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mma");
    private static DateTimeFormatter output_format = DateTimeFormatter.ofPattern("hh:mma");
    
    private enum Status {
        ACTIVE(0,0,255), // Blue
        COMPLETE(204,204,204), // Light Gray
        BLOCKED(211,84,0), // Custom - Orange
        PAST_DUE(255,0,0); // Red
        
        private final int red_value;
        private final int green_value;
        private final int blue_value;
        
        private Status(int red, int green, int blue)
        {
            this.red_value = red;
            this.green_value = green;
            this.blue_value = blue;
        }
        
        public Color color() { return new Color(this.red_value, this.green_value, this.blue_value); }
        public static Status fromString(String status)
        {
            // Convert String to Status
            // .valueOf(String status) wasn't working for ACTIVE
            for(Status s : Status.values())
            {
                if(s.toString().equalsIgnoreCase(status))
                {
                    return s;
                }
            }
            
            return null;
        }
    }
    
    public TaskItem(String start_time, String task_summary, String task_id, String status, ActionListener task_clicked)
    {
        if(status.equals("ACTIVE"))
        {
            status = parseStatus(start_time);
        }

        task_status = Status.fromString(status);
        time = convertDateStamp(start_time);
        summary = task_summary;
        id = task_id;
        task_clicked_listener = task_clicked;
        
        // Configure clickable
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
                {
                    // On click, fire the associated ActionListener from the TaskList pane
                    // this listener attaches the two scripts together to allow for more
                    // modular code.
                    ActionEvent ae = new ActionEvent(this, 0, "TASK_DETAILS:" + id);
        
                    task_clicked_listener.actionPerformed(ae);
                }
        });
        
        // Configure JLabel
        this.setText(time + "\t" + summary);
        this.setForeground(task_status.color());
    }
    
  
    //===========================================
    // External Functions
    //===========================================
    
    
    
    //===========================================
    // Internal Functions
    //===========================================
    
    private String convertDateStamp(String start_date)
    {
        LocalDateTime time = LocalDateTime.parse(start_date, task_format);

        
        return time.format(output_format);
    }
    
    private String parseStatus(String start_date)
    {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime task_due = LocalDateTime.parse(start_date, task_format);
        
        if(task_due.isBefore(now))
        {
            return "PAST_DUE";
        }
        else
        {
            return "ACTIVE";
        } 
    }
}
