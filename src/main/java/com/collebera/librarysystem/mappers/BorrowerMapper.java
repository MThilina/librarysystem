package com.collebera.librarysystem.mappers;

import com.collebera.librarysystem.dto.BorrowerDTO;
import com.collebera.librarysystem.model.Borrower;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BorrowerMapper {
    BorrowerMapper INSTANCE = Mappers.getMapper(BorrowerMapper.class);

    BorrowerDTO toDTO(Borrower borrower);

    Borrower toEntity(BorrowerDTO borrowerDTO);
}