package com.kkaminsky.simplelogin.model

import javax.persistence.*


@Entity
@Table(name="grant",schema = "public")
class GrantEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    var user: UserEntity? = null

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "file_id",nullable = false)
    var file: FileEntity? = null

    @Column(name="type")
    var type: String? = null
}