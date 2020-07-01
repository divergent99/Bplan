package com.abhineetsharma.bplan2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewPropertyAnimator;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.IDN;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.*;


public class MainActivity extends AppCompatActivity {

    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;


    public void func1(View view) {
        ImageView Lee = (ImageView) findViewById(R.id.lee);
        Lee.animate().alpha(1f).setDuration(1000);
    }
    public void order(View view)
    {
        ImageView menu=(ImageView)findViewById(R.id.mbg);
               menu.animate().alpha(1f).setDuration(1000);

                Button b5=(Button)findViewById(R.id.button5);
                b5.animate().alpha(1f).setDuration(1000);

               TextView t=(TextView)findViewById(R.id.textView10);
                t.animate().alpha(1f).setDuration(1000);

                EditText e1=(EditText)findViewById(R.id.editText9);
                e1.animate().alpha(1f).setDuration(1000);


                TextView x=(TextView)findViewById(R.id.textView11);
                x.animate().alpha(1f).setDuration(1000);

                EditText e2=(EditText)findViewById(R.id.editText10);
                e2.animate().alpha(1f).setDuration(1000);




        Button b4=(Button)findViewById(R.id.button4);
        b4.animate().alpha(0f).setDuration(1000);
    }
    /*public void amount(View view)
        {

            ImageView menu=(ImageView)findViewById(R.id.mbg);
            menu.animate().alpha(1f).setDuration(1000);

            Button b5=(Button)findViewById(R.id.button5);
            b5.animate().alpha(1f).setDuration(1000);

            TextView t=(TextView)findViewById(R.id.textView10);
            t.animate().alpha(1f).setDuration(1000);

            EditText e1=(EditText)findViewById(R.id.editText9);
            e1.animate().alpha(1f).setDuration(1000);
            //name = e1.getText().toString();

            TextView x=(TextView)findViewById(R.id.textView11);
            x.animate().alpha(1f).setDuration(1000);

            EditText e2=(EditText)findViewById(R.id.editText10);
            e2.animate().alpha(1f).setDuration(1000);
            QTY= Integer.parseInt(e2.getText().toString());


            //TextView res=(TextView)findViewById(R.id.textView12);
            //res.animate().alpha(1f).setDuration(1000);

        }*/
        public void ans(View view)
        {
           EditText e=(EditText)findViewById(R.id.editText10);
           Double QTY= parseDouble(e.getText().toString());
           Double CGST=0.09*QTY;
           Double SGST=0.09*QTY;
           Double result=(200*QTY)+CGST+SGST;

            Toast.makeText(MainActivity.this,"PAYABLE AMOUNT IS (GST @18% Included) : " +result.toString() + " Rs ",Toast.LENGTH_LONG).show();
        }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //PERMISSION
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Button b1 =(Button)findViewById(R.id.button2);
            Button b2=(Button)findViewById(R.id.button3);

            TextView t1=(TextView)findViewById(R.id.quote);
            ImageView f=(ImageView)findViewById(R.id.fresh);
            f.animate().alpha(0f).setDuration(4000);
            t1.animate().alpha(0f).setDuration(4000);
            b1.animate().alpha(1f).setDuration(4000);
            b2.animate().alpha(1f).setDuration(4000);

            cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
            txtResult = (TextView) findViewById(R.id.txtResult);

            barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
            cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).build();


            //Adding the goddamn event
            cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //PERMISSION
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();

                }
            });


            barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<Barcode> detections) {
                    final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                    if (qrcodes.size() != 0) {
                        txtResult.post(new Runnable() {
                            @Override
                            public void run() {
                                //App Vibrates
                                Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(1000);

                                txtResult.setText(qrcodes.valueAt(0).displayValue);

                                if (qrcodes.valueAt(0).displayValue.equals("Food")) {
                                    ImageView miles = (ImageView) findViewById(R.id.miles);
                                    ImageView menu = (ImageView) findViewById(R.id.menu);
                                    miles.animate().alpha(0f).setDuration(1000);

                                    Button b1 = (Button) findViewById(R.id.button2);
                                    Button b2 = (Button) findViewById(R.id.button3);

                                    b1.animate().alpha(0f).setDuration(1000);
                                    b2.animate().alpha(0f).setDuration(1000);
                                    menu.animate().alpha(1f).setDuration(1000);

                                    Button b4=(Button)findViewById(R.id.button4);
                                    b4.animate().alpha(1f).setDuration(1000);

                                    ImageView Lee = (ImageView) findViewById(R.id.lee);
                                    Lee.animate().alpha(0f).setDuration(1000);
                                }


                            }
                        });
                    }
                }
            });
        }

    }
