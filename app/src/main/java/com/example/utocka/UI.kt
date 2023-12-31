package com.example.utocka

import MyViewModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun ImageForSending(imageResId: Int, onImageClick: (Int) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        ConfirmationDialog(onDismissRequest = { showDialog = false }, onConfirmation = {
            onImageClick(imageResId)
            showDialog = false
        }, dialogText = "Вам точно выпала эта роль?"
        )
    }

    Image(painter = painterResource(id = imageResId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clickable { showDialog = true }
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(12.dp)))
}

@Composable
fun VerticalGrid(imageIdList: List<Int>, viewModel: MyViewModel = viewModel()) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ) {
        items(imageIdList) { imageResId ->
            ImageForSending(imageResId = imageResId, onImageClick = viewModel::handleImageClick)
        }
    }
}

@Composable
fun ColumnElement(imageResId: Int, count: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(5.dp)
                .clip(shape = RoundedCornerShape(12.dp))
        )
        Text(
            text = "Выпадений: $count", fontSize = 30.sp
        )
    }
}

@Composable
fun ColumnWithData(viewModel: MyViewModel) {
    val data by viewModel.userData.observeAsState(emptyList())
    Log.i("info", "Подписка")
    LazyColumn(Modifier.fillMaxSize()) {
        items(data) { (imageResId, count) ->
            ColumnElement(imageResId = imageResId, count = count)
        }
    }
}

@Composable
fun CustomTabRow(selectedTabIndex: Int, tabItems: List<TabItem>, onTabSelected: (Int) -> Unit) {
    TabRow(selectedTabIndex = selectedTabIndex) {
        tabItems.forEachIndexed { index, item ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onTabSelected(index) },
                text = { Text(text = item.title) },
//                icon = {
//                    Icon(
//                        imageVector = if (index == selectedTabIndex) {
//                            item.selectedIcon
//                        } else item.unselectedIcon,
//                        contentDescription = item.title
//                    )
//                }
            )
        }
    }
}

@Composable
fun BottomAppBarContent(modifier: Modifier, text:String) {
    BottomAppBar(modifier = modifier
    ) {
        IconButton(onClick = {
            // Обработка нажатия на кнопку
        }

        ) {
            Icon(imageVector = Icons.Default.Create, contentDescription = null)
        }
        Text(text = text)
    }
}
