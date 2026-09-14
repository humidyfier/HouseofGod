package com.example.houseofgod.feature.prayer

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.houseofgod.core.designsystem.DarkElevatedSurface
import com.example.houseofgod.core.designsystem.DarkSurfaceVariant
import com.example.houseofgod.core.designsystem.RadiantGold
import com.example.houseofgod.core.designsystem.RadiantGoldContainer
import com.example.houseofgod.core.designsystem.RadiantGoldContainerText
import com.example.houseofgod.core.designsystem.RadiantGoldDark
import com.example.houseofgod.core.designsystem.SanctuarySlate
import com.example.houseofgod.core.designsystem.TextHighContrast
import com.example.houseofgod.core.designsystem.TextMediumContrast
import com.example.houseofgod.core.designsystem.TextSubtle

data class PrayerRequestItem(
    val id: String,
    val category: String,
    val text: String,
    val date: String,
    val isAnswered: Boolean
)

val MockPastPrayers = listOf(
    PrayerRequestItem(
        id = "prayer-1",
        category = "Health & Healing",
        text = "Praying for quick recovery of my grandmother from surgery.",
        date = "Sep 10, 2026",
        isAnswered = true
    ),
    PrayerRequestItem(
        id = "prayer-2",
        category = "Guidance",
        text = "Seeking wisdom for career transition and family relocation to Bangalore.",
        date = "Sep 12, 2026",
        isAnswered = false
    )
)

/**
 * Prayer Screen shell matching PRD scope:
 * Form with categories, pastoral contact toggle, anonymous toggle,
 * and a list of past prayer requests.
 */
@Composable
fun PrayerScreen(
    modifier: Modifier = Modifier,
    onSubmitRequest: (category: String, text: String, contactMe: Boolean, anonymous: Boolean) -> Unit = { _, _, _, _ -> }
) {
    var selectedCategory by rememberSaveable { mutableStateOf("Healing") }
    var prayerText by rememberSaveable { mutableStateOf("") }
    var contactRequested by rememberSaveable { mutableStateOf(false) }
    var isAnonymous by rememberSaveable { mutableStateOf(false) }

    val categories = listOf("Healing", "Family", "Guidance", "Thanksgiving")

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Screen Header
        item {
            Column {
                Text(
                    text = "PASTORAL CARE & INTERCESSION",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = RadiantGold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Prayer Requests",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextHighContrast
                )
            }
        }

        // New Request Form Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkElevatedSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Submit a Prayer Request",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextHighContrast
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Category Selector Pills
                    Text(
                        text = "Category",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSubtle
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.forEach { cat ->
                            val isSelected = selectedCategory == cat
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) RadiantGoldContainer else DarkSurfaceVariant,
                                modifier = Modifier.clickable { selectedCategory = cat }
                            ) {
                                Text(
                                    text = cat,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    ),
                                    color = if (isSelected) RadiantGoldContainerText else TextMediumContrast,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Request Text Area
                    OutlinedTextField(
                        value = prayerText,
                        onValueChange = { prayerText = it },
                        label = { Text("How can we pray with you?") },
                        placeholder = { Text("Write your prayer request here...") },
                        minLines = 3,
                        maxLines = 5,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RadiantGold,
                            unfocusedBorderColor = DarkSurfaceVariant,
                            focusedTextColor = TextHighContrast,
                            unfocusedTextColor = TextHighContrast,
                            focusedLabelColor = RadiantGold,
                            unfocusedLabelColor = TextSubtle
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Contact Me / Pastoral Callback Checkbox
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { contactRequested = !contactRequested }
                    ) {
                        Checkbox(
                            checked = contactRequested,
                            onCheckedChange = { contactRequested = it },
                            colors = CheckboxDefaults.colors(checkedColor = RadiantGold)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = "Request Pastoral Callback",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextHighContrast
                            )
                            Text(
                                text = "A pastor will reach out to your registered phone number",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSubtle
                            )
                        }
                    }

                    // Anonymous Checkbox
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isAnonymous = !isAnonymous }
                    ) {
                        Checkbox(
                            checked = isAnonymous,
                            onCheckedChange = { isAnonymous = it },
                            colors = CheckboxDefaults.colors(checkedColor = RadiantGold)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Submit Anonymously (Prayer Team only)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextHighContrast
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Submit Button
                    Button(
                        onClick = {
                            onSubmitRequest(selectedCategory, prayerText, contactRequested, isAnonymous)
                            prayerText = ""
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RadiantGold,
                            contentColor = RadiantGoldDark
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Submit Prayer Request",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }

        // Past Prayer Requests Header
        item {
            Text(
                text = "My Prayer History",
                style = MaterialTheme.typography.titleMedium,
                color = TextHighContrast
            )
        }

        // Past Requests List
        items(MockPastPrayers) { prayer ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = prayer.category,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = SanctuarySlate
                        )
                        Text(
                            text = if (prayer.isAnswered) "Answered" else "Active",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (prayer.isAnswered) RadiantGold else TextSubtle
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = prayer.text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextHighContrast
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = prayer.date,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSubtle
                    )
                }
            }
        }
    }
}
