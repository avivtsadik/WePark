package models;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

import models.Interfaces.AddParkingListener;
import models.Interfaces.GetAllParkingListener;

public class FirebaseModel {
    FirebaseFirestore db;
    FirebaseStorage storage;
    final String IMAGE_NAME_POSTFIX = ".jpg";

    FirebaseModel() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        FirebaseFirestoreSettings settings = new
                FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }

    public void getAllParkingLots(GetAllParkingListener listener) {
        db.collection("parkings").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Parking> list = new LinkedList<>();
                if (task.isSuccessful()) {
                    QuerySnapshot jsonsList = task.getResult();
                    for (DocumentSnapshot json : jsonsList) {
                        Parking pr = Parking.fromJson(json.getData());
                        list.add(pr);
                    }
                }
                listener.onComplete(list);
            }
        });
    }

    public void addParkingLot(Parking newParking, AddParkingListener listener) {
        db.collection("parkings").document(newParking.getId()).set(newParking.toJson()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onComplete();
            }
        });
    }

    public interface UploadImageListener {
        void onComplete(String uri);
    }

    public void uploadImage(Bitmap bitmap, String parkingId, UploadImageListener listener) {
        StorageReference storageRef = storage.getReference();

        StorageReference imageRef = storageRef.child(parkingId + IMAGE_NAME_POSTFIX);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });
            }
        });
    }
}
