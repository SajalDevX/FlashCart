package me.mrsajal.flashcart.common.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import me.mrsajal.flashcart.common.data.local.UserPreferences
import me.mrsajal.flashcart.common.data.local.UserSettings

internal class IOSUserPreferences (
    private val dataStore: DataStore<Preferences>
): UserPreferences {
    override suspend fun getUserData(): UserSettings {
        TODO("Not yet implemented")
    }

    override suspend fun setUserData(userSettings: UserSettings) {
        TODO("Not yet implemented")
    }

}


internal fun createDatastore(): DataStore<Preferences>? {
   return null
}