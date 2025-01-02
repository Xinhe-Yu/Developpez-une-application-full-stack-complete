package com.openclassrooms.mddapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.PaginationDto;

@Component
@Mapper(componentModel = "spring")
public interface PaginationMapper {
  @Mapping(target = "currentPage", source = "number")
  @Mapping(target = "totalPages", source = "totalPages")
  @Mapping(target = "pageSize", source = "size")
  @Mapping(target = "totalElements", source = "totalElements")
  PaginationDto toDto(Page<?> page);
}
