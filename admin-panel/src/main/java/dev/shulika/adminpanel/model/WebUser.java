package dev.shulika.adminpanel.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "web_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean isEnabled;
}