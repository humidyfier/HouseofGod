
package com.example.houseofgod.feature.bible

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.houseofgod.core.designsystem.DarkElevatedSurface
import com.example.houseofgod.core.designsystem.DarkSurfaceHighlight
import com.example.houseofgod.core.designsystem.DarkSurfaceVariant
import com.example.houseofgod.core.designsystem.RadiantGold
import com.example.houseofgod.core.designsystem.RadiantGoldContainer
import com.example.houseofgod.core.designsystem.RadiantGoldContainerText
import com.example.houseofgod.core.designsystem.SanctuarySlate
import com.example.houseofgod.core.designsystem.TextHighContrast
import com.example.houseofgod.core.designsystem.TextMediumContrast
import com.example.houseofgod.core.designsystem.TextSubtle

data class BibleVerse(
    val verseNumber: Int,
    val textEnglish: String,
    val textTamil: String
)

val SampleChapterVerses = listOf(
    BibleVerse(
        verseNumber = 16,
        textEnglish = "For God so loved the world, that he gave his only begotten Son, that whosoever believeth in him should not perish, but have everlasting life.",
        textTamil = "தேவன், தம்முடைய ஒரேபேறான குமாரனை விசுவாசிக்கிறவன் எவனோ அவன் கெட்டுப்போகாமல் நித்தியஜீவனை அடையும்படிக்கு, அவரைத் தந்தருளி, இவ்வளவாய் உலகத்தில் அன்புகூர்ந்தார்."
    ),
    BibleVerse(
        verseNumber = 17,
        textEnglish = "For God sent not his Son into the world to condemn the world; but that the world through him might be saved.",
        textTamil = "உலகத்தை ஆக்கினைக்குட்படுத்தும்படியாகத் தேவன் தம்முடைய குமாரனை உலகத்தில் அனுப்பாமல், அவராலே உலகம் இரட்சிக்கப்படுவதற்காகவே அவரை அனுப்பினார்."
    ),
    BibleVerse(
        verseNumber = 18,
        textEnglish = "He that believeth on him is not condemned: but he that believeth not is condemned already, because he hath not believed in the name of the only begotten Son of God.",
        textTamil = "அவரை விசுவாசிக்கிறவன் ஆக்கினைக்குட்படுத்தப்படான்; விசுவாசியாதவனோ தேவனுடைய ஒரேபேறான குமாரனின் நாமத்தில் விசுவாசமுள்ளவனாயிராதபடியினால், அவன் ஆக்கினைத்தீர்ப்புக்குட்பட்டாயிற்று."
    )
)

/**
 * Bible Reader Screen shell closely mirroring a distraction-free, premium reader.
 * Supports Book/Chapter selection, Tamil & English language toggle, and scripture bookmarks.
 */
@Composable
fun BibleScreen(
    modifier: Modifier = Modifier,
    onVerseBookmark: (Int) -> Unit = {}
) {
    var selectedLanguage by rememberSaveable { mutableStateOf("English") }
    var selectedBookAndChapter by rememberSaveable { mutableStateOf("John 3") }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top Scripture Bar (Book/Chapter Selector & Language Chips)
        BibleHeaderBar(
            selectedBookAndChapter = selectedBookAndChapter,
            selectedLanguage = selectedLanguage,
            onLanguageSelect = { selectedLanguage = it }
        )

        // Reader Area
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "$selectedBookAndChapter (KJV / BSI)",
                    style = MaterialTheme.typography.titleMedium,
                    color = RadiantGold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (selectedLanguage == "Tamil") "யோவான் 3 ஆம் அதிகாரம்" else "Chapter 3",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSubtle
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(SampleChapterVerses) { verse ->
                VerseReaderItem(
                    verse = verse,
                    language = selectedLanguage,
                    onBookmarkClick = { onVerseBookmark(verse.verseNumber) }
                )
            }
        }
    }
}

@Composable
fun BibleHeaderBar(
    selectedBookAndChapter: String,
    selectedLanguage: String,
    modifier: Modifier = Modifier,
    onLanguageSelect: (String) -> Unit = {}
) {
    Surface(
        color = DarkElevatedSurface,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Book & Chapter Selector Pill
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = DarkSurfaceVariant
                ) {
                    Text(
                        text = selectedBookAndChapter,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextHighContrast,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }

                // Language toggle pills
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("English", "Tamil").forEach { lang ->
                        val isSelected = selectedLanguage == lang
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) RadiantGoldContainer else DarkSurfaceVariant,
                            modifier = Modifier.clickable { onLanguageSelect(lang) }
                        ) {
                            Text(
                                text = lang,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = if (isSelected) RadiantGoldContainerText else TextMediumContrast,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VerseReaderItem(
    verse: BibleVerse,
    language: String,
    modifier: Modifier = Modifier,
    onBookmarkClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkElevatedSurface),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Verse ${verse.verseNumber}",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = RadiantGold
                )

                IconButton(onClick = onBookmarkClick) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Bookmark Verse",
                        tint = SanctuarySlate
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Primary Scripture Text rendered using HouseOfGodTypography with accessible line-height
            Text(
                text = if (language == "Tamil") verse.textTamil else verse.textEnglish,
                style = MaterialTheme.typography.bodyLarge,
                color = TextHighContrast
            )
        }
    }
}
