package me.ash.reader.ui.page.settings.color.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Segment
import androidx.compose.material.icons.rounded.Title
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.ash.reader.R
import me.ash.reader.data.model.preference.*
import me.ash.reader.ui.component.ReadingThemePrev
import me.ash.reader.ui.component.base.*
import me.ash.reader.ui.page.common.RouteName
import me.ash.reader.ui.page.settings.SettingItem
import me.ash.reader.ui.theme.palette.onLight

@Composable
fun ReadingStylePage(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var readingTheme = LocalReadingTheme.current
    val darkTheme = LocalReadingDarkTheme.current
    val darkThemeNot = !darkTheme
    val fonts = LocalReadingFonts.current

    var fontsDialogVisible by remember { mutableStateOf(false) }

    RYScaffold(
        containerColor = MaterialTheme.colorScheme.surface onLight MaterialTheme.colorScheme.inverseOnSurface,
        navigationIcon = {
            FeedbackIconButton(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onSurface
            ) {
                navController.popBackStack()
            }
        },
        content = {
            LazyColumn {
                item {
                    DisplayText(text = stringResource(R.string.reading_page), desc = "")
                }

                // Preview
                item {
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.width(24.dp))
                        ReadingThemePreference.values.map {
                            ReadingThemePrev(selected = readingTheme, theme = it) {
                                it.put(context, scope)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Spacer(modifier = Modifier.width((24 - 8).dp))
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                MaterialTheme.colorScheme.inverseOnSurface
                                        onLight MaterialTheme.colorScheme.surface.copy(0.7f)
                            )
                            .clickable { },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // General
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = stringResource(R.string.general)
                    )
                    SettingItem(
                        title = stringResource(R.string.dark_reading_theme),
                        desc = darkTheme.toDesc(context),
                        separatedActions = true,
                        onClick = {
                            navController.navigate(RouteName.READING_DARK_THEME) {
                                launchSingleTop = true
                            }
                        },
                    ) {
                        RYSwitch(
                            activated = darkTheme.isDarkTheme()
                        ) {
                            darkThemeNot.put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.reading_fonts),
                        desc = fonts.toDesc(context),
                        onClick = { fontsDialogVisible = true },
                    ) {}
                    SettingItem(
                        title = stringResource(R.string.bionic_reading),
                        separatedActions = true,
                        onClick = {
//                            (!articleListDesc).put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = true) {
//                            (!articleListDesc).put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.auto_hide_toolbars),
                        onClick = {
//                            (!articleListDesc).put(context, scope)
                        },
                    ) {
                        RYSwitch(activated = false) {
//                            (!articleListDesc).put(context, scope)
                        }
                    }
                    SettingItem(
                        title = stringResource(R.string.rearrange_buttons),
                        onClick = {},
                    ) {}
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Advanced
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = stringResource(R.string.advanced)
                    )
                    SettingItem(
                        title = stringResource(R.string.title),
                        desc = stringResource(R.string.title_desc),
                        icon = Icons.Rounded.Title,
                        onClick = {
                            navController.navigate(RouteName.READING_PAGE_TITLE) {
                                launchSingleTop = true
                            }
                        },
                    ) {}
                    SettingItem(
                        title = stringResource(R.string.text),
                        desc = stringResource(R.string.text_desc),
                        icon = Icons.Rounded.Segment,
                        onClick = {
                            navController.navigate(RouteName.READING_PAGE_TEXT) {
                                launchSingleTop = true
                            }
                        },
                    ) {}
                    SettingItem(
                        title = stringResource(R.string.images),
                        desc = stringResource(R.string.images_desc),
                        icon = Icons.Outlined.Image,
                        onClick = {
                            navController.navigate(RouteName.READING_PAGE_IMAGE) {
                                launchSingleTop = true
                            }
                        },
                    ) {}
                    SettingItem(
                        title = stringResource(R.string.videos),
                        desc = stringResource(R.string.videos_desc),
                        icon = Icons.Outlined.Movie,
                        onClick = {
                            navController.navigate(RouteName.READING_PAGE_VIDEO) {
                                launchSingleTop = true
                            }
                        },
                    ) {}
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                }
            }
        }
    )

    RadioDialog(
        visible = fontsDialogVisible,
        title = stringResource(R.string.reading_fonts),
        options = ReadingFontsPreference.values.map {
            RadioDialogOption(
                text = it.toDesc(context),
                style = TextStyle(fontFamily = it.asFontFamily()),
                selected = it == fonts,
            ) {
                it.put(context, scope)
            }
        }
    ) {
        fontsDialogVisible = false
    }
}
