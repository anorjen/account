package ru.iteco.accountbank.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.iteco.accountbank.exception.BankBookException;
import ru.iteco.accountbank.exception.NotFoundException;
import ru.iteco.accountbank.model.BankBookDto;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BankBookServiceImpl implements BankBookService {

    private final Map<Integer, BankBookDto> bankBookDtoMap = new ConcurrentHashMap<>();
    private final AtomicInteger sequenceId = new AtomicInteger(1);

    @PostConstruct
    void init() {
        int id = sequenceId.getAndIncrement();
        bankBookDtoMap.put(id, BankBookDto.builder().id(id).amount(new BigDecimal(10000)).currency("RUB").number("bb1").userId(1).build());
    }

    @Override
    public List<BankBookDto> getByUserId(Integer userId) {
        List<BankBookDto> result = new ArrayList<>();

        for (BankBookDto b : bankBookDtoMap.values()) {
            if (b.getUserId().equals(userId)) {
                result.add(b);
            }
        }

        if (result.isEmpty()) {
            throw new NotFoundException();
        }
        return result;
    }

    @Override
    public BankBookDto getById(Integer id) {
        BankBookDto result = bankBookDtoMap.get(id);
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }

    @Override
    public BankBookDto create(BankBookDto bankBookDto) {
        try {
            List<BankBookDto> bankBookDtoList = getByUserId(bankBookDto.getUserId());
            for (BankBookDto b : bankBookDtoList) {
                if (b.getNumber().equals(bankBookDto.getNumber())
                        && b.getCurrency().equals(bankBookDto.getCurrency())) {
                    throw new BankBookException("Счет с данной валютой уже имеется в хранилище: " + b.getId());
                }
            }
        } catch (NotFoundException ex) {
            log.info("NotFoundException: {}" + ex.getMessage());
        }

        int id = sequenceId.getAndIncrement();
        bankBookDto.setId(id);
        bankBookDtoMap.put(id, bankBookDto);
        return bankBookDto;
    }

    @Override
    public BankBookDto update(BankBookDto bankBookDto) {
        BankBookDto bankBookDtoFromMap = bankBookDtoMap.get(bankBookDto.getId());
        if (bankBookDtoFromMap == null) {
            throw new NotFoundException();
        } else {
            if (!bankBookDtoFromMap.getNumber().equals(bankBookDto.getNumber())) {
                throw new BankBookException("Номер менять нельзя");
            }
            bankBookDtoFromMap.setUserId(bankBookDto.getUserId());
            bankBookDtoFromMap.setAmount(bankBookDto.getAmount());
            bankBookDtoFromMap.setCurrency(bankBookDto.getCurrency());
            bankBookDtoMap.put(bankBookDtoFromMap.getId(), bankBookDtoFromMap);
            return bankBookDtoFromMap;
        }
    }

    @Override
    public void deleteById(Integer id) {
        bankBookDtoMap.remove(id);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        List<Integer> removeIds = bankBookDtoMap.values().stream()
                                    .filter(i -> Objects.equals(i.getUserId(), userId))
                                    .map(BankBookDto::getId)
                                    .collect(Collectors.toList());

        for (Integer b : removeIds) {
            bankBookDtoMap.remove(b);
        }
    }
}
