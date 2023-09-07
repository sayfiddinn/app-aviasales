package uz.pdp.appaviauz.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.appaviauz.entity.City;
import uz.pdp.appaviauz.entity.Country;
import uz.pdp.appaviauz.entity.Role;
import uz.pdp.appaviauz.entity.User;
import uz.pdp.appaviauz.entity.enums.CityName;
import uz.pdp.appaviauz.entity.enums.CountryName;
import uz.pdp.appaviauz.entity.enums.RoleName;
import uz.pdp.appaviauz.entity.enums.RoleTypeEnum;
import uz.pdp.appaviauz.feign.TelegramFeign;
import uz.pdp.appaviauz.repository.CityRepository;
import uz.pdp.appaviauz.repository.CountryRepository;
import uz.pdp.appaviauz.repository.RoleRepository;
import uz.pdp.appaviauz.repository.UserRepository;
import uz.pdp.appaviauz.util.Utils;

import java.util.HashSet;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    final RoleRepository roleRepository;
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final CountryRepository countryRepository;
    final CityRepository cityRepository;
    final TelegramFeign telegramFeign;
    @Value("${spring.sql.init.mode}")
    String mode;

    @Override
    public void run(String... args) {
        if (!Objects.equals(mode, "always"))
            return;
        Role superAdminRole = new Role();
        Role adminRole = new Role();
        Role moderatorRole = new Role();
        Role userRole = new Role();

        superAdminRole.setName(RoleName.SUPER_ADMIN.name());
        superAdminRole.setAuthorities(new HashSet<>(Utils.superAdminAuthority));
        superAdminRole.setRoleType(RoleTypeEnum.SUPER_ADMIN);

        adminRole.setName(RoleName.ADMIN.name());
        adminRole.setAuthorities(new HashSet<>(Utils.adminAuthority));
        adminRole.setRoleType(RoleTypeEnum.ADMIN);

        moderatorRole.setName(RoleName.MODERATOR.name());
        moderatorRole.setAuthorities(new HashSet<>(Utils.moderAuthority));
        moderatorRole.setRoleType(RoleTypeEnum.MODERATOR);

        userRole.setName(RoleName.USER.name());
        userRole.setAuthorities(new HashSet<>(Utils.userAuthority));
        userRole.setRoleType(RoleTypeEnum.USER);

        roleRepository.save(superAdminRole);
        roleRepository.save(adminRole);
        roleRepository.save(moderatorRole);
        roleRepository.save(userRole);

        User superAdmin = new User();
        superAdmin.setRole(superAdminRole);
        superAdmin.setEmail("SuperAdmin@gmail.com");
        superAdmin.setPassword(passwordEncoder.encode("root123"));
        superAdmin.setName("superAdmin");
        superAdmin.setPassportId("superAdmin");
        superAdmin.setPhoneNumber("+998990000000");
        superAdmin.setEnabled(true);
        userRepository.save(superAdmin);

        Country country1 = new Country();
        country1.setName(CountryName.UZBEKISTAN.name());

        Country country2 = new Country();
        country2.setName(CountryName.RUSSIA.name());

        Country country3 = new Country();
        country3.setName(CountryName.AMERICA.name());

        countryRepository.save(country1);
        countryRepository.save(country2);
        countryRepository.save(country3);

        City city1 = new City();
        city1.setName(CityName.TASHKENT.name());
        city1.setCountry(country1);

        City city2 = new City();
        city2.setName(CityName.MOSCOW.name());
        city2.setCountry(country2);

        City city3 = new City();
        city3.setName(CityName.NEW_YORK.name());
        city3.setCountry(country3);

        cityRepository.save(city1);
        cityRepository.save(city2);
        cityRepository.save(city3);
    }


}
