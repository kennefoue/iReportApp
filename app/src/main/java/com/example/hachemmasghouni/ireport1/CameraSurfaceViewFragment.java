package com.example.hachemmasghouni.ireport1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CameraSurfaceViewFragment extends Fragment implements SurfaceHolder.Callback{

    public static final int CAMERA_PERMISSION_REQUEST_CODE = 8675309;
    public static final int EXTERNAL_STORAGE_REQUEST_CODE = 902349;
    private final int NUMBER_OF_PICTURE = 3;
    private int TAKED_PICTURES = 0;

    Camera camera;
    SurfaceView cameraSurfaceView;
    SurfaceHolder cameraSurfaceHolder;
    FloatingActionButton btnTakePicture;
    ImageView ivFullScreenCamera;
    ImageView ivCameraPreview;
    boolean previewing = false;
    onAllPictureTaked allPictureTaked;
    ArrayList<byte[]> imagesDataList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_camera_surface_view, container, false);

        // Camera Preview
        cameraSurfaceView = (SurfaceView) v.findViewById(R.id.srfcv_camera);
        btnTakePicture = (FloatingActionButton) v.findViewById(R.id.btn_take_photo);
        ivFullScreenCamera = (ImageView) v.findViewById(R.id.iv_fullscreen);
        ivCameraPreview = (ImageView) v.findViewById(R.id.iv_preview);

                /* Camera surface View */
        getActivity().getWindow().setFormat(PixelFormat.UNKNOWN);
        cameraSurfaceView = (SurfaceView) v.findViewById(R.id.srfcv_camera);
        cameraSurfaceHolder = cameraSurfaceView.getHolder();
        cameraSurfaceHolder.addCallback(this);
        cameraSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        Activity parentActivity = (ReportActivity) getActivity();


        return v;

    }



    /* Communication with Activity when all the pictures are taked */
    public void getImageData(byte[] data) {
        imagesDataList.add(data);
    }

    public interface onAllPictureTaked {
        public void changeFragement();
        public void getImageDataList(ArrayList<byte[]> dataList);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            allPictureTaked = (onAllPictureTaked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                                          + " must Implement Interface Method");
        }
    }

    /* Camera surface view */
    public void takePicture() {
        camera.takePicture(null, null, jpegCallback);
    }

    private void createDirAfterPermission() {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            createDir();
        } else {
            String[] externalStoragePermission = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(externalStoragePermission, EXTERNAL_STORAGE_REQUEST_CODE);
        }

    }

    private void createDir() {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        new File(dir, "iReport_Pictures");

    }

    private void refreshGallery (File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        getActivity().sendBroadcast(intent);
    }

    public void refreshCamera () {
        if(cameraSurfaceHolder.getSurface() == null) {
            // Preview surface does not exist
            return;
        }

        // Stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start preview with new settings
        try {
            camera.setPreviewDisplay(cameraSurfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            getImageData(data);
            FileOutputStream outputStream = null;
            createDirAfterPermission();
            File imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/iReport_Pictures" );
            if(!imageDir.exists() && !imageDir.mkdirs()) {
                Toast.makeText(getContext(), "Can't Create Directory to save Image", Toast.LENGTH_LONG)
                        .show();
                refreshCamera();
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyymmddhhmmss");
                String date = simpleDateFormat.format(new Date());
                String pictureFile = "iReport" + date + ".jpg";
                String pictureFileName = imageDir.getAbsolutePath() + "/" + pictureFile;
                File picFile = new File(pictureFileName);
                try {
                    outputStream = new FileOutputStream(picFile);
                    outputStream.write(data);
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }
                Toast.makeText(getContext(), "Picture saved", Toast.LENGTH_LONG)
                        .show();
                TAKED_PICTURES++;
                if(TAKED_PICTURES == NUMBER_OF_PICTURE) {
                    allPictureTaked.getImageDataList(imagesDataList);
                    allPictureTaked.changeFragement();
                    TAKED_PICTURES = 0;
                    stopCameraPreview();

                }
                refreshCamera();
                refreshGallery(picFile);
            }
        }
    };

    public void startCameraPreviewAfterPermission() {

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraPreview();
        } else {
            String[] cameraPermissionRequest = {android.Manifest.permission.CAMERA};
            requestPermissions(cameraPermissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
        }


    }

    private void cameraPreview() {
        if(!previewing) {
            camera = camera.open();
            Camera.Parameters cameraParameters = camera.getParameters();
            cameraParameters.setRotation(180);
            camera.setParameters(cameraParameters);
            camera.setDisplayOrientation(90);
            if(camera != null) {
                try {
                    camera.setPreviewDisplay(cameraSurfaceHolder);
                    camera.startPreview();
                    previewing = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(!previewing) {
                    camera = camera.open();
                    if(camera != null) {
                        try {
                            camera.setPreviewDisplay(cameraSurfaceHolder);
                            camera.startPreview();
                            previewing = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                Toast.makeText(getContext(), "Failed to get permission", Toast.LENGTH_LONG)
                        .show();
            }
        } else if (requestCode == EXTERNAL_STORAGE_REQUEST_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createDir();
            } else {
                Toast.makeText(getContext(), "can't create directory without permission", Toast.LENGTH_LONG);
            }
        }
    }

    public void stopCameraPreview() {
        if(camera != null && previewing) {
            camera.stopPreview();
            camera.release();
            camera = null;
            previewing = false;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        stopCameraPreview();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startCameraPreviewAfterPermission();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopCameraPreview();
    }


}
