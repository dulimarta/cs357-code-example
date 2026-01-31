package edu.gvsu.cis.kmp_roomdb

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Label
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.gvsu.cis.kmp_roomdb.db.Person

@Composable
fun DatabaseDemoScreen(modifier: Modifier = Modifier, viewModel: AppViewModel) {
    val allNames by viewModel.everyone.collectAsState(emptyList())

    Column(modifier = modifier.padding(8.dp)) {
        Text("You have ${allNames.size} records in the database")
        Button(onClick = viewModel::addName) {
            Text("Add a name")
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            itemsIndexed(allNames) { pos, person ->
                PersonDetails(person = person) {
                    viewModel.deletePerson(person)
                }
            }
        }
    }
}

@Composable
fun PersonDetails(modifier: Modifier = Modifier, person: Person, onDelete: (Person) -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Text(person.name, fontSize = 20.sp)
            Text("Age ${person.age}")
        }
        Button(onClick = {
            onDelete(person)
        }) {
            Text("Delete")
        }
    }
}