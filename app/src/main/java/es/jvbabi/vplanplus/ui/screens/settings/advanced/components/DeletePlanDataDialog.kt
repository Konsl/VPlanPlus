package es.jvbabi.vplanplus.ui.screens.settings.advanced.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import es.jvbabi.vplanplus.R
import es.jvbabi.vplanplus.ui.common.YesNoDialog

@Composable
fun DeletePlanDataDialog(
    onOk: () -> Unit,
    onDismiss: () -> Unit
) {
    YesNoDialog(
        icon = Icons.Default.DeleteForever,
        title = stringResource(id = R.string.advancedSettings_deletePlanDataDialogTitle),
        message = stringResource(id = R.string.advancedSettings_deletePlanDataDialogText),
        onYes = { onOk() },
        onDismiss = { onDismiss() },
        onNo = { onDismiss() }
    )
}

@Preview
@Composable
private fun DeletePlanDataDialogPreview() {
    DeletePlanDataDialog({}, {})
}