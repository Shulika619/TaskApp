package dev.shulika.telegrambot.model;

public enum State {
    NONE,
    CANCEL,
    START_OR_CANCEL_REG,
    COMMON_PASS,
    CHECK_COMMON_PASS,
    INPUT_FULL_NAME,
    CHECK_INPUT_FULL_NAME,
    SELECT_DEPARTMENT,
    CHECK_SELECT_DEPARTMENT,
    DEPARTMENT_PASS,
    CHECK_DEPARTMENT_PASS,
    SEND_MSG,
    ADD_DEPARTMENT
}