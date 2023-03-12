//===================================================================
// ListItem.java
// 	Description:
// 		Definition for how items will be displayed in a list
//===================================================================

package com.socialvagrancy.taskmanager.client.ui.component;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

public class ListItem extends JLabel
{
	private String id;
	private Status status;
	private ActionListener item_clicked_listener;
	private MouseListener clickable_listener;


	private enum Status {
		ACTIVE(0, 0, 255), // Blue
		INACTIVE(204, 204, 204); // Light Gray

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
		public static Status fromBoolean(boolean state)
		{
			if(state)
			{
				return Status.ACTIVE;
			}
			else
			{
				return Status.INACTIVE;
			}
		}
	}

	public ListItem(String name, boolean account_status, ActionListener item_clicked)
	{
		status = Status.fromBoolean(account_status);

		// Configure listeners
		item_clicked_listener = item_clicked;



		// Configure the JLabel
		this.setText(name);
		this.setForeground(status.color());
	}

}
