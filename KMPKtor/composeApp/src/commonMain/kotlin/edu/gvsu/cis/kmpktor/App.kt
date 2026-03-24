package edu.gvsu.cis.kmpktor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage

@Composable
@Preview
fun App() {
    MaterialTheme {
        val vm = viewModel {
            AppViewModel()
        }
        val users by vm.randomUsers.collectAsState()
        val quotes by vm.randomQuotes.collectAsState()
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                    vm.getUser(50)
                }) {
                    Text("Users (${users.size})")
                }
                Button(onClick = {
                    vm.getQuotes(20)
                }) {
                    Text("Quotes (${quotes.size})")
                }
            }
            if (users.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(items = users) {
                        UserDetails(user = it)
                    }
                }
            }
            if (quotes.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(items = quotes) {
                        QuoteDetails(q = it)
                    }
                }
            }
        }
    }
}


@Composable
fun UserDetails(modifier: Modifier = Modifier, user: RandomUser) {
    Row(modifier = modifier.fillMaxSize()
        .border(
        2.dp,
        color = Color(27, 43, 129, 255),
        shape = RoundedCornerShape(4.dp),
    ).padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(modifier = Modifier.size(64.dp),
            model = user.picture.medium,
            contentDescription = "Profile picture")
        Column(
            modifier = Modifier

        ) {
            Text(
                "${user.name.first} ${user.name.last}",
                fontSize = 24.sp
            )
            Text(user.email)
        }
    }
}

@Composable
fun QuoteDetails(modifier: Modifier = Modifier, q: RandomQuote) {
    Column(modifier = modifier.fillMaxWidth().border(1.dp, Color.Black).padding(4.dp)) {
        Text(q.content, fontSize = 20.sp, fontStyle = FontStyle.Italic)
        Text(q.author, modifier = Modifier.align(Alignment.End))
    }
}