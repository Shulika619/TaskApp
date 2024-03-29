package dev.shulika.telegrambot.service;

import dev.shulika.telegrambot.model.AppUser;
import dev.shulika.telegrambot.model.Role;
import dev.shulika.telegrambot.model.State;
import dev.shulika.telegrambot.repository.AppUserRepository;
import dev.shulika.telegrambot.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.ws.rs.NotFoundException;

import static dev.shulika.telegrambot.BotConst.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AppUserService {
    private final MessageService messageService;
    private final AppUserRepository appUserRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeService employeeService;

    public void saveNewAppUser(Message message) {
        var chatId = message.getChatId();
        String firstName = message.getChat().getFirstName();
        messageService.sendMessage(message, String.format("%s, `%s`\\! \n\n%s", HELLO_MSG, firstName, FIRST_START_MSG));

        if (appUserRepository.findById(chatId).isEmpty()) {
            AppUser appUser = AppUser.builder()
                    .id(chatId)
                    .tgFirstName(firstName)
                    .state(State.NONE)
                    .role(Role.GUEST)
                    .build();
            appUserRepository.save(appUser);
            log.info("+++++ IN AppUserService :: saveNewAppUser :: ChatId - {}, FirstName - {} :: Saved", chatId, firstName);
        }
    }

    public void cancelBtn(Message message) {
        log.info("----- IN AppUserService :: cancelBtn :: ChatId - {}, FirstName - {} -----",
                message.getChatId(), message.getChat().getFirstName());
        messageService.sendEditMessage(message, MSG_CANCEL);
        changeState(message, State.CANCEL);
    }

    public void cancelCommand(Message message) {
        log.info("----- IN AppUserService :: cancelCommand :: ChatId - {}, FirstName - {} -----",
                message.getChatId(), message.getChat().getFirstName());
        messageService.sendMessage(message, MSG_CANCEL);
        changeState(message, State.CANCEL);
    }

    public AppUser findUserById(long chatId) {
        log.info("+++++ IN AppUserService :: findUserById :: CHECK :: ChatId - {}", chatId);
        return appUserRepository.findById(chatId).orElse(null);
    }

    public void changeState(Message message, State state) {
        var user = appUserRepository.findById(message.getChatId())
                .orElseThrow(() -> new NotFoundException("----- User Not found-----"));
        user.setState(state);
        log.info("+++++ IN AppUserService :: changeState :: ChatId - {}, FirstName - {}, Status - {} :: Saved",
                message.getChatId(), message.getChat().getFirstName(), state);
    }

    public void setFullName(Message message) {
        var user = appUserRepository.findById(message.getChatId())
                .orElseThrow(() -> new NotFoundException("----- User Not found-----"));
        var newFullName = message.getText();
        user.setFullName(newFullName);
        log.info("+++++ IN AppUserService :: setFullName :: ChatId - {}, FirstName - {}, FullName - {} :: Saved",
                message.getChatId(), message.getChat().getFirstName(), newFullName);
    }

    public void setDepartmentId(Message message, long idDepartment) {
        var user = appUserRepository.findById(message.getChatId())
                .orElseThrow(() -> new NotFoundException("----- User Not found-----"));
        user.setDepartmentId(idDepartment);
        log.info("+++++ IN AppUserService :: setDepartmentId :: ChatId - {}, FirstName - {}, DepartmentId - {} :: Saved",
                message.getChatId(), message.getChat().getFirstName(), idDepartment);
    }

    private void setRole(AppUser appUser, Role role) {
        log.info("+++++ IN AppUserService :: setRole :: ChatId - {}, Role - {}:: START +++++", appUser.getId(), role);
        appUser.setRole(role);
    }

    public boolean isDepartmentPassCorrect(Message message) {
        log.info("+++++ IN AppUserService :: isDepartmentPassCorrect :: START +++++");
        var user = appUserRepository.findById(message.getChatId())
                .orElseThrow(() -> new NotFoundException("----- User Not found-----"));
        var selectedDepartmentId = user.getDepartmentId();
        var department = departmentRepository.findById(selectedDepartmentId)
                .orElseThrow(() -> new NotFoundException("----- Department Not found-----"));
        setRole(user, Role.USER);
        return department.getPassword().equals(message.getText());
    }

    public void saveEmployee(Message message) {
        log.info("+++++ IN AppUserService :: saveEmployee :: ChatId - {}, FirstName - {} :: START",
                message.getChatId(), message.getChat().getFirstName());
        var user = appUserRepository.findById(message.getChatId())
                .orElseThrow(() -> new NotFoundException("----- User Not found-----"));
        employeeService.saveFromAppUser(user);
    }

    public void setSendTo(Message message, Long departmentId) {
        log.info("+++++ IN AppUserService :: setSendTo :: ChatId - {}, FirstName - {}, departmentId - {} ",
                message.getChatId(), message.getChat().getFirstName(), departmentId);
        var user = appUserRepository.findById(message.getChatId())
                .orElseThrow(() -> new NotFoundException("----- User Not found-----"));
        user.setSendTo(departmentId);
    }
}