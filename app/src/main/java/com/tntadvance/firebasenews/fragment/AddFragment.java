package com.tntadvance.firebasenews.fragment;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.tntadvance.firebasenews.R;
import com.tntadvance.firebasenews.connection.FirebaseConnection;
import com.tntadvance.firebasenews.dao.NewsDao;
import com.tntadvance.firebasenews.util.DateUtil;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import static com.tntadvance.firebasenews.R.drawable.default1;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AddFragment extends Fragment implements View.OnClickListener {

    private EditText edHeader;
    private ImageButton btnAddImage;
    private Button btnSave;
    private Button btnUpdate;
    private Button btnDelete;
    private ImageView imgView;
    private TextView tvPath;
    private String id;
    private EditText edContent;
    private String header;
    private String content;
    private FirebaseConnection firebase;

    private NewsDao dao;
    private DateUtil dateUtil;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    private Uri mImageCaptureUri;
    private Bitmap bitmapCrop;
    private String path;

    public AddFragment() {
        super();
    }

    public static AddFragment newInstance(NewsDao dao) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putParcelable("dao", dao);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = getArguments().getParcelable("dao");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        initInstances(rootView);
        checkData();

        return rootView;
    }

    private void initInstances(View rootView) {
        getActivity().setTitle("Add News");
        firebase = new FirebaseConnection();
        dateUtil = new DateUtil();

        edHeader = (EditText) rootView.findViewById(R.id.edHeader);
        edContent = (EditText) rootView.findViewById(R.id.edContent);
        btnAddImage = (ImageButton) rootView.findViewById(R.id.btnAddImage);
        btnAddImage.setOnClickListener(this);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnUpdate = (Button) rootView.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        btnDelete = (Button) rootView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        imgView = (ImageView) rootView.findViewById(R.id.imgView);
        tvPath = (TextView) rootView.findViewById(R.id.tvPath);

    }

    private void checkData(){
        dao = getArguments().getParcelable("dao");

        id = dao.getNewsId();
        header = dao.getHeader();
        content = dao.getContent();
        path = dao.getImagePath();

        if (!TextUtils.isEmpty(header)) {
            getActivity().setTitle(header);
            edHeader.setText(header);
            edContent.setText(content);
            tvPath.setText(path);

            Glide.with(getContext())
                    .load(path)
                    .placeholder(default1)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .skipMemoryCache(true)
                    .into(imgView);

            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

        } else {
            btnSave.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnAddImage) {
            addImage();
        } else if (view == btnSave) {
            addData();
        } else if (view == btnUpdate) {
            updateData();
        } else if (view == btnDelete) {
            deleteData();
        }
    }


    private void addImage() {
        final String[] items = new String[]{"Take from camera", "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                //pick from camera
                if (item == 0) {
                    Log.d("camera", "camera");
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    try {
                        intent.putExtra("return-data", true);

                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    //pick from file
                    if (Build.VERSION.SDK_INT < 19) {
                        Intent intent = new Intent();

                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);

                    } else {

                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");

                        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
                    }
                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                mImageCaptureUri = data.getData();
                doCrop();
                break;

            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();
                doCrop();
                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();

                if (extras != null) {
                    bitmapCrop = extras.getParcelable("data");
                    imgView.setImageBitmap(bitmapCrop);
                }

                break;
        }
    }

    private void doCrop() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, 0);

        int size = list.size();

        if (size == 0) {
            Toast.makeText(getActivity(), "Can not find image crop app", Toast.LENGTH_SHORT).show();
        } else {
            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 600);
            intent.putExtra("outputY", 600);
            intent.putExtra("aspectX", 6);
            intent.putExtra("aspectY", 6);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

            startActivityForResult(i, CROP_FROM_CAMERA);
        }
    }

    private void addData() {
        id = firebase.getDatabase("news").push().getKey();
        header = edHeader.getText().toString();
        content = edContent.getText().toString();

        NewsDao dao = new NewsDao();
        dao.setNewsId(id);
        dao.setHeader(header);
        dao.setContent(content);
        dao.setDate(dateUtil.currentTimeStamp());

        uploadFromDataInMemory(dao);
    }

    private void uploadFromDataInMemory(final NewsDao dao) {
        // Get the data from an ImageView as bytes
        imgView.setDrawingCacheEnabled(true);
        imgView.buildDrawingCache();
        Bitmap bitmap = imgView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = firebase.getStorage("news/" + id + "/" + dateUtil.currentTimeStamp() + ".png").putBytes(data);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.d("progress", "Upload is " + progress + "% done");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                tvPath.setText(downloadUrl.toString());
                dao.setImagePath(downloadUrl.toString());
                firebase.getDatabase("news").child(id).setValue(dao);

//                Toast.makeText(getActivity(), "Upload Complete", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });

    }

    private void deleteData() {
        //removing
        firebase.getDatabase("news").child(id).removeValue();

        getActivity().finish();
        Toast.makeText(getActivity(), "ANews Deleted", Toast.LENGTH_LONG).show();
    }

    private void updateData() {
        if (bitmapCrop != null) {
            NewsDao dao = new NewsDao();
            dao.setNewsId(id);
            dao.setHeader(header);
            dao.setContent(content);
            dao.setDate(dateUtil.currentTimeStamp());

            uploadFromDataInMemory(dao);
        } else {
            Toast.makeText(getActivity(), "Do not change image", Toast.LENGTH_LONG).show();

            HashMap<String, Object> postValues = new HashMap<>();
            postValues.put("header", edHeader.getText().toString());
            postValues.put("content", edContent.getText().toString());

            firebase.getDatabase("news").child(id).updateChildren(postValues);

            getActivity().finish();
        }
    }


}
