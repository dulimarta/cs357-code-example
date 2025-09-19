package edu.gvsu.cis.composenavigation.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

@Composable
fun SelectShippingAddressScreen(onShipTo: (String) -> Unit) {
    val context = LocalContext.current
    var kontakAddr by remember { mutableStateOf<String?>(null) }
    val contract = ActivityResultContracts.StartActivityForResult()
    val pickKontakLauncher = rememberLauncherForActivityResult(contract) { result ->
        kontakAddr = null
        if (result.resultCode == RESULT_OK) {
            println("Result: ${result.resultCode}, ${result.data?.data}")
            val cursor = context.contentResolver.query(
                result.data?.data!!,
                arrayOf(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS),
                null, null, null
            )
            if (cursor?.moveToFirst() == true) {
                kontakAddr = cursor.getString(0)
            }
        } else {
            kontakAddr = "<No Address selected>"
        }
    }

    Column(modifier = Modifier.background(Color.LightGray).fillMaxSize()) {
        Text("Select Shipping Address", fontSize = 20.sp)
        if (kontakAddr != null)
            Text("Ship to contact $kontakAddr")
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { onShipTo("49401") }) {
                Text("Allendale")
            }
            Button(onClick = {
                val gmap = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Mackinac+Hall")).also {
                    it.setPackage("com.google.android.apps.maps")
                }
                try {
                    context.startActivity(gmap)
                } catch (e: Exception) {
                    println("Can't use maps: $e")
                }
            }) {
                Text("By Map")
            }
            Button(onClick = {
                val kontakIntent = Intent(
                    Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI
                )
                pickKontakLauncher.launch(kontakIntent)
            }) {
                Text("Search Contact")

            }
        }
    }

}