package pr0.ves.eliteboy.elitedangerous.companionapi

import com.google.gson.annotations.Expose
import se.simbio.encryption.Encryption
import java.util.*
import javax.crypto.KeyGenerator

class Credentials {
    var email: String? = null
    private var hole: ByteArray? = null
    private var pepper: String? = null
    private var ev = ByteArray(16).also { Random().nextBytes(it) }
    @Expose(serialize = false, deserialize = false)
    @Transient
    private var crypter: Encryption? = null
    private var encPwd = ""
    fun pwd(): String = crypter!!.decrypt(encPwd)
    var appId: String? = null
    var machineId: String? = null
    var machineToken: String? = null

    fun setPassword(pwd: String) {
        encPwd = crypter!!.encrypt(pwd).removeSuffix("\n")
    }

    fun initializeCrypter() {
        hole = KeyGenerator.getInstance("AES").generateKey().encoded
        pepper = Random().nextDouble().toString()
        ev = ByteArray(16).also { Random().nextBytes(it) }
        updateCrypter()
    }

    fun updateCrypter() {
        crypter = Encryption.getDefault(String(hole!!), pepper, ev)!!
    }
}