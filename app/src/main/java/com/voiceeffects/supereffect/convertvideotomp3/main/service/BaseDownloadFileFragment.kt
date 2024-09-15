//package com.voiceeffects.supereffect.convertvideotomp3.main.service
//
//import android.os.Build
//import android.Manifest
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.provider.Settings.Global.getString
//import android.view.View
//import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.core.content.ContentProviderCompat.requireContext
//import androidx.core.content.ContextCompat
//import androidx.viewbinding.ViewBinding
//import com.voiceeffects.supereffect.convertvideotomp3.R
//import com.voiceeffects.supereffect.convertvideotomp3.data.model.FileDownloadModel
//
//abstract class BaseDownloadFileFragment<T : ViewBinding> : BaseCacheFragment<T>() {
//
//    private val downloadPermission =
//        mutableListOf<String>().apply {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
//                addAll(
//                    arrayOf(
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    )
//                )
//            } else {
//                add(Manifest.permission.READ_MEDIA_IMAGES)
//            }
//        }.toTypedArray()
//
//    private var downloadResource: Triple<ResourceModel, String?, Boolean>? = null
//
//    private val activityResultLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()
//        )
//        { permissions ->
//            var permissionGranted = true
//            permissions.entries.forEach {
//                if (it.key in downloadPermission && !it.value)
//                    permissionGranted = false
//            }
//            if (!permissionGranted) {
//                ToastUtils.show(
//                    requireContext(),
//                    getString(R.string.string_album),
//                    Toast.LENGTH_LONG
//                )
//                FileDownloadService.finishDownload.value =
//                    Pair(downloadResource?.second, downloadResource?.first?.resourceId)
//            } else {
//                downloadResource?.let { actionDownload(it.first , it.second , it.third) }
//            }
//        }
//
//    private fun allPermissionsGranted() = downloadPermission.all {
//        ContextCompat.checkSelfPermission(
//            requireContext(), it
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initDownloadObserver()
//    }
//
//    private fun initDownloadObserver() {
//        FileDownloadService.progressChangeLiveData.observe(viewLifecycleOwner) { triple ->
//            updateDownloadProgress(
//                resourceId = triple.second,
//                postId = triple.third,
//                progress = triple.first
//            )
//        }
//        FileDownloadService.finishDownload.observe(viewLifecycleOwner) { pair ->
//            onDownloadFinished(resourceId = pair.second, postId = pair.first)
//        }
//    }
//
//    fun actionDownload(resourceModel: ResourceModel, postId: String?, isCancel: Boolean) {
//        if (allPermissionsGranted()) {
//            if (!isCancel) {
//                showCommonDialogFragment(
//                    childFragmentManager,
//                    dialogInput = DialogInput(
//                        message = context?.getString(R.string.file_download_question).orEmpty(),
//                        cancelMessageId = R.string.noLabel,
//                        positiveMessageId = R.string.yesLabel
//                    ), onOk = {
//                        resourceModel.isDownloading = true
//                        updateDownloadProgress(resourceModel.resourceId, postId, 0f)
//                        performDownload(resourceModel, postId, isCancel)
//                    }
//                )
//            } else {
//                performDownload(resourceModel, postId, isCancel)
//            }
//        } else {
//            downloadResource = Triple(resourceModel, postId, isCancel)
//            activityResultLauncher.launch(downloadPermission)
//        }
//    }
//
//    private fun performDownload(
//        resourceModel: ResourceModel,
//        postId: String?,
//        isCancel: Boolean
//    ) {
//        val intent = Intent(context, FileDownloadService::class.java)
//        intent.putExtra(
//            FileDownloadService.KEY_FILE_DOWNLOAD_MODEL, FileDownloadModel(
//                fileName = resourceModel.name,
//                resourceId = resourceModel.resourceId,
//                postId = postId,
//                path = resourceModel.url
//            )
//        )
//        intent.putExtra(FileDownloadService.KEY_FILE_IS_CANCEL, isCancel)
//        context?.startService(intent)
//    }
//
//    abstract fun updateDownloadProgress(resourceId: String?, postId: String?, progress: Float)
//    abstract fun onDownloadFinished(resourceId: String?, postId: String?)
//}