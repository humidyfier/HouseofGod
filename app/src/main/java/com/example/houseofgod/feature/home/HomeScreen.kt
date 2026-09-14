
package com.example.houseofgod.feature.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import com.example.houseofgod.core.designsystem.DarkElevatedSurface
import com.example.houseofgod.core.designsystem.RadiantGold
import com.example.houseofgod.core.designsystem.RadiantGoldContainer
import com.example.houseofgod.core.designsystem.RadiantGoldContainerText
import com.example.houseofgod.core.designsystem.SanctuarySlate
import com.example.houseofgod.core.designsystem.TextHighContrast
import com.example.houseofgod.core.designsystem.TextMediumContrast

/**
 * Home Spiritual Dashboard screen displaying the Verse of the Day hero,
 * Daily Guided Reflection, and Upcoming Church Events.
 * Features a subtle, slow animated motion background with a soft blurry blue orb
 * moving over a deep devotional dark baseline.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onReadChapterClick: (String) -> Unit = {},
    onReflectionClick: () -> Unit = {},
    onEventClick: () -> Unit = {}
) {
    // Subtle, slow animated motion background (6500ms - 8000ms duration for calm devotional feel)
    val infiniteTransition = rememberInfiniteTransition(label = "HomeAmbientMotion")

    val animatedCenterX by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "AmbientMotionCenterX"
    )

    val animatedCenterY by infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "AmbientMotionCenterY"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                val ambientRadialGradient = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF142236).copy(alpha = 0.75f), // Soft blurry sanctuary night blue
                        Color(0xFF0F1622).copy(alpha = 0.40f), // Smooth twilight transition
                        Color(0xFF0A0C10)                      // Deep, calm near-black baseline
                    ),
                    center = Offset(
                        x = size.width * animatedCenterX,
                        y = size.height * animatedCenterY
                    ),
                    radius = maxOf(size.width, size.height) * 0.85f
                )
                drawRect(brush = ambientRadialGradient)
            }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Welcome Header
            item {
                HomeGreetingHeader()
            }

        // Verse of the Day Hero Card
        item {
            VerseOfTheDayCard(
                onReadChapterClick = onReadChapterClick
            )
        }

        // Guided Reflection Card
        item {
            GuidedReflectionCard(
                onReflectionClick = onReflectionClick
            )
        }

        // Upcoming Event Card
        item {
            UpcomingEventCard(
                onEventClick = onEventClick
            )
        }
    }
}
}

@Composable
fun HomeGreetingHeader(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "HOUSE OF GOD",
            style = MaterialTheme.typography.labelMedium.copy(
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold
            ),
            color = RadiantGold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Welcome to worship",
            style = MaterialTheme.typography.headlineMedium,
            color = TextHighContrast
        )
    }
}

/**
 * Verse of the Day hero card featuring a devotional background image with
 * a dark vertical gradient overlay ensuring high contrast for scripture reading.
 */
@Composable
fun VerseOfTheDayCard(
    modifier: Modifier = Modifier,
    verseText: String = "For God so loved the world, that he gave his only begotten Son, that whosoever believeth in him should not perish, but have everlasting life.",
    verseReference: String = "John 3:16",
    imageUrl: String = "https://images.unsplash.com/photo-1507692049790-de58290a4334?auto=format&fit=crop&w=1200&q=80",
    onReadChapterClick: (String) -> Unit = {}
) {
    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = DarkElevatedSurface),
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image via Coil
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Verse of the Day Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Contrast Scrim: Brush.verticalGradient from transparent to 80% black
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.45f),
                                Color.Black.copy(alpha = 0.80f)
                            )
                        )
                    )
            )

            // Content Overlay
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top Tag
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Black.copy(alpha = 0.55f),
                    contentColor = RadiantGold
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = RadiantGold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "VERSE OF THE DAY",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            letterSpacing = 1.sp
                        )
                    }
                }

                // Scripture Body & Action
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "“$verseText”",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            lineHeight = 26.sp
                        ),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = verseReference,
                            style = MaterialTheme.typography.titleMedium,
                            color = RadiantGold
                        )

                        FilledTonalButton(
                            onClick = { onReadChapterClick(verseReference) },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = RadiantGoldContainer,
                                contentColor = RadiantGoldContainerText
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Read Chapter",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Guided Reflection placeholder card.
 */
@Composable
fun GuidedReflectionCard(
    modifier: Modifier = Modifier,
    title: String = "Walking in Peace",
    body: String = "Take 3 quiet minutes with the Lord to reflect on His unfailing faithfulness amidst the rush of daily life.",
    onReflectionClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DAILY GUIDED REFLECTION",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = SanctuarySlate
                )
                Text(
                    text = "3 min",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMediumContrast
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = TextHighContrast
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = TextMediumContrast
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(
                onClick = onReflectionClick,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Begin Reflection",
                    style = MaterialTheme.typography.labelMedium,
                    color = RadiantGold
                )
            }
        }
    }
}

/**
 * Upcoming Event placeholder card.
 */
@Composable
fun UpcomingEventCard(
    modifier: Modifier = Modifier,
    title: String = "Sunday Worship & Communion",
    timeAndLocation: String = "This Sunday • 9:30 AM IST • Bangalore Sanctuary",
    onEventClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                text = "UPCOMING CHURCH EVENT",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                ),
                color = RadiantGold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = TextHighContrast
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = timeAndLocation,
                style = MaterialTheme.typography.bodyMedium,
                color = TextMediumContrast
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(
                onClick = onEventClick,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "View Event Details",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextHighContrast
                )
            }
        }
    }
}
