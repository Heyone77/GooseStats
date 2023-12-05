
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.utocka.HttpClient
import com.example.utocka.R
import com.example.utocka.Role
import com.example.utocka.fetchUserData
import com.example.utocka.parseJson
import com.example.utocka.sendRolesUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val appContext = getApplication<Application>().applicationContext

    private val _userData = MutableLiveData<List<Pair<Int, Int>>>()
    val userData: LiveData<List<Pair<Int, Int>>> = _userData


    fun handleImageClick(imageResId: Int) {
        val resourceName = appContext.resources.getResourceName(imageResId).substringAfterLast('/')
        sendRolesUpdate(HttpClient.client, "hey", resourceName)
    }

    private fun getImageResourceId(roleName: String): Int {
        val resId = appContext.resources.getIdentifier(
            roleName, "drawable", appContext.packageName
        )
        return if (resId != 0) resId else R.drawable.default_image
    }

    private fun processRoles(roles: List<Role>): List<Pair<Int, Int>> {
        return roles.map { role ->
            val imageResId = getImageResourceId(role.role)
            imageResId to role.count
        }
    }

    fun loadUserData(username: String) {
        viewModelScope.launch {
            try {
                val jsonString = withContext(Dispatchers.IO){fetchUserData(username)}
                val roles = parseJson(jsonString)
                _userData.value = processRoles(roles)
                Log.i("info", "Сервер")
            } catch (e: Exception) {
                Log.i("info",e.toString())
            }
        }
    }
}


