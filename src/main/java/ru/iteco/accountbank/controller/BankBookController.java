package ru.iteco.accountbank.controller;

import org.springframework.web.bind.annotation.*;
import ru.iteco.accountbank.model.BankBookDto;
import ru.iteco.accountbank.service.BankBookService;

import java.util.List;

@RestController
@RequestMapping("/bank-book")
public class BankBookController {

    private final BankBookService service;

    public BankBookController(BankBookService service) {
        this.service = service;
    }

    @GetMapping("/by-user-id/{userId}")
    public List<BankBookDto> getByUserId(@PathVariable Integer userId) {
        if (userId == null) {
            throw new RuntimeException("VALIDATION_ERROR");
        }
        return service.getByUserId(userId);
    }

    @GetMapping("/{bankBookId}")
    public BankBookDto getById(@PathVariable("bankBookId") Integer id) {
        if (id == null) {
            throw new RuntimeException("VALIDATION_ERROR");
        }
        return service.getById(id);
    }

    @PostMapping
    public BankBookDto create(@RequestBody BankBookDto bankBookDto) {
        return service.create(bankBookDto);
    }

    @PutMapping
    public BankBookDto update(@RequestBody BankBookDto bankBookDto) {
        return service.update(bankBookDto);
    }

    @DeleteMapping("/{bankBookId}")
    public void deleteById(@PathVariable("bankBookId") Integer id) {
        service.deleteById(id);
    }

    @DeleteMapping("by-user-id/{userId}")
    public void deleteByUserId(@PathVariable Integer userId) {
        service.deleteById(userId);
    }
}
