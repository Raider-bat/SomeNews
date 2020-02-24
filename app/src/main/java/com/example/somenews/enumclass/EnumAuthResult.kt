package com.example.somenews.enumclass

enum class EnumAuthResult(val string: String) {
    ADMIN_SIGN_UP("Вход"),
    USER_SIGN_UP("Вход"),
    WRONG_DATA("Неверно ведённые данные"),
    WRONG_PASSWORD("Неверный пароль"),
    ACCOUNT_NOT_FOUND("Подобного аккаунта не существует"),
    ACCOUNT_CREATE_SUCCESSFUL("Успешно"),
    ACCOUNT_CREATE_EXCEPTION("Ошибка регистрации"),
    ACCOUNT_CREATE_ALREADY_EXIST("Подобный аккаунт уже существует");
}