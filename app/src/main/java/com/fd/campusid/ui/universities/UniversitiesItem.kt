package com.fd.campusid.ui.universities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fd.campusid.data.repository.university.model.University
import com.fd.campusid.ui.theme.CampusIDTheme

@Composable
fun UniversitiesItem(
    position : Int,
    university: University,
    onClick: () -> Unit
) {
    Card(
        onClick = { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "$position. ${university.name ?: "-"}",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        )

        Text(
            text = university.country ?: "-",
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp)
        )

        Text(
            text = university.webPages ?: "-",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UniversitiesItemPreview() {
    CampusIDTheme {
        UniversitiesItem(
            position = 1,
            university = University(
                name = "University of Toronto",
                country = "Canada",
                webPages = "http://www.utoronto.ca"
            ),
            onClick = {}
        )
    }
}