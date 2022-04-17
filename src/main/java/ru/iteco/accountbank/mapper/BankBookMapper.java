package ru.iteco.accountbank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.iteco.accountbank.model.BankBookDto;
import ru.iteco.accountbank.model.entity.BankBookEntity;

@Mapper(componentModel = "spring")
public interface BankBookMapper {

    @Mapping(target = "currency", source = "currency.name")
    BankBookDto mapToDto(BankBookEntity bankBookEntity);

    @Mapping(target = "currency.name", source = "currency")
    BankBookEntity mapToEntity(BankBookDto bankBookDto);
}
