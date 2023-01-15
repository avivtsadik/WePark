package application;

import java.util.List;
import java.util.Vector;

import models.Parking;

public interface GetDataListener {
    void OnComplete(List<Parking> data);
}
