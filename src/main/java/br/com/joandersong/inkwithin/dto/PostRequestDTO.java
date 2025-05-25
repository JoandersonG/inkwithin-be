package br.com.joandersong.inkwithin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostRequestDTO {
    private String title;
    private String content;
}
