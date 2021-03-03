package com.shirinansaripractical.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shirinansaripractical.R
import com.shirinansaripractical.database.AppDatabase
import com.shirinansaripractical.database.dao.UserDao
import com.shirinansaripractical.database.dao.UserLocationDao
import com.shirinansaripractical.database.dao.UserNameDao
import com.shirinansaripractical.database.dao.UserPictureDao
import com.shirinansaripractical.database.entity.UserEntity
import com.shirinansaripractical.database.entity.UserLocationEntity
import com.shirinansaripractical.database.entity.UserNameEntity
import com.shirinansaripractical.database.entity.UserPictureEntity
import com.shirinansaripractical.databinding.FragmentUserBinding
import com.shirinansaripractical.model.UserResponse
import com.shirinansaripractical.util.AndroidLog
import com.shirinansaripractical.util.CommonUtil
import com.shirinansaripractical.util.NetworkUtil
import com.shirinansaripractical.view.adapter.UserAdapter
import com.shirinansaripractical.view.fragment.base.BaseFragment
import com.shirinansaripractical.viewmodel.UserViewModel

class UserFragment : BaseFragment<FragmentUserBinding>() {

    private var userList = ArrayList<UserResponse.UserItem?>()
    private var userAdapter: UserAdapter? = null

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var userNameDao: UserNameDao? = null
    private var userLocationDao: UserLocationDao? = null
    private var userPictureDao: UserPictureDao? = null

    override fun getLayoutResId(): Int {
        return R.layout.fragment_user
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getInstance(requireActivity())
        userDao = db?.userDao
        userNameDao = db?.userNameDao
        userLocationDao = db?.userLocationDao
        userPictureDao = db?.userPictureDao

        dataBinding!!.userViewModel = UserViewModel()

        getDataFromDB()
        if (NetworkUtil.isNetworkConnected(requireActivity())) {
            if (userList.size == 0) {
                userList.add(null)
                setAdapter()
            }
            dataBinding!!.userViewModel!!.getUsers(requireActivity())
        }

        dataBinding!!.userViewModel!!.showMsg.observe(
            viewLifecycleOwner,
            Observer { msg: String? ->
                if (msg != null) {
                    CommonUtil.showSnackBar(dataBinding!!.clUser, msg)
                }
            })
        dataBinding!!.userViewModel!!.userResponse.observe(viewLifecycleOwner, Observer {
            AndroidLog.e("UserResponse", "" + it)

            userList.clear()
            userList.addAll(it.results)
            setAdapter()
            addDataInDB()
        })
    }

    private fun getDataFromDB() {//Getting data from database
        val userEntityList = userDao!!.getAll()
        val userNameEntityList = userNameDao!!.getAll()
        val userLocationEntityList = userLocationDao!!.getAll()
        val userPictureEntityList = userPictureDao!!.getAll()
        userEntityList.forEach {
            val uuid = it.id

            //name
            val tempUserName = userNameEntityList.find { it.userId == uuid }
            val userName = UserResponse.UserName(
                title = tempUserName!!.title,
                first = tempUserName.first,
                last = tempUserName.last
            )

            //location
            val tempLocationName = userLocationEntityList.find { it.userId == uuid }
            val userCoordinates = UserResponse.UserCoordinates(
                latitude = tempLocationName!!.latitude,
                longitude = tempLocationName.longitude
            )
            val userLocation = UserResponse.UserLocation(
                city = tempLocationName.city,
                state = tempLocationName.state,
                country = tempLocationName.country,
                coordinates = userCoordinates
            )

            //picture
            val tempPictureName = userPictureEntityList.find { it.userId == uuid }
            val userPicture = UserResponse.UserPicture(
                medium = tempPictureName!!.medium,
                thumbnail = tempPictureName.thumbnail
            )

            //Login
            val userLogin = UserResponse.UserLogin(uuid = uuid)

            val userItem =
                UserResponse.UserItem(
                    gender = it.gender,
                    name = userName,
                    location = userLocation,
                    email = it.email,
                    phone = it.phone,
                    login = userLogin,
                    picture = userPicture
                )
            userList.add(userItem)
        }

        setAdapter()
    }

    private fun addDataInDB() {//Saving data into database

        userDao!!.deleteAll()
        userNameDao!!.deleteAll()
        userLocationDao!!.deleteAll()
        userPictureDao!!.deleteAll()

        userList.forEachIndexed { _, userItem ->
            if (userItem != null) {
                val userEntity = UserEntity(
                    id = userItem.login!!.uuid!!,
                    gender = userItem.gender,
                    email = userItem.email,
                    phone = userItem.phone
                )
                userDao!!.insertAll(userEntity)

                //name
                if (userItem.name != null) {
                    val userNameEntity = UserNameEntity(
                        userItem.login!!.uuid!!,
                        userId = userItem.login!!.uuid!!,
                        title = userItem.name!!.title,
                        first = userItem.name!!.first,
                        last = userItem.name!!.last
                    )
                    userNameDao!!.insertAll(userNameEntity)
                }

                //location
                if (userItem.location != null) {
                    val userLocationEntity = UserLocationEntity(
                        userItem.login!!.uuid!!,
                        userId = userItem.login!!.uuid!!,
                        city = userItem.location!!.city,
                        state = userItem.location!!.state,
                        country = userItem.location!!.country,
                        latitude = userItem.location!!.coordinates!!.latitude,
                        longitude = userItem.location!!.coordinates!!.longitude
                    )
                    userLocationDao!!.insertAll(userLocationEntity)
                }

                //picture
                if (userItem.picture != null) {
                    val userPictureEntity = UserPictureEntity(
                        userItem.login!!.uuid!!,
                        userId = userItem.login!!.uuid!!,
                        medium = userItem.picture!!.medium,
                        thumbnail = userItem.picture!!.thumbnail
                    )
                    userPictureDao!!.insertAll(userPictureEntity)
                }
            }
        }
    }

    private fun setAdapter() {
        userAdapter = UserAdapter(userList)
        dataBinding!!.rvUser.layoutManager = LinearLayoutManager(requireActivity())
        dataBinding!!.rvUser.adapter = userAdapter
    }
}