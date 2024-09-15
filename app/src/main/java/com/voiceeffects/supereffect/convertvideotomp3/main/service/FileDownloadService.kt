//package com.voiceeffects.supereffect.convertvideotomp3.main.service
//
//import android.app.Service
//import android.content.Context
//import android.content.Intent
//import android.net.ConnectivityManager
//import android.net.Network
//import android.net.NetworkCapabilities
//import android.os.FileUtils
//import android.os.IBinder
//import androidx.lifecycle.MutableLiveData
//import com.voiceeffects.supereffect.convertvideotomp3.data.model.FileDownloadModel
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.async
//import kotlinx.coroutines.cancel
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import java.io.BufferedInputStream
//import java.io.File
//import java.io.IOException
//import java.io.InputStream
//import java.io.RandomAccessFile
//import java.net.HttpURLConnection
//import java.net.SocketException
//import java.net.URL
//
//class FileDownloadService : Service() {
//
//    private val serviceJob = Job()
//    private val scope = CoroutineScope(Dispatchers.IO + serviceJob)
//    private val listFileFailToDownload = arrayListOf<FileDownloadModel>()
//    private val map: HashMap<FileDownloadModel, HttpURLConnection> = hashMapOf()
//    private val connectivityManager: ConnectivityManager by lazy {
//        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//    }
//    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
//        override fun onAvailable(network: Network) {
//            super.onAvailable(network)
//            listFileFailToDownload.forEach {
//                scope.launch {
//                    downloadFile(it)
//                }
//            }
//        }
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        val networkRequest = android.net.NetworkRequest.Builder()
//            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//            .build()
//        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        val fileDownloadModel = intent?.getParcelableExtra<FileDownloadModel>(KEY_FILE_DOWNLOAD_MODEL)
//        val isCancel = intent?.getBooleanExtra(KEY_FILE_IS_CANCEL , false) ?: false
//        if (isCancel) {
//            map[fileDownloadModel]?.disconnect()
//            map.remove(fileDownloadModel)
//        } else {
//            scope.async {
//                fileDownloadModel?.let {
//                    downloadFile(
//                        it
//                    )
//                }
//            }
//        }
//        return START_NOT_STICKY
//    }
//
//    private suspend fun downloadFile(
//        fileDownloadModel: FileDownloadModel
//    ) = withContext(Dispatchers.IO) {
//        var inputStream: InputStream
//        var connection: HttpURLConnection
//        val file = File(FileUtils.getFileOutputDownload(fileDownloadModel.fileName.orEmpty()))
//        val append = file.exists()
//        val randomAccessFile = RandomAccessFile(file, "rw")
//        kotlin.runCatching {
//            val downloadUrl = URL(fileDownloadModel.path)
//            connection = downloadUrl.openConnection() as HttpURLConnection
//            connection.connect()
//            map[fileDownloadModel] = connection
//            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
//                inputStream = BufferedInputStream(connection.inputStream)
//                val buffer = ByteArray(4096)
//                var bytesRead: Int
//                var totalBytesRead = 0L
//                val fileSize = connection.contentLength
//                if (append) {
//                    totalBytesRead = file.length()
//                    randomAccessFile.seek(file.length())
//                    inputStream.skip(file.length())
//                }
//                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
//                    randomAccessFile.write(buffer, 0, bytesRead)
//                    totalBytesRead += bytesRead
//                    val progress = (totalBytesRead * 100f / fileSize)
//                    withContext(Dispatchers.Main) {
//                        progressChangeLiveData.value =
//                            Triple(progress, fileDownloadModel.resourceId, fileDownloadModel.postId)
//                    }
//                }
//                inputStream.close()
//                randomAccessFile.close()
//                connection.disconnect()
//            }
//        }.onFailure {
//            onFailToDownload(randomAccessFile, fileDownloadModel, it)
//        }.onSuccess {
//            randomAccessFile.close()
//            map.remove(fileDownloadModel)
//            listFileFailToDownload.remove(fileDownloadModel)
//            downloadSuccessLiveData.postValue(
//                Pair(
//                    fileDownloadModel.postId,
//                    fileDownloadModel.resourceId
//                )
//            )
//            finishDownload.postValue(
//                Pair(
//                    fileDownloadModel.postId,
//                    fileDownloadModel.resourceId
//                )
//            )
//        }
//    }
//
//    private fun onFailToDownload(
//        randomAccessFile: RandomAccessFile,
//        fileDownloadModel: FileDownloadModel,
//        it: Throwable
//    ) {
//        randomAccessFile.close()
//        map.remove(fileDownloadModel)
//        if (it is IOException && !isNetworkAvailable()) {
//            if (!listFileFailToDownload.contains(fileDownloadModel)) {
//                listFileFailToDownload.add(fileDownloadModel)
//            }
//        } else if (it !is SocketException) {
//            downloadFailLiveData.postValue(
//                Pair(
//                    fileDownloadModel.postId,
//                    fileDownloadModel.resourceId
//                )
//            )
//            finishDownload.postValue(
//                Pair(
//                    fileDownloadModel.postId,
//                    fileDownloadModel.resourceId
//                )
//            )
//        }
//    }
//
//    override fun onDestroy() {
//        scope.cancel()
//        connectivityManager.unregisterNetworkCallback(networkCallback)
//        super.onDestroy()
//    }
//
//    companion object {
//        const val KEY_FILE_DOWNLOAD_MODEL = "key_file_download_model"
//        const val KEY_FILE_IS_CANCEL = "key_is_cancel"
//        val progressChangeLiveData = MutableLiveData<Triple<Float, String?, String?>>()
//        val downloadSuccessLiveData = MutableLiveData<Pair<String?, String?>>()
//        val downloadFailLiveData = MutableLiveData<Pair<String?, String?>>()
//        val finishDownload = MutableLiveData<Pair<String?, String?>>()
//    }
//}