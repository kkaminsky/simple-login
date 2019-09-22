package com.kkaminsky.simplelogin.model

import javax.persistence.*


@Entity
@Table(name="file",schema = "public")
class FileEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0

    @Column(name = "file_name")
    var fileName: String? = null

    @Column(name = "text")
    var text: String? = null

    @OneToMany(mappedBy = "file", cascade = [CascadeType.REMOVE])
    var grants: MutableList<GrantEntity> = mutableListOf()

}