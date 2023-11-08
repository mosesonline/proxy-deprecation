package de.mosesonline.proxydeprecation;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import java.util.List;
import java.util.UUID;

@Entity(name = "contacts")
@Table(name = "contacts")
@Getter
@Setter
public class ContactEntity {
    @Id
    private UUID id;
    @Column(nullable = false)
    private UUID partnerId;
    private String name;
    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<ContactChannelEntity> channels;

    @Entity
    @Table(name = "contact_channels")
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    @DiscriminatorColumn(name = "channel_type",
            discriminatorType = DiscriminatorType.STRING)
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Proxy(lazy = false)
    static sealed abstract class ContactChannelEntity permits ContactChannelEntity.PhoneNumber, ContactChannelEntity.Email, ContactChannelEntity.Other {
        @Id
        private UUID id;

        @Entity
        @DiscriminatorValue("phone")
        @NoArgsConstructor
        @Getter
        @Setter
        static final class PhoneNumber extends ContactChannelEntity {
            @Size(max = 64)
            private String phoneNumber;
            @Enumerated(EnumType.STRING)
            private PhoneType phoneType;

            PhoneNumber(UUID id, String phoneNumber, PhoneType phoneType) {
                super(id);
                this.phoneNumber = phoneNumber;
                this.phoneType = phoneType;
            }
        }

        @Entity
        @DiscriminatorValue("mail")
        @NoArgsConstructor
        @Getter
        @Setter
        static final class Email extends ContactChannelEntity {
            @Size(max = 320)
            private String email;
            @Enumerated(EnumType.STRING)
            private EMailType eMailType;

            Email(UUID id, String email, EMailType eMailType) {
                super(id);
                this.email = email;
                this.eMailType = eMailType;
            }
        }

        @Entity
        @DiscriminatorValue("other")
        @NoArgsConstructor
        @Getter
        @Setter
        static final class Other extends ContactChannelEntity {
            @Size(max = 512)
            private String contactInfo;

            Other(UUID id, String contactInfo) {
                super(id);
                this.contactInfo = contactInfo;
            }
        }
    }
}
