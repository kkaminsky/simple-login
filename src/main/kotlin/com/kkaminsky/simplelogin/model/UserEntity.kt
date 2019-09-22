package com.kkaminsky.simplelogin.model

import javax.persistence.*

@Entity
@Table(name="user",schema = "public")
class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0

    @Column(name = "username")
    var username: String? = null

    @Column(name = "password")
    var password: String? = null

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id",nullable = false)
    var role: RoleEntity? = null

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    var grants: MutableList<GrantEntity> = mutableListOf()

}