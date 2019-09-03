package com.kkaminsky.simplelogin.model

import javax.persistence.*

@Entity
@Table(name="user")
class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0

    @Column(name = "username")
    lateinit var username: String

    @Column(name = "password")
    lateinit var password: String

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id",nullable = false)
    lateinit var role: RoleEntity

}