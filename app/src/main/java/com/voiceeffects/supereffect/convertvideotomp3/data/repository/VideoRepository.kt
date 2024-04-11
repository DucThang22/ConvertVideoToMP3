package com.voiceeffects.supereffect.convertvideotomp3.data.repository

import android.content.Context
import android.provider.MediaStore
import com.voiceeffects.supereffect.convertvideotomp3.data.database.dao.VideoDao
import com.voiceeffects.supereffect.convertvideotomp3.data.database.entity.VideoGalleryEntity
import com.voiceeffects.supereffect.convertvideotomp3.data.model.VideoModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class VideoRepository @Inject constructor(
    @ApplicationContext private val application: Context,
    private val videoDao: VideoDao
) {
    suspend fun addVideo(videoGalleryEntity: VideoGalleryEntity) = videoDao.addVideo(videoGalleryEntity)

    suspend fun deleteVideo(id: Long) = videoDao.deleteVideo(id)

    suspend fun deleteAllVideo() = videoDao.deleteAllVideo()

    suspend fun updateVideo(videoGalleryEntity: VideoGalleryEntity) = videoDao.updateVideo(videoGalleryEntity)

    suspend fun getAllVideo() = videoDao.getAllVideo()

    suspend fun queryVideos(): List<VideoModel> {
        val videos = mutableListOf<VideoModel>()
        withContext(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.ALBUM,
                MediaStore.Video.Media.ARTIST,
            )
            val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            val orderBy = MediaStore.Video.Media.DATE_ADDED
            val cursor = application.contentResolver.query(
                uri,
                projection,
                null,
                null,
                " $orderBy DESC "
            )
            if (cursor != null && cursor.count > 0) {
                val columnIndexId = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val columnIndexPath = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                val columnIndexName =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val columnIndexDuration =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                val columnIndexSize = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
                val columnIndexArtist = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)
                val columnIndexAlbum = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM)

                while (cursor.moveToNext()) {
                    try {
                        val id = cursor.getLong(columnIndexId)
                        val pathFile = cursor.getString(columnIndexPath)
                        val name = cursor.getString(columnIndexName)
                        val album = cursor.getString(columnIndexAlbum)
                        val artist = cursor.getString(columnIndexArtist)
                        val duration = cursor.getLong(columnIndexDuration) // Milliseconds
                        val size = cursor.getLong(columnIndexSize) // Milliseconds
                        val file = File(pathFile)
                        if (file.exists()) {
                            val video =
                                VideoModel()
                            video.id = id
                            video.title = name
                            video.duration = duration
                            video.path = pathFile
                            video.size = size
                            video.album = album
                            video.artist = artist
                            videos += video
                        }
                    } catch (ex: NullPointerException) {
                        //ex.printStackTrace();
                    }
                }
                // Close cursor
                cursor.close()
            }
        }
        return videos
    }

    private fun queryVideosByIdFolder(
        idFolder: Long,
        listVideo: List<VideoModel>,
    ): List<VideoModel> {

        val videos = mutableListOf<VideoModel>()
        val selection = " media_type = 3 AND " + MediaStore.Video.Media.BUCKET_ID + "=?"
        val orderBy = MediaStore.Video.Media.DATE_ADDED
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            //  MediaStore.Audio.Albums.ALBUM_ID
//            MediaStore.Video.Media.ALBUM,
//            MediaStore.Video.Media.ARTIST,
        )
        val uri = MediaStore.Files.getContentUri("external")
        val cursor = application.contentResolver.query(
            uri,
            projection,
            selection,
            arrayOf(idFolder.toString()),
            " $orderBy DESC "
        )
        if (cursor != null && cursor.count > 0) {
            val columnIndexId = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val columnIndexPath = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val columnIndexName = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val columnIndexDuration = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val columnIndexSize = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            //   val columnIndexArtist = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)
            //  val columnIndexAlbum = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM)

            while (cursor.moveToNext()) {
                try {
                    val id = cursor.getLong(columnIndexId)
                    val pathFile = cursor.getString(columnIndexPath)
                    val name = cursor.getString(columnIndexName)
                    val duration = cursor.getLong(columnIndexDuration) // Milliseconds
                    val size = cursor.getLong(columnIndexSize) // Milliseconds
                    //   val album = cursor.getString(columnIndexAlbum)
                    //   val artist = cursor.getString(columnIndexArtist)
                    val file = File(pathFile)
                    if (file.exists()) {
                        val video = VideoModel()
                        video.id = id
                        video.title = name
                        video.duration = duration
                        video.path = pathFile
                        video.size = size
                        videos += video
//                        video.album = nameFolder
//                        video.artist = nameFolder

                        listVideo.find {
                            it.id == video.id
                        }?.let {
                            video.album = it.album
                            video.artist = it.artist
                        }
                    }
                } catch (ex: NullPointerException) {
                    //ex.printStackTrace();
                }
            }
            // Close cursor
            cursor.close()
        }
        return videos
    }


    private fun queryVideosByIdFolderAll(
        idFolder: Long,
        nameFolder: String,
    ): List<VideoModel> {

        val videos = mutableListOf<VideoModel>()
        val selection = " media_type = 3 AND " + MediaStore.Video.Media.BUCKET_ID + "=?"
        val orderBy = MediaStore.Video.Media.DATE_ADDED
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            //  MediaStore.Audio.Albums.ALBUM_ID

//            MediaStore.Video.Media.ALBUM,
//            MediaStore.Video.Media.ARTIST,
        )
        val uri = MediaStore.Files.getContentUri("external")
        val cursor = application.contentResolver.query(
            uri,
            projection,
            selection,
            arrayOf(idFolder.toString()),
            " $orderBy DESC "
        )
        if (cursor != null && cursor.count > 0) {
            val columnIndexId = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val columnIndexPath = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val columnIndexName = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val columnIndexDuration = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val columnIndexSize = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            //   val columnIndexArtist = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)
            //  val columnIndexAlbum = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM)

            while (cursor.moveToNext()) {
                try {
                    val id = cursor.getLong(columnIndexId)
                    val pathFile = cursor.getString(columnIndexPath)
                    val name = cursor.getString(columnIndexName)
                    val duration = cursor.getLong(columnIndexDuration) // Milliseconds
                    val size = cursor.getLong(columnIndexSize) // Milliseconds
                    //   val album = cursor.getString(columnIndexAlbum)
                    //   val artist = cursor.getString(columnIndexArtist)
                    val file = File(pathFile)
                    if (file.exists()) {
                        val video = VideoModel()
                        video.id = id
                        video.title = name
                        video.duration = duration
                        video.path = pathFile
                        video.size = size
                        videos += video
                        video.album = nameFolder
                        video.artist = nameFolder

//                        listVideo.find {
//                            it.id == video.id
//                        }?.let {
//                            video.album = it.album
//                            video.artist = it.artist
//                        }
                    }
                } catch (ex: NullPointerException) {
                    //ex.printStackTrace();
                }
            }
            // Close cursor
            cursor.close()
        }
        return videos
    }
}