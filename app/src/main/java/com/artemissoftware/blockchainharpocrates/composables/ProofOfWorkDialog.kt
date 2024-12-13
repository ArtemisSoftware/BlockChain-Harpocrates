package com.artemissoftware.blockchainharpocrates.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.blockchainharpocrates.R
import com.artemissoftware.blockchainharpocrates.ui.theme.BlockchainHarpocratesTheme

@Composable
fun ProofOfWorkDialog(
    pow: String,
    onValueChange: (String) -> Unit,
    onConfirmClick: () -> Unit,
    onCloseClick: () -> Unit,
    showDialog: Boolean
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onCloseClick,
            title = { Text(text = stringResource(id = R.string.pow)) },
            text = {
                Column {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = Color.Blue
                                ),
                                shape = RoundedCornerShape(50)
                            ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "",
                                tint = Color.Green,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                        },
                        value = pow,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { onValueChange.invoke(it) },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            },

            confirmButton = { // 6
                Button(
                    content = {
                        Text(
                            text = "Confirm",
                            color = Color.White
                        )
                    },
                    onClick = onConfirmClick
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BlockCardPreview() {
    BlockchainHarpocratesTheme {
        ProofOfWorkDialog(
            onConfirmClick = {},
            onCloseClick = {},
            onValueChange = {},
            showDialog = true,
            pow = "2",

        )
    }
}