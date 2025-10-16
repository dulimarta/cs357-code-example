package edu.gvsu.cis357.android_firebase_compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(modifier: Modifier = Modifier, whoamI: String, vm: MyViewModel) {
    var selectedEntry by rememberSaveable { mutableStateOf<Int?>(null) }
    val allNames by vm.allNames.collectAsState()
    val docID by vm.lastDocId.collectAsState()
    Column(modifier.padding(start = 16.dp, end = 16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "Your Firebase UID is $whoamI",
            fontSize = 16.sp,
            modifier = Modifier.padding(16.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                vm.addRandomName()
            }) {
                Text("Add New")
            }
            if (docID != null) {
                Text("DocID ${docID!!}")
            }
        }
        LazyColumn {
            itemsIndexed(allNames)
            { pos, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (pos % 2 == 0) Color.Transparent else Color.LightGray)
                        .selectable(false, onClick = {
                            if (selectedEntry == pos)
                                selectedEntry = null
                            else
                                selectedEntry = pos
                        })


                ) {
                    if (selectedEntry == pos) {
                        Image(
                            imageVector = Icons.Default.Delete,
                            "",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable {
                                    vm.deleteItem(item.id)
                                    selectedEntry = null
                                })
                    }
                    Text("${item.firstName} ${item.lastName}")
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp,  alignment = Alignment.CenterHorizontally)) {
            Text("Sort by")
            Button(onClick = { vm.sortByFirstName()}) {
                Text("First Name")
            }
            Button(onClick = { vm.sortByLastName()}) {
                Text("Last Name")
            }
        }
    }
}