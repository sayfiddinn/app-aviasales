package uz.pdp.appaviauz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appaviauz.payload.AerodromeDTO;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.service.AerodromeService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "aerodrome-controller", description = "Aerodrome controller management, admin and above can access")
@SecurityRequirement(name = "bearerAuth")
public class AerodromeControllerImpl implements AerodromeController {
    static final String ID = "/{id}";
    static final String add_file = "/add_file/{aero_id}";
    static final String delete_file = "/delete_file/{aero_id}";
    static final String update_file = "/update_file/{aero_id}";
    private final AerodromeService aerodromeService;


    @Override
    public ResponseEntity<?> addAerodrome(AerodromeDTO aerodromeDTO) {
        ResultMessage resultMessage = aerodromeService.addAerodrome(aerodromeDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> editAerodrome(UUID id, AerodromeDTO aerodromeDTO) {
        ResultMessage resultMessage = aerodromeService.editAerodrome(id, aerodromeDTO);
        return ResponseEntity.ok(resultMessage);
    }

    @Override
    public ResponseEntity<?> showAerodromes(Integer page, Integer size) {
        return ResponseEntity.ok(aerodromeService
                .showAerodromes(page, size)
                .getObject());
    }

    @Override
    public ResponseEntity<?> showAerodrome(UUID id) {
        return ResponseEntity.ok(aerodromeService
                .showAerodrome(id)
                .getObject());
    }

    @Override
    public ResponseEntity<?> deleteAerodrome(UUID id) {
        ResultMessage resultMessage = aerodromeService.deleteAerodrome(id);
        return ResponseEntity.ok(resultMessage);
    }

    @Override
    public ResponseEntity<?> addPlaneInAero(UUID id, UUID planeId) {
        ResultMessage resultMessage = aerodromeService.addPlaneInAero(id, planeId);
        return ResponseEntity.ok(resultMessage);
    }

    @Override
    public ResponseEntity<?> addFile(MultipartFile multipartFile, UUID aero_id) {
        ResultMessage resultMessage = aerodromeService.addFile(multipartFile, aero_id);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.CREATED
                        : HttpStatus.CONFLICT)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> deleteFile(UUID aero_id) {
        ResultMessage resultMessage = aerodromeService.deleteFile(aero_id);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.OK
                        : HttpStatus.NOT_ACCEPTABLE)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> updateFile(UUID aero_id, MultipartFile multipartFile) {
        ResultMessage resultMessage = aerodromeService.updateFile(aero_id, multipartFile);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.ACCEPTED
                        : HttpStatus.NOT_MODIFIED)
                .body(resultMessage);
    }

}
