package io.fotoapparat.hardware.v2.capabilities;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.concurrent.CountDownLatch;

/**
 * Wrapper around api's {@link android.hardware.camera2.CameraCharacteristics}
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraCapabilities {

	private final CountDownLatch countDownLatch = new CountDownLatch(1);
	private final CameraManager manager;
	private CameraCharacteristics cameraCharacteristics;

	public CameraCapabilities(CameraManager manager) {
		this.manager = manager;
	}

	/**
	 * Sets the id of currently open camera.
	 *
	 * @param cameraId the device camera id
	 * @throws CameraAccessException if the camera device has been disconnected.
	 */
	public void setCameraId(String cameraId) throws CameraAccessException {
		cameraCharacteristics = manager.getCameraCharacteristics(cameraId);
		countDownLatch.countDown();
	}

	public CameraCharacteristics getCameraCharacteristics() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// Do nothing
		}
		return cameraCharacteristics;
	}
}
