package uz.pdp.appaviauz.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appaviauz.entity.Aerodrome;
import uz.pdp.appaviauz.entity.Plane;
import uz.pdp.appaviauz.exception.ConflictException;
import uz.pdp.appaviauz.exception.NotFoundException;
import uz.pdp.appaviauz.mapper.MyMapper;
import uz.pdp.appaviauz.payload.PlaneDTO;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.repository.AerodromeRepository;
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
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository;
    private final AerodromeRepository aerodromeRepository;
    private final MyMapper mapper;

    @Override
    public ResultMessage addPlane(PlaneDTO planeDTO) {

        Plane plane = mapper.planeDtoToPlane(planeDTO);
        checkAndSave(plane, planeDTO.getAerodromeId());
        return new ResultMessage(true, SAVED);
    }


    @Override
    public ResultMessage deletePlane(UUID id) {
        Plane plane = planeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plane" + NOT_FOUND));
        List<Aerodrome> aerodromes = aerodromeRepository.findByPlanesById(plane.getId());
        for (Aerodrome aerodrome : aerodromes) {
            aerodrome.getPlanes().remove(plane);
        }
        aerodromeRepository.saveAll(aerodromes);
        planeRepository.delete(plane);
        return new ResultMessage(true, DELETED);
    }

    @Override
    public ResultMessage editePlane(PlaneDTO planeDTO, UUID id) {
        Plane plane = planeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plane" + NOT_FOUND));
        mapper.updatePlane(planeDTO, plane);
        checkAndSave(plane, planeDTO.getAerodromeId());
        return new ResultMessage(true, UPDATED);

    }

    @Override
    public ResultMessage showPlanesByAerodrome(UUID aeroId, Integer page, Integer size) {
        Aerodrome aerodrome = aerodromeRepository.findById(aeroId)
                .orElseThrow(() -> new NotFoundException("Aerodrome" + NOT_FOUND));
        Validation.checkPage(page, size, (long) aerodrome.getPlanes().size());
        return new ResultMessage(true, aerodrome.getPlanes());
    }

    @Override
    public ResultMessage showPlanes(Integer page, Integer size) {
        Validation.checkPage(page, size, planeRepository.count());
        List<Plane> all = planeRepository
                .findAll(Util.getPageable(page, size))
                .getContent();
        return new ResultMessage(true, all);
    }


    @Override
    public ResultMessage showPlane(UUID id) {
        Plane plane = planeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plane" + NOT_FOUND));
        return new ResultMessage(true, plane);
    }

    private void checkAndSave(Plane plane, @NonNull UUID aerodromeId) {
        Aerodrome aerodrome = aerodromeRepository.findById(aerodromeId)
                .orElseThrow(() -> new NotFoundException("Aerodrome" + NOT_FOUND));
        Plane demoPlane = planeRepository.findByPlaneCompanyAndPlaneNumber(
                        plane.getPlaneCompany(),
                        plane.getPlaneNumber())
                .orElse(null);
        if (demoPlane != null && !Objects.equals(demoPlane.getId(), plane.getId())) {
            throw new ConflictException("There is an plane with this number and company");
        }
        planeRepository.save(plane);
        Set<Plane> planes = aerodrome.getPlanes();
        planes.add(plane);
        aerodromeRepository.save(aerodrome);
    }

}
