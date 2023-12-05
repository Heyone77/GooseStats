import android.app.Application
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
    private val _rolesData = MutableLiveData<List<Pair<Int, Int>>>()
    val rolesData: LiveData<List<Pair<Int, Int>>> = _rolesData


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

    fun getUserData(username: String) {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                fetchUserData(username)
            }
            _rolesData.postValue(processRoles(parseJson(data)))
        }
    }
}
