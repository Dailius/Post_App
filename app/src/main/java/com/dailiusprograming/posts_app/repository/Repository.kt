package com.dailiusprograming.posts_app.repository

import android.util.Log
import com.dailiusprograming.posts_app.data.local.PostsDao
import com.dailiusprograming.posts_app.data.model.Posts
import com.dailiusprograming.posts_app.data.model.relations.PostAndUser
import com.dailiusprograming.posts_app.data.remote.RemoteData
import com.dailiusprograming.posts_app.util.DataResult
import com.dailiusprograming.posts_app.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteData: RemoteData,
    private val postsDao: PostsDao
) {

    suspend fun fetchPosts(
        status: DataResult.Status
    ): Flow<DataResult<List<Posts>>> {

        return flow {

            if (status == DataResult.Status.INIT) {
                emit(DataResult.loading())
            }

            val remoteData = remoteData.fetchPosts()
            if (remoteData.status == DataResult.Status.SUCCESS) {
                postsDao.insertPosts(remoteData.data)
            }

            delay(1000)

            val localData = fetchPostsCache()
            when (localData.data?.isNullOrEmpty()) {
                true -> Log.i(TAG, "Local data is EMPTY")
                false -> {
                    Log.i(TAG, "Local data is AVAILABLE")
                    emit(localData)
                }
            }
        }
            .catch { e ->
                Log.i(TAG, "ERROR_1: ${e.message}")
                emit(DataResult.error(e.message.toString(), null))
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun fetchPostDetail(userId: Int, postId: Int): Flow<DataResult<PostAndUser>> {

        return flow {
            val localData = fetchPostAndUserCache(postId)
            if (localData.data != null) {
                emit(localData)
            }
            postsDao.insertUserDetails(remoteData.fetchUserDetails(userId).data)
            postsDao.insertPostDetails(remoteData.fetchPostDetails(postId).data)

            emit(fetchPostAndUserCache(postId))

        }.catch { e ->
            Log.i(TAG, "ERROR_2: ${e.message}")
            emit(DataResult.error(e.message.toString(), null))
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun fetchPostsCache(): DataResult<List<Posts>> =
        DataResult.success(postsDao.getAllPosts())

    private suspend fun fetchPostAndUserCache(postsId: Int): DataResult<PostAndUser> =
        DataResult.success(postsDao.getPostAndUser(postsId = postsId))


}