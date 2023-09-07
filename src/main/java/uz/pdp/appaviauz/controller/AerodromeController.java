package uz.pdp.appaviauz.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appaviauz.payload.AerodromeDTO;
import uz.pdp.appaviauz.util.Path;

import java.util.UUID;

import static uz.pdp.appaviauz.controller.AerodromeControllerImpl.*;


@RequestMapping(Path.BASE_PATH_AERODROME)
@PreAuthorize("hasAnyAuthority('ADD_AERODROME','EDIT_AERODROME','SHOW_AERODROME','DELETE_AERODROME')")
public interface AerodromeController {
    @PostMapping
    ResponseEntity<?> addAerodrome(@RequestBody AerodromeDTO aerodromeDTO);

    @PutMapping(ID)
    ResponseEntity<?> editAerodrome(@PathVariable UUID id,
                                    @RequestBody AerodromeDTO aerodromeDTO);

    @GetMapping
    ResponseEntity<?> showAerodromes(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size);


    @GetMapping(ID)
    ResponseEntity<?> showAerodrome(@PathVariable UUID id);

    @DeleteMapping(ID)
    ResponseEntity<?> deleteAerodrome(@PathVariable UUID id);

    @PostMapping(ID)
    ResponseEntity<?> addPlaneInAero(@PathVariable UUID id,
                                     @RequestParam(name = "plane_id") UUID planeId);

    @Operation(summary = "Upload a file",description = Path.FILE_DESCRIPTION)
    @PostMapping(value = add_file, consumes = {"multipart/form-data"})
    ResponseEntity<?> addFile(@RequestParam(value = "file") MultipartFile multipartFile,
                              @PathVariable UUID aero_id);

    @DeleteMapping(delete_file)
    ResponseEntity<?> deleteFile(@PathVariable UUID aero_id);

    @Operation(summary = "Upload a file",description = Path.FILE_DESCRIPTION)
    @PutMapping(value = update_file, consumes = {"multipart/form-data"})
    ResponseEntity<?> updateFile(@PathVariable UUID aero_id,
                                 @RequestParam(value = "file") MultipartFile multipartFile);
}
