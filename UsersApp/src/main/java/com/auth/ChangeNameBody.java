package com.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Builder
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ChangeNameBody {
    String firstName;
    String lastName;
}
