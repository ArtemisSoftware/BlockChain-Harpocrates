package com.artemissoftware.blockchainharpocrates.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.blockchainharpocrates.R
import com.artemissoftware.blockchainharpocrates.ui.theme.BlockchainHarpocratesTheme

@Composable
fun ChainCard(
    modifier: Modifier = Modifier
) {

    Card(modifier = modifier) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(stringResource(id = R.string.block))

            Detail(
                title = R.string.previous_hash,
                description = "",
                modifier = Modifier.fillMaxWidth()
            )

            Detail(
                title = R.string.time_stamp,
                description = "",
                modifier = Modifier.fillMaxWidth()
            )

            Detail(
                title = R.string.block_data,
                description = "",
                modifier = Modifier.fillMaxWidth()
            )

            Detail(
                title = R.string.block_hash,
                description = "",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun Detail(
    @StringRes title: Int,
    description: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            stringResource(id = title),
            fontWeight = FontWeight.Bold
        )
        Text(description)
    }
}

@Preview(showBackground = true)
@Composable
private fun ChainCardPreview() {
    BlockchainHarpocratesTheme {
        ChainCard(
            modifier = Modifier.fillMaxWidth()
        )
    }
}
