package uz.pdp.appaviauz.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appaviauz.payload.PlaneDTO;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.service.PlaneService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "plane-controller", description = "Plane controller management, admin and above can access")
@SecurityRequirement(name = "bearerAuth")
public class PlaneControllerImpl implements PlaneController {
    static final String ID = "/{id}";
    static final String SHOW_BY_AERODROME = "/aerodrome_by/{aero_id}";


    private final PlaneService planeService;

    @Override
    public ResponseEntity<?> addPlane(PlaneDTO planeDTO) {
        ResultMessage resultMessage = planeService.addPlane(planeDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> deletePlane(UUID id) {
        return ResponseEntity
                .ok(planeService.deletePlane(id));
    }

    @Override
    public ResponseEntity<?> showPlanes(Integer page, Integer size) {
        return ResponseEntity.ok(planeService
                .showPlanes(page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showPlanesByAerodrome(UUID aero_id, Integer page, Integer size) {
        return ResponseEntity.ok(planeService
                .showPlanesByAerodrome(aero_id, page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showPlane(UUID id) {
        return ResponseEntity.ok(planeService
                .showPlane(id)
                .getObject());
    }

    @Override
    public ResponseEntity<?> editePlane(UUID id, PlaneDTO planeDTO) {
        ResultMessage resultMessage = planeService.editePlane(planeDTO, id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(resultMessage);
    }
}
