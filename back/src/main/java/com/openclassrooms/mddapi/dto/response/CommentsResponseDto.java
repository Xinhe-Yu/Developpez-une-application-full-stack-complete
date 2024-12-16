package com.openclassrooms.mddapi.dto.response;

import java.util.List;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.PaginationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsResponseDto {
  private List<CommentDto> data;
  private PaginationDto pagination;
}
