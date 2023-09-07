package uz.pdp.appaviauz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appaviauz.entity.PurchasedTicket;
import uz.pdp.appaviauz.entity.Role;
import uz.pdp.appaviauz.entity.User;
import uz.pdp.appaviauz.entity.Verify;
import uz.pdp.appaviauz.entity.enums.RoleTypeEnum;
import uz.pdp.appaviauz.exception.BadRequestException;
import uz.pdp.appaviauz.exception.ConflictException;
import uz.pdp.appaviauz.exception.NotFoundException;
import uz.pdp.appaviauz.mapper.MyMapper;
import uz.pdp.appaviauz.payload.EmailDetails;
import uz.pdp.appaviauz.payload.PurchasedTicketDTO;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.repository.*;
import uz.pdp.appaviauz.util.Util;
import uz.pdp.appaviauz.util.Utils;
import uz.pdp.appaviauz.util.Validation;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static uz.pdp.appaviauz.util.Path.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final PurchasedTicketRepo purchasedTicketRepo;
    private final EmailService emailService;
    private final MyMapper mapper;
    private final BaseService baseService;
    private final VerifyRepositary verifyRepositary;

    @Override
    public ResultMessage addModerator(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("This user" + NOT_FOUND));
        checkUser(user.isDeleted());
        if (Objects.equals(user.getRole().getRoleType(), RoleTypeEnum.MODERATOR)) {
            return new ResultMessage(false, "This user is already a moderator");
        }
        String link = LINK_CHECK_MODER + user.getId() + "&email=" + user.getEmail();
        verifyRepositary.save(new Verify(link));
        CompletableFuture.runAsync(() -> emailService.sendSimpleMail(new EmailDetails(
                user.getEmail(),
                "If you want to become a moderator,click the link below \n" + link,
                "This email generated automatically, please do not reply!"
        )));
        return ResultMessage.builder()
                .success(true)
                .object("This user has been recommended for moderation")
                .build();
    }

    private void checkUser(boolean deleted) {
        if (deleted) throw new NotFoundException("This userId not found");
    }

    @Override
    public ResultMessage deleteModerator(UUID id) {
        User user = userRepository.findByIdAndRole_RoleType(id, RoleTypeEnum.MODERATOR)
                .orElseThrow(() -> new NotFoundException("This moderator" + NOT_FOUND));
        checkUser(user.isDeleted());
        if (!Objects.equals(user.getRole().getRoleType(), RoleTypeEnum.MODERATOR)) {
            return new ResultMessage(false, "This user is not a moderator");
        }
        changeUserRole(user, RoleTypeEnum.USER);
        return new ResultMessage(true, DELETED);
    }

    @Override
    public ResultMessage showModerators(Integer page, Integer size) {
        Validation.checkPage(page, size, userRepository
                .countAllByRole_RoleType(RoleTypeEnum.MODERATOR));
        List<User> moderators = userRepository
                .findAllByRole_RoleType(RoleTypeEnum.MODERATOR,
                        Util.getPageable(page, size));
        return new ResultMessage(true, moderators);
    }

    @Override
    public ResultMessage showModerator(UUID id) {
        User user = userRepository.findByIdAndRole_RoleType(id, RoleTypeEnum.MODERATOR)
                .orElseThrow(() -> new NotFoundException("This moderator" + NOT_FOUND));
        return new ResultMessage(true, user);
    }

    @Override
    public ResultMessage checkModerator(UUID id, String email) {
        baseService.checkVerify(LINK_CHECK_MODER + id + "&email=" + email);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("This user" + NOT_FOUND));
        if (!Objects.equals(user.getEmail(), email))
            throw new BadRequestException("data error");
        changeUserRole(user, RoleTypeEnum.MODERATOR);
        return new ResultMessage(true, "Congratulations, you became a moderator");
    }

    @Override
    public ResultMessage showSoldTicketsByFlight(UUID flightId, Integer page, Integer size) {
        if (!flightScheduleRepository.existsById(flightId))
            throw new NotFoundException("Flight" + NOT_FOUND);

        Validation.checkPage(page, size, purchasedTicketRepo.countByFlightId(flightId));
        List<PurchasedTicketDTO> result = new ArrayList<>();
        makerTicketList(purchasedTicketRepo
                .findByFlightId(flightId, Util.getPageable(page, size)), result);
        return new ResultMessage(true, result);
    }

    @Override
    public ResultMessage showSoldTicketsByDate(Date time,
                                               Integer page, Integer size) {
        Validation.checkPage(page, size, purchasedTicketRepo.countByTime(time));
        List<PurchasedTicketDTO> result = new ArrayList<>();
        makerTicketList(purchasedTicketRepo
                .findByTime(time, page, size), result);
        return new ResultMessage(true, result);
    }

    @Override
    public ResultMessage showSoldTicketsByDateBetween(Date start, Date end,
                                                      Integer page, Integer size) {
        Validation.checkPage(page, size, purchasedTicketRepo.countByTimeBetween(start, end));
        List<PurchasedTicketDTO> result = new ArrayList<>();
        makerTicketList(purchasedTicketRepo
                .findByTimeBetween(start, end, page, size), result);
        return new ResultMessage(true, result);
    }

    @Override
    public ResultMessage showSoldTicketsByAeroAndDate(UUID aerodromeId, Date time,
                                                      Integer page, Integer size) {
        Validation.checkPage(page, size, purchasedTicketRepo.countByTimeAndAero(aerodromeId, time));
        List<PurchasedTicketDTO> result = new ArrayList<>();
        makerTicketList(purchasedTicketRepo
                .findByTimeAndAero(aerodromeId, time, page, size), result);
        return new ResultMessage(true, result);
    }

    @Override
    public ResultMessage showSoldTicketsByAeroAndDateBetween(UUID aerodromeId,
                                                             Date start, Date end,
                                                             Integer page, Integer size) {
        Validation.checkPage(page, size, purchasedTicketRepo
                .countByTimeBetweenAndAero(aerodromeId, start, end));
        List<PurchasedTicketDTO> result = new ArrayList<>();
        makerTicketList(purchasedTicketRepo
                .findByTimeBetweenAndAero(aerodromeId, start, end, page, size), result);
        return new ResultMessage(true, result);
    }

    @Override
    public ResultMessage showUsers(Integer page, Integer size) {
        if (baseService.getUser().getAuthorities().size()> Utils.adminAuthority.size()) {
            Validation.checkPage(page,size,userRepository.count());
            return ResultMessage.builder()
                    .success(true)
                    .object(userRepository.findAll(Util.getPageable(page,size)))
                    .build();
        }else {
            Validation.checkPage(page,size, userRepository.countAllByRoleTypeNon(RoleTypeEnum.SUPER_ADMIN.name()));
            List<User> all = userRepository.findAllByRoleTypeNon(RoleTypeEnum.SUPER_ADMIN.name(),
                    Util.getExtraPageable(page, size, "creation_timestamp", false));
            return new ResultMessage(true,all);
        }
    }

    @Override
    public ResultMessage showUser(UUID user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new NotFoundException("User" + NOT_FOUND));
        if (baseService.getUser().getAuthorities().size() <= Utils.adminAuthority.size()) {
            if (user.getAuthorities().size() > Utils.adminAuthority.size()) {
                throw new ConflictException(NOT_ALLOWED);
            }
        }
        return new ResultMessage(true,user);
    }


    private void changeUserRole(User user, RoleTypeEnum roleTypeEnum) {
        Role role = roleRepository.findByRoleType(roleTypeEnum)
                .orElseThrow(() -> new NotFoundException("This role not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    private void makerTicketList(List<PurchasedTicket> all, List<PurchasedTicketDTO> result) {
        for (PurchasedTicket ticket : all) {
            PurchasedTicketDTO ticketDTO = mapper.mappingPurTicketDTO(ticket);
            ticketDTO.setTicketId(ticket.getTicket().getId());
            ticketDTO.setUserId(ticket.getUser().getId());
            result.add(ticketDTO);
        }
    }


}
