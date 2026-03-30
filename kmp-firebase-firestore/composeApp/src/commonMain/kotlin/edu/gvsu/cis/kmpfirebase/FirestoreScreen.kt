package edu.gvsu.cis.kmpfirebase

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource

@Composable
fun FirestoreScreen(modifier: Modifier = Modifier, viewModel: AppViewModel) {
    val cities by viewModel.allCities.collectAsState(emptyList())
    var selectedID by remember { mutableStateOf<String?>(null) }
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Firestore Demo", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Button(onClick = {
            viewModel.addRandomCity()
        }) {
            Text("New City")
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(cities) {
                CityDetails(
                    city = it,
                    deletable = selectedID == it.id,
                    modifier = Modifier.selectable(
                        selected = selectedID == it.id,
                        onClick = {
                            if (selectedID == null || selectedID != it.id)
                                selectedID = it.id
                            else selectedID = null
                        })
                ) {
                    viewModel.deleteCityById(it)
                }
            }
        }
        Text("Click on a city to delete it")
    }
}


@Composable
fun CityDetails(modifier: Modifier = Modifier, city: City,
                deletable: Boolean = false,
                onDeleteCity: (String) -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth()
            .border(1.dp, Color.Black)
            .padding(4.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)

        ) {
            Text("${city.name}", fontSize = 24.sp)
            Text("ID ${city.id}")
        }
        if (deletable)
            IconButton(onClick = {
                onDeleteCity(city.id)
            }) {
                Icon(Icons.Default.Delete, contentDescription = null)
            }
        else
            Text("Pop ${city.population}")
    }
}