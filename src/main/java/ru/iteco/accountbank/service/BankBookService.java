package ru.iteco.accountbank.service;

import ru.iteco.accountbank.model.BankBookDto;

import java.util.List;

public interface BankBookService {
    List<BankBookDto> getByUserId(Integer userId);
    BankBookDto getById(Integer id);
    BankBookDto create(BankBookDto bankBookDto);
    BankBookDto update(BankBookDto bankBookDto);
    void deleteById(Integer id);
    void deleteByUserId(Integer userId);
}
