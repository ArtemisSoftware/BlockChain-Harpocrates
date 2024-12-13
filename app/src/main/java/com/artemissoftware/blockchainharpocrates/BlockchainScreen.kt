package com.artemissoftware.blockchainharpocrates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.blockchainharpocrates.composables.BlockCard
import com.artemissoftware.blockchainharpocrates.composables.ProofOfWorkDialog
import com.artemissoftware.blockchainharpocrates.models.Block
import com.artemissoftware.blockchainharpocrates.ui.theme.BlockchainHarpocratesTheme

@Composable
fun BlockchainScreen(viewModel: BlockchainViewModel) {
    val state = viewModel.state.collectAsState().value

    BlockchainContent(
        event = viewModel::onTriggerEvent,
        state = state
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BlockchainContent(
    event: (BlockchainEvent) -> Unit,
    state: BlockchainState,
) {
    val controller = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        stringResource(id = R.string.block_chain),
                        maxLines = 1,
                    )
                },
                actions = {
                    IconButton(
                        onClick = { event.invoke(BlockchainEvent.OpenDialog) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
        content = { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ){

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.blocks){ block ->
                        BlockCard(
                            block = block,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                ProofOfWorkDialog(
                    pow = state.pow,
                    onValueChange = { event.invoke(BlockchainEvent.UpdatePoW(it)) },
                    onConfirmClick = { event.invoke(BlockchainEvent.SavePoW) },
                    onCloseClick = { event.invoke(BlockchainEvent.OpenDialog) },
                    showDialog = state.showDialog
                )

                if(state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = Color.Green,
                    )
                }
            }

        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(
                            enabled = state.message.isNotEmpty(),
                            onClick = {
                                controller?.hide()
                                event.invoke(BlockchainEvent.UpdateBlockChain)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    value = state.message,
                    onValueChange = { event.invoke(BlockchainEvent.UpdateMessage(it)) },
                    label = { Text("Label") }
                )
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
private fun BlockchainContentPreview() {
    BlockchainHarpocratesTheme {
        BlockchainContent(
            event = {},
            state = BlockchainState(
                difficulty = 1,
                blocks = listOf(
                    Block(
                        previousHash = "previousHash",
                        timestamp = 1733828573L,
                        index = 1,
                        data = "data"
                    )
                ),
                showDialog = false
            )
        )
    }
}