package com.lebocoin.data.model

import com.lebocoin.data.database.entity.InformationEntity
import com.lebocoin.domain.model.Information

fun InformationDTO.toDomain() = Information(
    albumId = albumId,
    id = id,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)

fun InformationDTO.toEntity() = InformationEntity(
    albumId = albumId,
    id = id,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)

fun InformationEntity.toDomain() = Information(
    albumId = albumId,
    id = id,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)