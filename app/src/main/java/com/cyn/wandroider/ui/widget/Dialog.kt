package com.cyn.wandroider.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 *    author : cyn
 *    e-mail : cyn202104069911@163.com
 *    date   : 2023/5/8
 *    desc   :
 */
@Composable
fun PromptDialog(
    title: String,
    content: String,
    confirmText: String = "确定",
    onDismiss: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = title)
        },
        text = { Text(text = content)},
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDismiss.invoke()
            }) {
                Text(text = confirmText)
            }
        },
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    )
}