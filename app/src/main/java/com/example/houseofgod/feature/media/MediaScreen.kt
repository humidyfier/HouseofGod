package com.example.houseofgod.feature.media

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.houseofgod.core.designsystem.DarkElevatedSurface
import com.example.houseofgod.core.designsystem.DarkSurfaceVariant
import com.example.houseofgod.core.designsystem.RadiantGold
import com.example.houseofgod.core.designsystem.SanctuarySlate
import com.example.houseofgod.core.designsystem.TextHighContrast
import com.example.houseofgod.core.designsystem.TextMediumContrast
import com.example.houseofgod.core.designsystem.TextSubtle

enum class MediaType {
    SERMON, MUSIC
}

data class MediaItem(
    val id: String,
    val title: String,
    val speakerOrArtist: String,
    val dateOrDuration: String,
    val scriptureReference: String? = null,
    val youtubeVideoId: String,
    val thumbnailUrl: String,
    val type: MediaType
)

val MockSermons = listOf(
    MediaItem(
        id = "sermon-1",
        title = "The Power of Radical Faith",
        speakerOrArtist = "Pastor John Victor",
        dateOrDuration = "Last Sunday • 42 mins",
        scriptureReference = "Hebrews 11:1-6",
        youtubeVideoId = "dQw4w9WgXcQ",
        thumbnailUrl = "https://images.unsplash.com/photo-1438232992991-995b7058bbb3?auto=format&fit=crop&w=600&q=80",
        type = MediaType.SERMON
    ),
    MediaItem(
        id = "sermon-2",
        title = "Walking in Divine Peace",
        speakerOrArtist = "Pastor Sarah Matthew",
        dateOrDuration = "2 weeks ago • 38 mins",
        scriptureReference = "Philippians 4:6-7",
        youtubeVideoId = "dQw4w9WgXcQ",
        thumbnailUrl = "https://images.unsplash.com/photo-1519491050282-cf00c82424b4?auto=format&fit=crop&w=600&q=80",
        type = MediaType.SERMON
    ),
    MediaItem(
        id = "sermon-3",
        title = "Grace Abounding in Hard Times",
        speakerOrArtist = "Dr. David Roy",
        dateOrDuration = "3 weeks ago • 45 mins",
        scriptureReference = "Romans 5:1-5",
        youtubeVideoId = "dQw4w9WgXcQ",
        thumbnailUrl = "https://images.unsplash.com/photo-1544427920-c49ccfb85579?auto=format&fit=crop&w=600&q=80",
        type = MediaType.SERMON
    )
)

val MockMusic = listOf(
    MediaItem(
        id = "music-1",
        title = "Way Maker / King of Kings (Live Worship)",
        speakerOrArtist = "House of God Worship Bangalore",
        dateOrDuration = "Live Service • 8:24 mins",
        scriptureReference = null,
        youtubeVideoId = "dQw4w9WgXcQ",
        thumbnailUrl = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?auto=format&fit=crop&w=600&q=80",
        type = MediaType.MUSIC
    ),
    MediaItem(
        id = "music-2",
        title = "Goodness of God (Acoustic Tamil & English)",
        speakerOrArtist = "House of God Worship Bangalore",
        dateOrDuration = "Devotional Session • 6:15 mins",
        scriptureReference = null,
        youtubeVideoId = "dQw4w9WgXcQ",
        thumbnailUrl = "https://images.unsplash.com/photo-1465847899084-d164df4dedc6?auto=format&fit=crop&w=600&q=80",
        type = MediaType.MUSIC
    ),
    MediaItem(
        id = "music-3",
        title = "Holy Forever - Sunday Praise",
        speakerOrArtist = "House of God Worship Bangalore",
        dateOrDuration = "Sunday Praise • 7:40 mins",
        scriptureReference = null,
        youtubeVideoId = "dQw4w9WgXcQ",
        thumbnailUrl = "https://images.unsplash.com/photo-1508700115892-45ecd05ae2ad?auto=format&fit=crop&w=600&q=80",
        type = MediaType.MUSIC
    )
)

/**
 * Media Screen skeleton featuring segmented tabs [ Sermons ] and [ Music ]
 * with state-driven toggle between list representations using Material 3 list items.
 */
@Composable
fun MediaScreen(
    modifier: Modifier = Modifier,
    onMediaItemClick: (MediaItem) -> Unit = {}
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Sermons", "Music")

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 12.dp)
        ) {
            Text(
                text = "MEDIA & WORSHIP",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = RadiantGold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Watch & Listen",
                style = MaterialTheme.typography.headlineMedium,
                color = TextHighContrast
            )
        }

        // Segmented TabRow for [ Sermons ] and [ Music ]
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = DarkElevatedSurface,
            contentColor = TextHighContrast,
            indicator = { tabPositions ->
                if (selectedTabIndex < tabPositions.size) {
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = RadiantGold
                    )
                }
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            ),
                            color = if (selectedTabIndex == index) RadiantGold else TextMediumContrast
                        )
                    }
                )
            }
        }

        // State-driven switch between mock lists
        when (selectedTabIndex) {
            0 -> MediaItemList(
                items = MockSermons,
                onMediaItemClick = onMediaItemClick,
                modifier = Modifier.fillMaxSize()
            )
            1 -> MediaItemList(
                items = MockMusic,
                onMediaItemClick = onMediaItemClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

/**
 * Scrollable list of media items using standard Material 3 ListItems.
 */
@Composable
fun MediaItemList(
    items: List<MediaItem>,
    modifier: Modifier = Modifier,
    onMediaItemClick: (MediaItem) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = items,
            key = { it.id }
        ) { item ->
            MediaListItemCard(
                item = item,
                onClick = { onMediaItemClick(item) }
            )
        }
    }
}

/**
 * Material 3 card container wrapping a standard Material 3 ListItem.
 */
@Composable
fun MediaListItemCard(
    item: MediaItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkElevatedSurface),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent,
                headlineColor = TextHighContrast,
                supportingColor = TextMediumContrast
            ),
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(width = 96.dp, height = 64.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.thumbnailUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Semi-transparent play badge overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.35f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = RadiantGold,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            },
            headlineContent = {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            },
            supportingContent = {
                Column {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = item.speakerOrArtist,
                        style = MaterialTheme.typography.bodySmall,
                        color = RadiantGold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = item.dateOrDuration,
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSubtle
                        )
                        if (item.scriptureReference != null) {
                            Text(
                                text = " • ${item.scriptureReference}",
                                style = MaterialTheme.typography.labelSmall,
                                color = SanctuarySlate
                            )
                        }
                    }
                }
            }
        )
    }
}
