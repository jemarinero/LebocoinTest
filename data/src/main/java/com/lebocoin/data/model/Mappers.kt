package com.lebocoin.data.model

import com.lebocoin.domain.model.Information

fun InformationDTO.toDomain() = Information(
    albumId = albumId,
    id = id,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)