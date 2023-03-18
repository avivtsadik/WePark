package models;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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

import models.Interfaces.OnActionDoneListener;

public class FirebaseModel {
    FirebaseFirestore db;
    FirebaseStorage storage;
    final String IMAGE_NAME_POSTFIX = ".jpg";

    FirebaseModel() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(false).build();
        db.setFirestoreSettings(settings);
    }

    public void getFavoriteParkingLotsSince(List<String> favorites, Long since, OnActionDoneListener<List<Parking>> listener) {
        db.collection("parkings")
                .whereIn("city", favorites)
                .whereGreaterThanOrEqualTo(Parking.LAST_UPDATED, new Timestamp(since, 0)).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

    public void getParkingLotOfUser(String userId, OnActionDoneListener<List> listener) {
        db.collection("parkings").whereEqualTo("userId", userId).whereNotEqualTo("mark", "D").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

    public void getParkingLot(String parkingId, OnActionDoneListener<Parking> listener) {
        db.collection("parkings").document(parkingId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Parking newParking = new Parking();
                DocumentSnapshot jsonsList = task.getResult();
                if (task.isSuccessful()) {
                    newParking = Parking.fromJson(jsonsList.getData());
                }
                listener.onComplete(newParking);
            }
        });
    }

    public void addParkingLot(Parking newParking, OnActionDoneListener<Void> listener) {
        db.collection("parkings").document(newParking.getId()).set(newParking.toJson()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onComplete(null);
            }
        });
    }

    public void removeParkingLot(String parkingId, OnActionDoneListener<Void> listener) {
        db.collection("parkings").document(parkingId).update("mark", "D").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void task) {
                listener.onComplete(null);
            }
        });
    }

    public void updateParkingLot(String parkingId, OnActionDoneListener<Void> listener) {
        db.collection("parkings").document(parkingId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void task) {
                listener.onComplete(null);
            }
        });
    }

    public void uploadImage(Bitmap bitmap, String imageId, OnActionDoneListener<String> listener) {
        StorageReference storageRef = storage.getReference();

        StorageReference imageRef = storageRef.child(imageId + IMAGE_NAME_POSTFIX);

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

    public void updateUserData(User user, OnActionDoneListener listener) {
        db.collection("users").document(user.getId()).set(user.toJson()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onComplete(null);
            }
        });
    }

    public void getUser(String userId, OnActionDoneListener<User> listener) {
        db.collection("users").whereEqualTo("id", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                User user = new User();
                if (task.isSuccessful()) {
                    QuerySnapshot jsonsList = task.getResult();
                    for (DocumentSnapshot json : jsonsList) {
                        user = User.fromJson(json.getData());
                    }
                }
                listener.onComplete(user);
            }
        });
    }
}
