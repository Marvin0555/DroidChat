package com.example.droidchat.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.droidchat.R
import com.example.droidchat.ui.extension.getVisualTransformationForPassword
import com.example.droidchat.ui.theme.ColorError
import com.example.droidchat.ui.theme.DroidChatTheme
import com.example.droidchat.ui.theme.Surface

@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes
    leadingIcon: Int ? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    errorMessage: String? = null,
    placeholder: String = "",
) {

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
    ){
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = placeholder)
            },
            leadingIcon = {
                leadingIcon?.let {
                    Icon(
                        painter = painterResource(id = leadingIcon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            trailingIcon = {
                if(keyboardType == KeyboardType.Password && value.isNotEmpty()) {
                    val visibilityIcon = if (passwordVisible) {
                        R.drawable.ic_visibility
                    } else {
                        R.drawable.ic_visibility_off
                    }
                    Icon(
                        painter = painterResource(id = visibilityIcon),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            passwordVisible = !passwordVisible
                        },
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            visualTransformation = keyboardType.getVisualTransformationForPassword(passwordVisible),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            singleLine = true,
            shape = CircleShape,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (errorMessage !=null) {
                    ColorError
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
            )

        )
        errorMessage?.let {
            Text(
                text = it,
                color = ColorError,
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview
@Composable
private fun PrimaryTextFieldPreview() {
    DroidChatTheme {
        PrimaryTextField(
            value = "123",
            onValueChange = {},
            leadingIcon = R.drawable.ic_lock,
            keyboardType = KeyboardType.Password,
            errorMessage = ""
        )
    }
}

@Preview
@Composable
private fun PrimaryTextFielErrordPreview() {
    DroidChatTheme {
        PrimaryTextField(
            value = "123",
            onValueChange = {},
            placeholder = "E-mail",
            leadingIcon = R.drawable.ic_envelope,
            keyboardType = KeyboardType.Email,
            errorMessage = "E-mail inválido"
        )
    }
}

@Preview
@Composable
private fun PrimaryTextFielErrordPasswordPreview() {
    DroidChatTheme {
        PrimaryTextField(
            value = "123",
            onValueChange = {},
            placeholder = "E-mail",
            leadingIcon = R.drawable.ic_envelope,
            keyboardType = KeyboardType.Password,
            errorMessage = "Senha errada"
        )
    }
}