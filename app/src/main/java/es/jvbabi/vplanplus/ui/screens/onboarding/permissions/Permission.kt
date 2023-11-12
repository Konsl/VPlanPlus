package es.jvbabi.vplanplus.ui.screens.onboarding.permissions

import android.Manifest
import android.os.Build
import es.jvbabi.vplanplus.R

/**
 * Data class that represents a permission
 * @param type The type of the permission (android.Manifest.permission)
 * @param name The name of the permission (R.string)
 * @param description The description of the permission (R.string)
 */
data class Permission(
    val type: String,
    val name: Int,
    val description: Int,
) {
    companion object {
        val permissions = listOfNotNull(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                Permission(
                    type = Manifest.permission.POST_NOTIFICATIONS,
                    name = R.string.permission_notificationsTitle,
                    description = R.string.permission_notificationsText
                )
            else null,

            Permission(
                type = Manifest.permission.WRITE_CALENDAR,
                name = R.string.permission_writeCalendarTitle,
                description = R.string.permission_writeCalendarText
            ),

            Permission(
                type = Manifest.permission.READ_CALENDAR,
                name = R.string.permission_readCalendarTitle,
                description = R.string.permission_readCalendarText
            ),
        )
    }
}
