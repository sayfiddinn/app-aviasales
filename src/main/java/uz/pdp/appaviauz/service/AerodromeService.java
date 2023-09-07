package uz.pdp.appaviauz.service;

import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appaviauz.payload.AerodromeDTO;
import uz.pdp.appaviauz.payload.ResultMessage;

import java.util.UUID;

public interface AerodromeService {
    ResultMessage addAerodrome(AerodromeDTO aerodromeDTO);

    ResultMessage editAerodrome(UUID id, AerodromeDTO aerodromeDTO);

    ResultMessage showAerodromes(Integer page, Integer size);

    ResultMessage showAerodrome(UUID id);

    ResultMessage deleteAerodrome(UUID id);

    ResultMessage addPlaneInAero(UUID id, UUID planeId);

    ResultMessage addFile(MultipartFile multipartFile, UUID aeroId);

    ResultMessage deleteFile(UUID aeroId);

    ResultMessage updateFile(UUID aeroId, MultipartFile multipartFile);
}
