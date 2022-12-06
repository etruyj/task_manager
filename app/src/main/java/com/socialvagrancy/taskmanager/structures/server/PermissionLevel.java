//===================================================================
// PermissionLevel.java
// 	Description:
// 		Defines the permission levels for a user.
//===================================================================

package com.socialvagrancy.taskmanager.structure.server;

public enum PermissionLevel
{
	ACCOUNT_MONITOR(0),
	ACCOUNT_USER(1),
	ACCOUNT_ADMIN(2),
	ORGANIZATION_MONITOR(3),
	ORGANIZATION_USER(4),
	ORGANIZATION_ADMIN(5),
	DATABASE_ADMIN(6);

	private int level;

	private PermissionLevel(int priv) { this.level = priv; }
	public int level() { return level; }

	public boolean checkPermissions(PermissionLevel toCheck)
	{
		if(toCheck.level() >= this.level)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
