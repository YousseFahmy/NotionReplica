package com.notionreplica.userapp.entities;

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
public class ChangeNameRequest {
    String firstName;
    String lastName;
}
