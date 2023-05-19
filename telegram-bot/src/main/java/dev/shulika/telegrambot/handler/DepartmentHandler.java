package dev.shulika.telegrambot.handler;

import dev.shulika.telegrambot.model.State;
import dev.shulika.telegrambot.service.AppUserService;
import dev.shulika.telegrambot.service.DepartmentService;
import dev.shulika.telegrambot.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static dev.shulika.telegrambot.BotConst.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class DepartmentHandler {
    private final MessageService messageService;
    private final AppUserService appUserService;
    private final DepartmentService departmentService;

    public void addDepartmentStartStep1(Message message) {
        log.info("+++++ IN DepartmentHandler :: addDepartmentStartStep1 +++++");
        appUserService.changeState(message, State.ADD_DEPARTMENT);
        messageService.sendMessage(message, ADD_DEPARTMENT_MSG);
    }

    public void addDepartmentSaveStep2(Message message) {
        log.info("+++++ IN DepartmentHandler :: addDepartmentSaveStep2 +++++");
        if (departmentService.saveDepartment(message)){
            messageService.deleteMsg(message);
            messageService.sendMessage(message, ADD_DEPARTMENT_MSG_COMPLETE);
        } else{
            messageService.deleteMsg(message);
            messageService.sendMessage(message, ADD_DEPARTMENT_MSG_FAIL);
        }
        appUserService.changeState(message, State.NONE);
    }
}
