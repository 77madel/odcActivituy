package com.odk.dto;

import com.odk.Entity.TypeActivite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeActiviteMapper {

    TypeActiviteDTO toDTO(TypeActivite typeActivite);

    TypeActivite toEntity(TypeActiviteDTO typeActiviteDTO);
}