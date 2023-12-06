package com.example.utocka

import MyViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.utocka.ui.theme.UtockaTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            val myViewModel: MyViewModel by viewModels()
            UtockaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    var selectedTabIndex by remember {
                        mutableIntStateOf(0)
                    }
                    val pagerState = rememberPagerState {
                        tabItems.size
                    }
                    var username by remember {
                        mutableStateOf("Имя")
                    }

                    LaunchedEffect(key1 = selectedTabIndex) {
                        if (selectedTabIndex == 1) {
                            myViewModel.loadUserData("hey")
                        }
                    }

                    LaunchedEffect(selectedTabIndex) {
                        pagerState.animateScrollToPage(selectedTabIndex)
                    }
                    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                        if (!pagerState.isScrollInProgress) {
                            selectedTabIndex = pagerState.currentPage
                        }
                    }

                    Column(modifier = Modifier.fillMaxSize()) {
                        CustomTabRow(
                            selectedTabIndex = selectedTabIndex,
                            tabItems = tabItems,
                            onTabSelected = { index -> selectedTabIndex = index }
                        )

                        HorizontalPager(
                            state = pagerState, modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (pagerState.currentPage == 0) {
                                    VerticalGrid(imageResourceList)
                                } else {
                                    ColumnWithData(myViewModel)
                                }
                            }

                        }
                        BottomAppBarContent(Modifier.weight(0.075f), username)
                    }
                }
            }
        }
    }
}