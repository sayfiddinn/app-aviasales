package uz.pdp.appaviauz.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appaviauz.entity.Aerodrome;
import uz.pdp.appaviauz.entity.Attachment;
import uz.pdp.appaviauz.entity.Plane;
import uz.pdp.appaviauz.exception.ConflictException;
import uz.pdp.appaviauz.exception.NotFoundException;
import uz.pdp.appaviauz.mapper.MyMapper;
import uz.pdp.appaviauz.payload.AerodromeDTO;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.repository.AerodromeRepository;
import uz.pdp.appaviauz.repository.CityRepository;
import uz.pdp.appaviauz.repository.PlaneRepository;
import uz.pdp.appaviauz.util.Util;
import uz.pdp.appaviauz.util.Validation;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static uz.pdp.appaviauz.util.Path.*;

@Service
@RequiredArgsConstructor
public class AerodromeServiceImpl implements AerodromeService {
    private final AerodromeRepository aerodromeRepository;
    private final PlaneRepository planeRepository;
    private final CityRepository cityRepository;
    private final MyMapper mapper;
    private final AttachmentService attachmentService;

    //Aerodrome CRUD
    @Override
    public ResultMessage addAerodrome(AerodromeDTO aerodromeDTO) {
        Aerodrome aerodrome = mapper.aerodromeDtoToaerodrome(aerodromeDTO);
        checkAndSaveAerodrome(aerodrome, aerodromeDTO.getCityId());
        return new ResultMessage(true, SAVED);
    }

    @Override
    public ResultMessage editAerodrome(UUID id, AerodromeDTO aerodromeDTO) {
        Aerodrome aerodrome = aerodromeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aerodrome" + NOT_FOUND));
        mapper.updateAerodrome(aerodromeDTO, aerodrome);
        checkAndSaveAerodrome(aerodrome, aerodromeDTO.getCityId());
        return new ResultMessage(true, UPDATED);
    }

    @Override
    public ResultMessage showAerodromes(Integer page, Integer size) {
        Validation.checkPage(page, size, aerodromeRepository.count());
        List<Aerodrome> all = aerodromeRepository
                .findAll(Util.getPageable(page, size))
                .getContent();
        return new ResultMessage(true, all);
    }


    @Override
    public ResultMessage showAerodrome(UUID id) {
        Aerodrome aerodrome = aerodromeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aerodrome" + NOT_FOUND));
        return new ResultMessage(true, aerodrome);
    }

    @Override
    public ResultMessage deleteAerodrome(UUID id) {
        if (!aerodromeRepository.existsById(id)) {
            throw new NotFoundException("Aerodrome" + NOT_FOUND);
        }
        aerodromeRepository.deleteById(id);
        return new ResultMessage(true, DELETED);
    }

    @Override
    public ResultMessage addPlaneInAero(UUID id, UUID planeId) {
        Aerodrome aerodrome = aerodromeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aerodrome" + NOT_FOUND));
        Plane plane = planeRepository.findById(planeId)
                .orElseThrow(() -> new NotFoundException("Plane" + NOT_FOUND));
        Set<Plane> planes = aerodrome.getPlanes();
        planes.add(plane);
        aerodromeRepository.save(aerodrome);
        return new ResultMessage(true, SAVED);
    }

    @Override
    public ResultMessage addFile(MultipartFile multipartFile, UUID aeroId) {
        Aerodrome aerodrome = aerodromeRepository.findById(aeroId)
                .orElseThrow(() -> new NotFoundException("Aerodrome" + NOT_FOUND));
        if (aerodrome.getAttachment() != null) {
            throw new ConflictException("Image already exists");
        }
        ResultMessage resultMessage = attachmentService.saveFile(multipartFile, aeroId);
        if (resultMessage.getSuccess()) {
            aerodrome.setAttachment((Attachment) resultMessage.getObject());
            aerodromeRepository.save(aerodrome);
            return new ResultMessage(true, SAVED);
        } else return resultMessage;
    }

    @Override
    public ResultMessage deleteFile(UUID aeroId) {
        Aerodrome aerodrome = aerodromeRepository.findById(aeroId)
                .orElseThrow(() -> new NotFoundException("Aerodrome" + NOT_FOUND));
        if (aerodrome.getAttachment() == null) {
            throw new ConflictException("Image not found");
        }
        ResultMessage resultMessage = attachmentService
                .deleteFile(aerodrome.getAttachment().getId(), aeroId);
        if (resultMessage.getSuccess()) {
            aerodrome.setAttachment(null);
            aerodromeRepository.save(aerodrome);
        }
        return resultMessage;
    }

    @Override
    public ResultMessage updateFile(UUID aeroId, MultipartFile multipartFile) {
        Attachment attachment = aerodromeRepository.findById(aeroId)
                .orElseThrow(() -> new NotFoundException("Aerodrome" + NOT_FOUND))
                .getAttachment();
        if (attachment == null) {
            throw new ConflictException("Image not found");
        }
        return attachmentService.updateFile(attachment.getId(), multipartFile, aeroId);
    }

    private void checkAndSaveAerodrome(Aerodrome aerodrome, @NonNull Integer cityId) {
        aerodrome.setCity(cityRepository
                .findById(cityId)
                .orElseThrow(() -> new NotFoundException("City" + NOT_FOUND)));
        Aerodrome aero = aerodromeRepository.findByNameAndCity(
                aerodrome.getName(),
                aerodrome.getCity()).orElse(null);
        if (aero != null && !Objects.equals(aero.getId(), aerodrome.getId())) {
            throw new ConflictException("There is an aerodrome with this name and city");
        }
        aerodromeRepository.save(aerodrome);
    }

}
