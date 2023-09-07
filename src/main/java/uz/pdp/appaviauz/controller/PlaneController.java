package uz.pdp.appaviauz.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appaviauz.payload.PlaneDTO;
import uz.pdp.appaviauz.util.Path;

import java.util.UUID;

import static uz.pdp.appaviauz.controller.PlaneControllerImpl.ID;
import static uz.pdp.appaviauz.controller.PlaneControllerImpl.SHOW_BY_AERODROME;

@PreAuthorize("hasAnyAuthority('ADD_PLANE','EDIT_PLANE','SHOW_PLANE','DELETE_PLANE')")
@RequestMapping(Path.BASE_PATH_PLANE)
public interface PlaneController {
    @Operation(summary = "The minimum value of seats number is <b>0</b> and the maximum value is <b>10000</b>")
    @PostMapping
    ResponseEntity<?> addPlane(@RequestBody PlaneDTO planeDTO);

    @DeleteMapping(ID)
    ResponseEntity<?> deletePlane(@PathVariable UUID id);

    @GetMapping
    ResponseEntity<?> showPlanes(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(SHOW_BY_AERODROME)
    ResponseEntity<?> showPlanesByAerodrome(@PathVariable UUID aero_id,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(ID)
    ResponseEntity<?> showPlane(@PathVariable UUID id);
    @Operation(summary = "The minimum value of seats number is <b>0</b> and the maximum value is <b>10000</b>")
    @PutMapping(ID)
    ResponseEntity<?> editePlane(@PathVariable UUID id, @RequestBody PlaneDTO planeDTO);
}
