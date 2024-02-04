package com.tutorial.searchsuggestions.api.model.deserializer

import com.google.gson.*
import com.tutorial.searchsuggestions.api.model.SearchSuggestionsResponse
import java.lang.reflect.Type

class SearchSuggestionsResponseDeserializer : JsonDeserializer<SearchSuggestionsResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SearchSuggestionsResponse {
        if (json == null || !json.isJsonArray) throw JsonParseException("Invalid JSON format")

        val jsonArray = json.asJsonArray
        if (jsonArray.size() != 2) throw JsonParseException("Invalid array length")

        if (!jsonArray[1].isJsonArray) throw JsonParseException("Invalid JSON format")
        val resultsArray = jsonArray[1].asJsonArray

        return SearchSuggestionsResponse(
            keyword = jsonArray[0].asString,
            results = resultsArray.map { it.asString }
        )
    }
}
