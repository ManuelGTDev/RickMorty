package com.mgtapps.rickmorty.data.remote.dto.characters

import com.mgtapps.rickmorty.domain.model.Characters

data class CharactersDto(
    val info: Info,
    val results: List<Result>
)

fun CharactersDto.toListCharacters(): List<Characters> {
    val resulEntries = results.mapIndexed { _, entries ->
        Characters(
            id = entries.id,
            name = entries.name,
            image = entries.image,
            species = entries.species
        )
    }
    return resulEntries
}