package uz.pdp.appaviauz.service;

import uz.pdp.appaviauz.payload.PlaneDTO;
import uz.pdp.appaviauz.payload.ResultMessage;

import java.util.UUID;

public interface PlaneService {
    ResultMessage addPlane(PlaneDTO planeDTO);

    ResultMessage deletePlane(UUID id);

    ResultMessage editePlane(PlaneDTO planeDTO, UUID id);

    ResultMessage showPlanesByAerodrome(UUID aeroId, Integer page, Integer size);

    ResultMessage showPlanes(Integer page, Integer size);

    ResultMessage showPlane(UUID id);
}
