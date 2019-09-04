package com.kkaminsky.simplelogin.security

import org.springframework.stereotype.Service
import java.security.Signature
import javax.annotation.PostConstruct
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.util.*


@Service
class SignatureService {

    val signatureSign: Signature = Signature.getInstance("SHA256WithDSA")

    val signatureVerify: Signature =  Signature.getInstance("SHA256WithDSA")

    @PostConstruct
    fun init(){
        val secureRandom = SecureRandom()
        val keyPairGenerator = KeyPairGenerator.getInstance("DSA")
        val keyPair = keyPairGenerator.generateKeyPair()

        signatureSign.initSign(keyPair.private, secureRandom)

        signatureVerify.initVerify(keyPair.public)
    }

    fun getSign(text: String): String {

        signatureSign.update(Base64.getEncoder().encode(text.toByteArray()))

        return String(signatureSign.sign())

    }

    fun verify(text: String, sign: String): Boolean {

        signatureVerify.update(Base64.getEncoder().encode(text.toByteArray()))

        return signatureVerify.verify(sign.toByteArray())
    }
}