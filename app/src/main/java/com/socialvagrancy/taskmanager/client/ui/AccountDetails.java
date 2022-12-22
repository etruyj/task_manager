/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.socialvagrancy.taskmanager.client.ui;

/**
 *
 * @author seans
 */
public class AccountDetails extends javax.swing.JPanel {

    /**
     * Creates new form AccountDetails
     */
    public AccountDetails() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        account_name_panel = new javax.swing.JPanel();
        account_name_field = new javax.swing.JTextField();
        account_details_pane = new javax.swing.JTabbedPane();
        account_detail_tab = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        task_tab = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        create_task_button = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        contact_tab = new javax.swing.JPanel();
        contact_list_scroll = new javax.swing.JScrollPane();
        contact_list_area = new javax.swing.JTextArea();
        contact_details_panel = new javax.swing.JPanel();
        contact_first_name_label = new javax.swing.JLabel();
        contact_last_name_label = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        contact_office_text = new javax.swing.JLabel();
        contact_phone_text = new javax.swing.JLabel();
        contact_email_text = new javax.swing.JLabel();
        contact_location_name = new javax.swing.JLabel();
        contact_phone_label = new javax.swing.JLabel();
        contact_email_label = new javax.swing.JLabel();
        contact_notes_panel = new javax.swing.JPanel();
        new_contact_button = new javax.swing.JButton();
        edit_contact_button = new javax.swing.JButton();
        project_tab = new javax.swing.JPanel();
        location_tab = new javax.swing.JPanel();
        location_list_scroll_pane = new javax.swing.JScrollPane();
        location_list_area = new javax.swing.JTextArea();
        location_details_panel = new javax.swing.JPanel();
        location_name_label = new javax.swing.JLabel();
        location_street_2_label = new javax.swing.JLabel();
        location_building_label = new javax.swing.JLabel();
        location_street_1_label = new javax.swing.JLabel();
        location_country_label = new javax.swing.JLabel();
        location_city_label = new javax.swing.JLabel();
        location_state_label = new javax.swing.JLabel();
        location_zip_label = new javax.swing.JLabel();
        location_notes_panel = new javax.swing.JPanel();
        new_location_button = new javax.swing.JButton();
        edit_location_button = new javax.swing.JButton();
        button_panel = new javax.swing.JPanel();
        cancel_button = new javax.swing.JButton();
        save_button = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Account Details"));

        account_name_panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Name:"));

        javax.swing.GroupLayout account_name_panelLayout = new javax.swing.GroupLayout(account_name_panel);
        account_name_panel.setLayout(account_name_panelLayout);
        account_name_panelLayout.setHorizontalGroup(
            account_name_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(account_name_panelLayout.createSequentialGroup()
                .addComponent(account_name_field)
                .addContainerGap())
        );
        account_name_panelLayout.setVerticalGroup(
            account_name_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(account_name_panelLayout.createSequentialGroup()
                .addComponent(account_name_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane4.setViewportView(jTextArea3);

        javax.swing.GroupLayout account_detail_tabLayout = new javax.swing.GroupLayout(account_detail_tab);
        account_detail_tab.setLayout(account_detail_tabLayout);
        account_detail_tabLayout.setHorizontalGroup(
            account_detail_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(account_detail_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addContainerGap())
        );
        account_detail_tabLayout.setVerticalGroup(
            account_detail_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(account_detail_tabLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        account_details_pane.addTab("Details", account_detail_tab);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        create_task_button.setText("Create Task");

        jLabel1.setText("Task Status:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Upcoming", "Active", "Completed", "Blocked" }));

        javax.swing.GroupLayout task_tabLayout = new javax.swing.GroupLayout(task_tab);
        task_tab.setLayout(task_tabLayout);
        task_tabLayout.setHorizontalGroup(
            task_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(task_tabLayout.createSequentialGroup()
                .addGroup(task_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(task_tabLayout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(create_task_button))
                    .addGroup(task_tabLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, task_tabLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        task_tabLayout.setVerticalGroup(
            task_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(task_tabLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(task_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(create_task_button)
                .addContainerGap())
        );

        account_details_pane.addTab("Tasks", task_tab);

        contact_list_area.setColumns(20);
        contact_list_area.setRows(5);
        contact_list_scroll.setViewportView(contact_list_area);

        contact_details_panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        contact_first_name_label.setText("First Name");

        contact_last_name_label.setText("Last Name");

        contact_office_text.setText("Office:");

        contact_phone_text.setText("Phone:");

        contact_email_text.setText("Email:");

        contact_location_name.setText("Location_name");

        contact_phone_label.setText("phone_number");

        contact_email_label.setText("email_address");

        contact_notes_panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Notes:"));

        javax.swing.GroupLayout contact_notes_panelLayout = new javax.swing.GroupLayout(contact_notes_panel);
        contact_notes_panel.setLayout(contact_notes_panelLayout);
        contact_notes_panelLayout.setHorizontalGroup(
            contact_notes_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        contact_notes_panelLayout.setVerticalGroup(
            contact_notes_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout contact_details_panelLayout = new javax.swing.GroupLayout(contact_details_panel);
        contact_details_panel.setLayout(contact_details_panelLayout);
        contact_details_panelLayout.setHorizontalGroup(
            contact_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contact_details_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contact_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contact_notes_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(contact_details_panelLayout.createSequentialGroup()
                        .addGroup(contact_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contact_phone_text)
                            .addComponent(contact_email_text))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(contact_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(contact_phone_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(contact_email_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(contact_details_panelLayout.createSequentialGroup()
                        .addComponent(contact_first_name_label, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(contact_last_name_label, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                    .addGroup(contact_details_panelLayout.createSequentialGroup()
                        .addComponent(contact_office_text)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(contact_location_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        contact_details_panelLayout.setVerticalGroup(
            contact_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contact_details_panelLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(contact_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contact_first_name_label)
                    .addComponent(contact_last_name_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contact_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contact_office_text)
                    .addComponent(contact_location_name))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contact_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contact_phone_text)
                    .addComponent(contact_phone_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contact_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contact_email_text)
                    .addComponent(contact_email_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contact_notes_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        new_contact_button.setText("New");

        edit_contact_button.setText("Edit");

        javax.swing.GroupLayout contact_tabLayout = new javax.swing.GroupLayout(contact_tab);
        contact_tab.setLayout(contact_tabLayout);
        contact_tabLayout.setHorizontalGroup(
            contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contact_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contact_list_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contact_details_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(contact_tabLayout.createSequentialGroup()
                        .addComponent(new_contact_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(edit_contact_button)))
                .addContainerGap())
        );
        contact_tabLayout.setVerticalGroup(
            contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contact_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contact_list_scroll)
                    .addGroup(contact_tabLayout.createSequentialGroup()
                        .addComponent(contact_details_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(contact_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(new_contact_button)
                            .addComponent(edit_contact_button))
                        .addGap(0, 6, Short.MAX_VALUE))))
        );

        account_details_pane.addTab("Contacts", contact_tab);

        javax.swing.GroupLayout project_tabLayout = new javax.swing.GroupLayout(project_tab);
        project_tab.setLayout(project_tabLayout);
        project_tabLayout.setHorizontalGroup(
            project_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 363, Short.MAX_VALUE)
        );
        project_tabLayout.setVerticalGroup(
            project_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 292, Short.MAX_VALUE)
        );

        account_details_pane.addTab("Projects", project_tab);

        location_list_area.setColumns(20);
        location_list_area.setRows(5);
        location_list_scroll_pane.setViewportView(location_list_area);

        location_details_panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        location_name_label.setText("Location Name");

        location_street_2_label.setText("Street Address 2");

        location_building_label.setText("Building Name");

        location_street_1_label.setText("Street Address 1");

        location_country_label.setText("COUNTRY");

        location_city_label.setText("City");

        location_state_label.setText("State");

        location_zip_label.setText("ZIP");

        location_notes_panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Notes:"));

        javax.swing.GroupLayout location_notes_panelLayout = new javax.swing.GroupLayout(location_notes_panel);
        location_notes_panel.setLayout(location_notes_panelLayout);
        location_notes_panelLayout.setHorizontalGroup(
            location_notes_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        location_notes_panelLayout.setVerticalGroup(
            location_notes_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 73, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout location_details_panelLayout = new javax.swing.GroupLayout(location_details_panel);
        location_details_panel.setLayout(location_details_panelLayout);
        location_details_panelLayout.setHorizontalGroup(
            location_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(location_details_panelLayout.createSequentialGroup()
                .addGroup(location_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(location_details_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(location_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(location_name_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(location_street_2_label, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(location_building_label, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(location_street_1_label, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(location_country_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(location_details_panelLayout.createSequentialGroup()
                                .addComponent(location_city_label, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(location_state_label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(location_zip_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(location_notes_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        location_details_panelLayout.setVerticalGroup(
            location_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(location_details_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(location_name_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(location_building_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(location_street_1_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(location_street_2_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(location_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(location_city_label)
                    .addComponent(location_state_label)
                    .addComponent(location_zip_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(location_country_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(location_notes_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        new_location_button.setText("New");

        edit_location_button.setText("Edit");

        javax.swing.GroupLayout location_tabLayout = new javax.swing.GroupLayout(location_tab);
        location_tab.setLayout(location_tabLayout);
        location_tabLayout.setHorizontalGroup(
            location_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(location_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(location_list_scroll_pane, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(location_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(location_details_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(location_tabLayout.createSequentialGroup()
                        .addComponent(new_location_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(edit_location_button)))
                .addContainerGap())
        );
        location_tabLayout.setVerticalGroup(
            location_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(location_tabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(location_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(location_list_scroll_pane)
                    .addGroup(location_tabLayout.createSequentialGroup()
                        .addComponent(location_details_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(location_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(new_location_button)
                            .addComponent(edit_location_button))
                        .addContainerGap())))
        );

        account_details_pane.addTab("Locations", location_tab);

        cancel_button.setText("Cancel");

        save_button.setText("Save");

        javax.swing.GroupLayout button_panelLayout = new javax.swing.GroupLayout(button_panel);
        button_panel.setLayout(button_panelLayout);
        button_panelLayout.setHorizontalGroup(
            button_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_panelLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(cancel_button)
                .addGap(63, 63, 63)
                .addComponent(save_button)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        button_panelLayout.setVerticalGroup(
            button_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, button_panelLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(button_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel_button)
                    .addComponent(save_button))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(account_name_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(account_details_pane)
                    .addComponent(button_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(account_name_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(account_details_pane, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel account_detail_tab;
    private javax.swing.JTabbedPane account_details_pane;
    private javax.swing.JTextField account_name_field;
    private javax.swing.JPanel account_name_panel;
    private javax.swing.JPanel button_panel;
    private javax.swing.JButton cancel_button;
    private javax.swing.JPanel contact_details_panel;
    private javax.swing.JLabel contact_email_label;
    private javax.swing.JLabel contact_email_text;
    private javax.swing.JLabel contact_first_name_label;
    private javax.swing.JLabel contact_last_name_label;
    private javax.swing.JTextArea contact_list_area;
    private javax.swing.JScrollPane contact_list_scroll;
    private javax.swing.JLabel contact_location_name;
    private javax.swing.JPanel contact_notes_panel;
    private javax.swing.JLabel contact_office_text;
    private javax.swing.JLabel contact_phone_label;
    private javax.swing.JLabel contact_phone_text;
    private javax.swing.JPanel contact_tab;
    private javax.swing.JButton create_task_button;
    private javax.swing.JButton edit_contact_button;
    private javax.swing.JButton edit_location_button;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JLabel location_building_label;
    private javax.swing.JLabel location_city_label;
    private javax.swing.JLabel location_country_label;
    private javax.swing.JPanel location_details_panel;
    private javax.swing.JTextArea location_list_area;
    private javax.swing.JScrollPane location_list_scroll_pane;
    private javax.swing.JLabel location_name_label;
    private javax.swing.JPanel location_notes_panel;
    private javax.swing.JLabel location_state_label;
    private javax.swing.JLabel location_street_1_label;
    private javax.swing.JLabel location_street_2_label;
    private javax.swing.JPanel location_tab;
    private javax.swing.JLabel location_zip_label;
    private javax.swing.JButton new_contact_button;
    private javax.swing.JButton new_location_button;
    private javax.swing.JPanel project_tab;
    private javax.swing.JButton save_button;
    private javax.swing.JPanel task_tab;
    // End of variables declaration//GEN-END:variables
}
