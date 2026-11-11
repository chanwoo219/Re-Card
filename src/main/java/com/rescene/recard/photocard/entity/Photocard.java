package com.rescene.recard.photocard.entity;

import com.rescene.recard.global.entity.BaseEntity;
import com.rescene.recard.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "photocards")
public class Photocard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PhotocardMember member;

    @Column(nullable = false, length = 100)
    private String album;

    @Column(length = 500)
    private String memo;

    protected Photocard() {
    }

    public Photocard(User owner, PhotocardMember member, String album, String memo) {
        this.owner = owner;
        this.member = member;
        this.album = album;
        this.memo = memo;
    }

    public void update(PhotocardMember member, String album, String memo) {
        this.member = member;
        this.album = album;
        this.memo = memo;
    }

    public void changeOwner(User owner) {
        this.owner = owner;
    }
}
