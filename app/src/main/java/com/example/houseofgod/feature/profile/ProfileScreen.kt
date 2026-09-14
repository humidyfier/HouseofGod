package com.example.houseofgod.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.houseofgod.core.designsystem.DarkElevatedSurface
import com.example.houseofgod.core.designsystem.DarkOutlineVariant
import com.example.houseofgod.core.designsystem.DarkSurfaceVariant
import com.example.houseofgod.core.designsystem.ErrorRed
import com.example.houseofgod.core.designsystem.RadiantGold
import com.example.houseofgod.core.designsystem.RadiantGoldContainer
import com.example.houseofgod.core.designsystem.RadiantGoldContainerText
import com.example.houseofgod.core.designsystem.SanctuarySlate
import com.example.houseofgod.core.designsystem.TextHighContrast
import com.example.houseofgod.core.designsystem.TextMediumContrast
import com.example.houseofgod.core.designsystem.TextSubtle

/**
 * Profile & Church Info screen matching PRD scope:
 * User preferences (theme, language, notifications), Bangalore church info with service times/map,
 * and data management options.
 */
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onOpenMapClick: () -> Unit = {},
    onContactChurchClick: () -> Unit = {}
) {
    var isDarkTheme by rememberSaveable { mutableStateOf(true) }
    var dailyVerseNotifications by rememberSaveable { mutableStateOf(true) }
    var preferredLanguage by rememberSaveable { mutableStateOf("English") }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header & Member Profile Card
        item {
            UserProfileCard()
        }

        // Church Information & Bangalore Location
        item {
            ChurchInfoCard(
                onOpenMapClick = onOpenMapClick,
                onContactChurchClick = onContactChurchClick
            )
        }

        // App Preferences & Settings
        item {
            SettingsCard(
                isDarkTheme = isDarkTheme,
                onThemeToggle = { isDarkTheme = it },
                dailyVerseNotifications = dailyVerseNotifications,
                onNotificationsToggle = { dailyVerseNotifications = it },
                preferredLanguage = preferredLanguage,
                onLanguageChange = { preferredLanguage = it }
            )
        }

        // Data & Privacy Management
        item {
            DataManagementCard()
        }
    }
}

@Composable
fun UserProfileCard(
    modifier: Modifier = Modifier,
    userName: String = "Church Member",
    userRole: String = "Bangalore Congregation",
    phoneNumber: String? = "+91 98765 43210"
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkElevatedSurface),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(RadiantGoldContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = RadiantGold,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextHighContrast
                )
                Text(
                    text = userRole,
                    style = MaterialTheme.typography.labelSmall,
                    color = RadiantGold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (phoneNumber != null) "Callback phone: $phoneNumber" else "Add phone number for pastoral callback",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (phoneNumber != null) TextMediumContrast else SanctuarySlate
                )
            }
        }
    }
}

@Composable
fun ChurchInfoCard(
    modifier: Modifier = Modifier,
    onOpenMapClick: () -> Unit = {},
    onContactChurchClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkElevatedSurface),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "HOUSE OF GOD CHURCH, BANGALORE",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = RadiantGold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Church Details & Gatherings",
                style = MaterialTheme.typography.titleMedium,
                color = TextHighContrast
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Service Times
            Text(
                text = "Sunday Worship Times:",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = TextHighContrast
            )
            Text(
                text = "• First Service: 9:30 AM (Tamil & English)\n• Second Service: 6:00 PM (Youth & Praise)",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMediumContrast
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Location
            Text(
                text = "Location:",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = TextHighContrast
            )
            Text(
                text = "House of God Sanctuary, Bangalore Central, Karnataka 560001",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMediumContrast
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(
                    onClick = onOpenMapClick,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = RadiantGold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Google Maps",
                        style = MaterialTheme.typography.labelMedium,
                        color = RadiantGold
                    )
                }

                OutlinedButton(
                    onClick = onContactChurchClick,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = SanctuarySlate
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Contact Office",
                        style = MaterialTheme.typography.labelMedium,
                        color = SanctuarySlate
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsCard(
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit,
    dailyVerseNotifications: Boolean,
    onNotificationsToggle: (Boolean) -> Unit,
    preferredLanguage: String,
    onLanguageChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkElevatedSurface),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "PREFERENCES",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = RadiantGold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "App Settings",
                style = MaterialTheme.typography.titleMedium,
                color = TextHighContrast
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Dark Theme Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Dark Theme",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextHighContrast
                    )
                    Text(
                        text = "Calm, reverent dark aesthetic",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSubtle
                    )
                }
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = onThemeToggle,
                    colors = SwitchDefaults.colors(checkedThumbColor = RadiantGold)
                )
            }

            HorizontalDivider(
                color = DarkOutlineVariant,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            // Notifications Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Verse of the Day Notifications",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextHighContrast
                    )
                    Text(
                        text = "Receive daily morning scripture reminder",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSubtle
                    )
                }
                Switch(
                    checked = dailyVerseNotifications,
                    onCheckedChange = onNotificationsToggle,
                    colors = SwitchDefaults.colors(checkedThumbColor = RadiantGold)
                )
            }

            HorizontalDivider(
                color = DarkOutlineVariant,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            // Language Selector
            Column {
                Text(
                    text = "Preferred Scripture Language",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextHighContrast
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("English", "தமிழ்", "ಕನ್ನಡ", "हिन्दी").forEach { lang ->
                        val isSelected = preferredLanguage == lang
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) RadiantGoldContainer else DarkSurfaceVariant,
                            modifier = Modifier.clickable { onLanguageChange(lang) }
                        ) {
                            Text(
                                text = lang,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = if (isSelected) RadiantGoldContainerText else TextMediumContrast,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DataManagementCard(
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkElevatedSurface),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "DATA & PRIVACY",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = TextSubtle
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Clear My Prayer History",
                style = MaterialTheme.typography.bodyMedium,
                color = SanctuarySlate,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* action */ }
                    .padding(vertical = 6.dp)
            )

            Text(
                text = "Delete Account & Data",
                style = MaterialTheme.typography.bodyMedium,
                color = ErrorRed,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* action */ }
                    .padding(vertical = 6.dp)
            )
        }
    }
}
