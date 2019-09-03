package com.kkaminsky.simplelogin.model

import javax.persistence.*


@Entity
@Table(name="role")
class RoleEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0

    @Column(name = "role")
    lateinit var role: String
}