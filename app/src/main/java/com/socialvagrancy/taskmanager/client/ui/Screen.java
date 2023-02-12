//===================================================================
// Screen.java
// 	Description:
// 		An interface for the functions each of the screens
// 		should have and override.
//===================================================================

package com.socialvagrancy.taskmanager.client.ui;

import com.socialvagrancy.taskmanager.client.ui.Controller;

public interface Screen
{
        // Refreshes the information on the screen
	// either to a fresh state (NEW) or by the specified ID
	public void refresh(String id);
        
        public void setApiController(Controller api);
}
