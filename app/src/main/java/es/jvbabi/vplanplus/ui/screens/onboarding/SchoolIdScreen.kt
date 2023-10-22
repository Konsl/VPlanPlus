package es.jvbabi.vplanplus.ui.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import es.jvbabi.vplanplus.R
import es.jvbabi.vplanplus.domain.usecase.SchoolIdCheckResult
import es.jvbabi.vplanplus.util.ErrorType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingSchoolIdScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.onboarding_schoolIdTitle),
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(text = stringResource(id = R.string.onboarding_schoolIdText))
                TextField(
                    value = state.schoolId,
                    singleLine = true,

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { viewModel.onSchoolIdInput(it.take(8)) },
                    label = { Text(text = stringResource(id = R.string.onboarding_schoolIdHint)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )

                when (state.currentErrorType) {
                    ErrorType.NOT_FOUND -> {
                        Text(
                            text = stringResource(id = R.string.onboarding_schoolIdNotFound),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                    ErrorType.OTHER -> {
                        Text(
                            text = stringResource(id = R.string.unknownError),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }

                    else -> {}
                }
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.onSchoolIdSubmit()
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                enabled = !state.isLoading && state.schoolIdState == SchoolIdCheckResult.SYNTACTICALLY_CORRECT
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (state.isLoading) CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                            .padding(6.dp)
                    )
                    else Text(text = stringResource(id = R.string.next))
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SchoolIdScreenPreview() {
    OnboardingSchoolIdScreen(navController = rememberNavController())
}