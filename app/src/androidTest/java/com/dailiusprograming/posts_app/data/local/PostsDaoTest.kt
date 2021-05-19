package com.dailiusprograming.posts_app.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.dailiusprograming.posts_app.data.model.*
import kotlinx.coroutines.test.runBlockingTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class PostsDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: PostsDatabase
    private lateinit var dao: PostsDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PostsDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.postsDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertPostDetails() = runBlockingTest {
        val postItem = PostDetails(1, 1, "test_1", "body_1")
        dao.insertPostDetails(postItem)
        val postDetail = dao.getPostDetails()

        assertThat(postDetail).isEqualTo(postItem)
    }

    @Test
    fun deleteAllPostDetails() = runBlockingTest {
        val postItem = PostDetails(1, 1, "test_1", "body_1")
        dao.insertPostDetails(postItem)
        dao.deleteAllPostDetails()
        val postDetail = dao.getPostDetails()

        assertThat(postDetail).isNull()
    }

    @Test
    fun insertUserDetails() = runBlockingTest {
        val company = Company(companyName = "Mickey&Co", companyCatchPhrase = "some_catch_phrase", companyBs = "some_bs")
        val address = Address(street = "some street", city = "MouseCity", suite = "some_suite", zipcode = "zip468")
        val userDetails = UserDetails(
            userId =  1, personFullName = "Mickey Mouse",  username = "Mouse",
            email = "mouse@mickey.com", phone = "4565 4645", website =  "http://www.mmouse.com",
            address = address, company = company )
        dao.insertUserDetails(userDetails)
        val getUserDetails = dao.getUserDetails(userId = 1)

        assertThat(getUserDetails).isEqualTo(userDetails)
    }

    @Test
    fun deleteUserDetails() = runBlockingTest {
        val company = Company(companyName = "Mickey&Co", companyCatchPhrase = "some_catch_phrase", companyBs = "some_bs")
        val address = Address(street = "some street", city = "MouseCity", suite = "some_suite", zipcode = "zip468")
        val userDetails = UserDetails(
            userId =  1, personFullName = "Mickey Mouse",  username = "Mouse",
            email = "mouse@mickey.com", phone = "4565 4645", website =  "http://www.mmouse.com",
            address = address, company = company )
        dao.insertUserDetails(userDetails)
        dao.deleteAllUserDetails()
        val getUserDetails = dao.getUserDetails(1)

        assertThat(getUserDetails).isNull()
    }

    @Test
    fun insertPosts() = runBlockingTest {
        val postItem1 = Posts(1, 1, "test_1", "body_first")
        val postItem2 = Posts(2, 2, "test_2", "body_second")
        val postItem3 = Posts(3, 3, "test_3", "body_third")
        val postItemList = mutableListOf(postItem1)
        postItemList.add(postItem2)
        postItemList.add(postItem3)
        dao.insertPosts(postItemList)
        val posts = dao.getAllPosts()

        assertThat(posts).isEqualTo(postItemList)
    }

    @Test
    fun postsAndUsers() = runBlockingTest {
        val company = Company(companyName = "Mickey&Co", companyCatchPhrase = "some_catch_phrase", companyBs = "some_bs")
        val address = Address(street = "some street", city = "MouseCity", suite = "some_suite", zipcode = "zip468")
        val userDetails = UserDetails(
            userId =  1, personFullName = "Mickey Mouse",  username = "Mouse",
            email = "mouse@mickey.com", phone = "4565 4645", website =  "http://www.mmouse.com",
            address = address, company = company )
        val postItem = PostDetails(1, 1, "test_1", "body_1")
        dao.insertPostDetails(postItem)
        dao.insertUserDetails(userDetails)

        val postsAndUsers = dao.getPostAndUser(postsId = 1)

        assertThat(postsAndUsers.postsDetails).isEqualTo(postItem)
        assertThat(postsAndUsers.user).isEqualTo(userDetails)
    }
}