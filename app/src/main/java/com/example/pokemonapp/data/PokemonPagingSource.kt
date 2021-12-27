package com.example.pokemonapp.data

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemonapp.api.PokemonService
import com.example.pokemonapp.model.Pokemon
import retrofit2.HttpException
import java.io.IOException

class PokemonPagingSource(private val service: PokemonService) : PagingSource<Int, Pokemon>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val pageNumber = params.key ?: 1
        return try {
            //first values for offset(page beggins) and limit(limit of pokemon for page(according to offset))
            val response = service.getPokemonResult(pageNumber, params.loadSize)
            val data = response?.results

            //to next page get from api parameter the offset and add with limit to create nextPageNUmber
            var nextPageNumber: Int? = null
            if (response?.next != null) {
                val uri = Uri.parse(response.next)
                val nextPageQuery = uri.getQueryParameter("offset")
                nextPageNumber = nextPageQuery?.toInt()?.plus(params.loadSize)
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
