/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.socialvagrancy.taskmanager.client.ui;

import com.socialvagrancy.taskmanager.client.command.GetTasks;
import com.socialvagrancy.taskmanager.client.ui.Controller;
import com.socialvagrancy.taskmanager.client.ui.component.TaskItem;
import com.socialvagrancy.taskmanager.structure.Contact;
import com.socialvagrancy.taskmanager.structure.Task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author seans
 */
public class TaskList extends javax.swing.JPanel implements Screen {

    /**
     * Creates new form TaskList
     */
    public TaskList() {
        initComponents();
        task_date_picker.setDateToToday();
        task_clicked_listener = new ActionListener() {;
            public void actionPerformed(ActionEvent ae) {
                screen_listener.actionPerformed(ae);
            }
        };
        
  /*
        Commented out to see if the same function in Netbeans' auto-generated code
        will result in the same behavior.
        
        search_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refresh(null); // Null refresh as no id is required.
            }
        });
 */
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        overview_pane = new javax.swing.JPanel();
        owner_selector = new javax.swing.JComboBox<>();
        search_button = new javax.swing.JButton();
        owner_label = new javax.swing.JLabel();
        date_label = new javax.swing.JLabel();
        task_date_picker = new com.github.lgooddatepicker.components.DatePicker();
        new_task_button = new javax.swing.JButton();
        task_list_panel = new javax.swing.JPanel();
        task_list_scroll_pane = new javax.swing.JScrollPane();
        task_panel = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Task List"));

        overview_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        owner_selector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        search_button.setText("Search");
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });

        owner_label.setText("Belongs To:");
        owner_label.setFont(new java.awt.Font("Helvetica Neue", 0, 10)); // NOI18N

        date_label.setText("Task Date:");
        date_label.setFont(new java.awt.Font("Helvetica Neue", 0, 10)); // NOI18N

        new_task_button.setText("New");

        javax.swing.GroupLayout overview_paneLayout = new javax.swing.GroupLayout(overview_pane);
        overview_pane.setLayout(overview_paneLayout);
        overview_paneLayout.setHorizontalGroup(
            overview_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(overview_paneLayout.createSequentialGroup()
                .addGroup(overview_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(overview_paneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(overview_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(overview_paneLayout.createSequentialGroup()
                                .addGap(135, 135, 135)
                                .addComponent(date_label))
                            .addGroup(overview_paneLayout.createSequentialGroup()
                                .addComponent(new_task_button)
                                .addGap(8, 8, 8)
                                .addComponent(task_date_picker, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(search_button))))
                    .addGroup(overview_paneLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(owner_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(owner_selector, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        overview_paneLayout.setVerticalGroup(
            overview_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, overview_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(date_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(overview_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(task_date_picker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(new_task_button)
                    .addComponent(search_button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(overview_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(owner_selector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(owner_label))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        task_list_panel.setBackground(new java.awt.Color(255, 255, 255));
        task_list_panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        task_panel.setBackground(new java.awt.Color(255, 255, 255));
        task_panel.setLayout(new java.awt.GridLayout(100, 1));
        task_list_scroll_pane.setViewportView(task_panel);

        javax.swing.GroupLayout task_list_panelLayout = new javax.swing.GroupLayout(task_list_panel);
        task_list_panel.setLayout(task_list_panelLayout);
        task_list_panelLayout.setHorizontalGroup(
            task_list_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(task_list_scroll_pane)
        );
        task_list_panelLayout.setVerticalGroup(
            task_list_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(task_list_scroll_pane)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(task_list_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(overview_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(overview_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(task_list_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public void attachToClient(ActionListener l)
    {
        screen_listener = l;
    }
    
    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_buttonActionPerformed
        // TODO add your handling code here:
        refresh(owner_selector.getSelectedItem().toString());
    }//GEN-LAST:event_search_buttonActionPerformed

    @Override
    public void refresh(String id)
    { 
        // id will be used to cache the user that was selected on the last 
        // refresh.
        if(id != null)
        {
            default_user = id;
        }
        
        // Reset the task owner selector box
        // This needs to be completed first before querying for tasks
        // otherwise we search for the default "Item 1"
        ArrayList<Contact> user_list = api_controller.getUsers();
        owner_selector.removeAllItems();
        
        for(int i=0; i<user_list.size(); i++)
        {
            owner_selector.addItem(user_list.get(i).fullName());
        }
    
        owner_selector.setSelectedItem(default_user);
        
        // Pull the tasks associted with the selected user for the current date
        ArrayList<Task> task_list = api_controller.getTasks(task_date_picker.toString(), owner_selector.getSelectedItem().toString());
        task_panel.removeAll();
        TaskItem task;

        for(int i=0; i<task_list.size(); i++)
        {
            task = new TaskItem(task_list.get(i).startTime(), task_list.get(i).subject(), task_list.get(i).id(), task_list.get(i).status().toString(), task_clicked_listener);
            task_panel.add(task);
        }
        
        this.revalidate();
        this.repaint();
    }

    @Override
    public void setApiController(Controller api)
    {
        api_controller = api;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel date_label;
    private javax.swing.JButton new_task_button;
    private javax.swing.JPanel overview_pane;
    private javax.swing.JLabel owner_label;
    private javax.swing.JComboBox<String> owner_selector;
    private javax.swing.JButton search_button;
    private com.github.lgooddatepicker.components.DatePicker task_date_picker;
    private javax.swing.JPanel task_list_panel;
    private javax.swing.JScrollPane task_list_scroll_pane;
    private javax.swing.JPanel task_panel;
    // End of variables declaration//GEN-END:variables

    private Controller api_controller;
    private String default_user; // the default user to display when the screen refreshes
    private ActionListener screen_listener;
    private ActionListener task_clicked_listener;
}
