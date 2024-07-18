package me.mrsajal.flashcart.common.data

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import me.mrsajal.flashcart.common.data.local.UserPreferences
import me.mrsajal.flashcart.common.data.local.UserSettings

internal class AndroidUserPreferences(
    private val dataStore: DataStore<UserSettings>
) : UserPreferences {
    override suspend fun getUserData(): UserSettings {
        return dataStore.data.first()
    }

    override suspend fun setUserData(userSettings: UserSettings) {
        dataStore.updateData { userSettings }
    }
}