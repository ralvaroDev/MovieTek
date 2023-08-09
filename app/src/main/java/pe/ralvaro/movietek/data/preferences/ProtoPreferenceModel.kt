package pe.ralvaro.movietek.data.preferences

import androidx.datastore.core.Serializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import pe.ralvaro.movietek.ui.InitialView
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream

/**
 * This class is the stored model for the preference data storage
 * @param initialView is the initial activity of the app
 */
@Serializable
data class ProtoPreferenceModel(
    val initialView: InitialView = InitialView.Login,
    val isPagingFixed: Boolean = false
)

object ProtoStoreSerializer : Serializer<ProtoPreferenceModel> {
    override val defaultValue: ProtoPreferenceModel
        get() = ProtoPreferenceModel()

    override suspend fun readFrom(input: InputStream): ProtoPreferenceModel {
        return try {
            Json.decodeFromString(
                deserializer = ProtoPreferenceModel.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            Timber.e(e)
            defaultValue
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: ProtoPreferenceModel, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = ProtoPreferenceModel.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

}
