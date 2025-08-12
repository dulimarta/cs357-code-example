package edu.gvsu.cis357.spacex_rest.model

//import edu.gvsu.cis357.spacex_rest.model.Headquarters
//import edu.gvsu.cis357.spacex_rest.model.Links

data class Company(
    val ceo: String,
    val founded: Int,
    val founder: String,
    val name: String,
)