package com.hmduc.foody.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import com.hmduc.foody.util.Constants.Companion.BACK_ONLINE
import com.hmduc.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.hmduc.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.hmduc.foody.util.Constants.Companion.PREFERENCE_DIET_TYPE
import com.hmduc.foody.util.Constants.Companion.PREFERENCE_DIET_TYPE_ID
import com.hmduc.foody.util.Constants.Companion.PREFERENCE_MEAL_TYPE
import com.hmduc.foody.util.Constants.Companion.PREFERENCE_MEAL_TYPE_ID
import com.hmduc.foody.util.Constants.Companion.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStroreRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private object Preferencekeys {
        val selectedMealType = stringPreferencesKey(PREFERENCE_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCE_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCE_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCE_DIET_TYPE_ID)
        val backOnline = booleanPreferencesKey(BACK_ONLINE)
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_NAME
    )

    suspend fun saveMealAndDiet(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        dataStore.edit {
            it[Preferencekeys.selectedMealType] = mealType
            it[Preferencekeys.selectedMealTypeId] = mealTypeId
            it[Preferencekeys.selectedDietType] = dietType
            it[Preferencekeys.selectedDietTypeId] = dietTypeId
        }

    suspend fun saveBackOnline(status: Boolean) =
        dataStore.edit {
            it[Preferencekeys.backOnline] = status
        }

    val readMealAndDiet: Flow<MealAndDietType> =  dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType = preferences[Preferencekeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[Preferencekeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[Preferencekeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[Preferencekeys.selectedDietTypeId] ?: 0
            MealAndDietType(selectedMealType,selectedMealTypeId,selectedDietType,selectedDietTypeId)
        }

    val readBackOnline:Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[Preferencekeys.backOnline] ?: false
            backOnline
        }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)