package com.example.kotlin_coroutine_practice_26_05

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() , CoroutineScope{

    private lateinit var textUser: TextView
    private lateinit var textMovie: TextView
    private lateinit var job : Job
    private lateinit var handler : CoroutineExceptionHandler

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textUser = findViewById(R.id.textUser)
        textMovie = findViewById(R.id.textMovie)

        job = Job()

        //this is an activity scope not application scope , for application scope use GlobalScope.launch

        //Exception handling in Kotlin coroutines

    /*
        //try and catch method
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val user = async(Dispatchers.IO) { fetchUser() }
                val movie = async(Dispatchers.IO) { fetchMovie() }
                showResult(user.await(), movie.await())
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }

     */

        //exception handling using CoroutineExceptionHandler

        handler = CoroutineExceptionHandler{ _ , exception ->
            Log.e("tag exception" , "$exception handled")
        }

        GlobalScope.launch(Dispatchers.Main + handler) {
            val user = async(Dispatchers.IO) { fetchUser() }
            val movie = async(Dispatchers.IO) { fetchMovie() }
            showResult(user.await(), movie.await())
        }

    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    suspend fun fetchUser(): User {
        return withContext(Dispatchers.IO) {
            val user = User()
            user.apply {
                name = "Natu"
                age = 23
            }
            Log.e("tag fetchUser", "${user.name} & ${user.age}")
            user
        }
    }

    fun showResult(user: User, movie: Movie) {
        textUser.text = "username : ${user.name} , age : ${user.age}"
        textMovie.text = "Movie name : ${movie.name} , rating : ${movie.rating}"
    }

//    }

    suspend fun fetchMovie(): Movie {
        return withContext(Dispatchers.IO) {
            val movie = Movie()
            movie.apply {
                name = "John Wick"
                rating = 7.5F
            }
            Log.e("tag fetchMovie", "${movie.name} & ${movie.rating}")
            movie
        }
    }

}

        /*

        /*
            //try and catch method
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val user = async(Dispatchers.IO) { fetchUser() }
                    val movie = async(Dispatchers.IO) { fetchMovie() }
                    showResult(user.await(), movie.await())
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }

         */

            //exception handling using CoroutineExceptionHandler

            val handler = CoroutineExceptionHandler{ _ , exception ->
                Log.e("tag exception" , "$exception handled")
            }

            GlobalScope.launch(Dispatchers.Main + handler) {
                val user = async(Dispatchers.IO) { fetchUser() }
                val movie = async(Dispatchers.IO) { fetchMovie() }
                showResult(user.await(), movie.await())
            }



         */




        /*
            * if we need global scope which is our application scope, not an activity scope then use it like below
                GlobalScope.launch(Dispatchers.Main) {
                    val user = async(Dispatchers.IO) { fetchUser() }
                    val movie = async(Dispatchers.IO) { fetchMovie() }
                    showResult(user.await(), movie.await())
                }

                **notes** :- using Global scope which is our application scope
                               if we destroy the activity , then it continue running fetchUser() and fetchMovie()
                               functions as we use GlobalScope

         */



/*

        * async coroutines implementation
        * for parallel execution task

        private lateinit var textUser : TextView
        private lateinit var textMovie : TextView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            textUser = findViewById(R.id.textUser)
            textMovie = findViewById(R.id.textMovie)

    //        GlobalScope.launch {
    //            fetchUser()
    //            fetchMovie()
    //        }

            GlobalScope.launch(Dispatchers.Main) {
                val user = async(Dispatchers.IO) {fetchUser()}
                val movie = async(Dispatchers.IO) {fetchMovie()}
                showResult(user.await() , movie.await())
            }

        }

        suspend fun fetchUser() : User {
            return GlobalScope.async(Dispatchers.IO) {
                val user = User()
                user.apply {
                    name = "Natu"
                    age = 23
                }
                Log.e("tag fetchUser" , "${user.name} & ${user.age}")
                user
            }.await()
        }

        fun showResult(user : User , movie : Movie) {
            textUser.text = "username : ${user.name} , age : ${user.age}"
            textMovie.text = "Movie name : ${movie.name} , rating : ${movie.rating}"
        }


        suspend fun fetchMovie() : Movie {
            return GlobalScope.async(Dispatchers.IO) {
                val movie = Movie()
                movie.apply {
                    name = "John Wick"
                    rating = 7.5F
                }
                Log.e("tag fetchMovie" ,"${movie.name} & ${movie.rating}")
                movie
            }.await()
        }


 */


/*

        * withContext coroutines implementations
        * for non parallel execution task

        private lateinit var textUser: TextView
        private lateinit var textMovie: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            textUser = findViewById(R.id.textUser)
            textMovie = findViewById(R.id.textMovie)

    //        GlobalScope.launch {
    //            fetchUser()
    //            fetchMovie()
    //        }

            GlobalScope.launch(Dispatchers.Main) {
                val user = withContext(Dispatchers.IO) { fetchUser() }
                val movie = withContext(Dispatchers.IO) { fetchMovie() }
                showResult(user, movie)
            }

        }

        suspend fun fetchUser(): User {
            return withContext(Dispatchers.IO) {
                val user = User()
                user.apply {
                    name = "Natu"
                    age = 23
                }
                Log.e("tag fetchUser", "${user.name} & ${user.age}")
                user
            }
        }

        fun showResult(user: User, movie: Movie) {
            textUser.text = "username : ${user.name} , age : ${user.age}"
            textMovie.text = "Movie name : ${movie.name} , rating : ${movie.rating}"
        }


        suspend fun fetchMovie(): Movie {
            return withContext(Dispatchers.IO) {
                val movie = Movie()
                movie.apply {
                    name = "John Wick"
                    rating = 7.5F
                }
                Log.e("tag fetchMovie", "${movie.name} & ${movie.rating}")
                movie
            }
        }


 */

/*
        * scopes in kotlin coroutines

        class MainActivity : AppCompatActivity() , CoroutineScope{

        private lateinit var textUser: TextView
        private lateinit var textMovie: TextView
        private lateinit var job : Job

        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main + job

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            textUser = findViewById(R.id.textUser)
            textMovie = findViewById(R.id.textMovie)

            job = Job()

            //this is an activity scope not application scope , for application scope use GlobalScope.launch
            launch {
                val user = async(Dispatchers.IO) { fetchUser() }
                val movie = async(Dispatchers.IO) { fetchMovie() }
                showResult(user.await(), movie.await())
            }

            /*
                * if we need global scope which is our application scope, not an activity scope then use it like below
                    GlobalScope.launch(Dispatchers.Main) {
                        val user = async(Dispatchers.IO) { fetchUser() }
                        val movie = async(Dispatchers.IO) { fetchMovie() }
                        showResult(user.await(), movie.await())
                    }

                    **notes** :- using Global scope which is our application scope
                                   if we destroy the activity , then it continue running fetchUser() and fetchMovie()
                                   functions as we use GlobalScope

             */

        }

        override fun onDestroy() {
            job.cancel()
            super.onDestroy()
        }

        suspend fun fetchUser(): User {
            return withContext(Dispatchers.IO) {
                val user = User()
                user.apply {
                    name = "Natu"
                    age = 23
                }
                Log.e("tag fetchUser", "${user.name} & ${user.age}")
                user
            }
        }

        fun showResult(user: User, movie: Movie) {
            textUser.text = "username : ${user.name} , age : ${user.age}"
            textMovie.text = "Movie name : ${movie.name} , rating : ${movie.rating}"
        }

    //    }

        suspend fun fetchMovie(): Movie {
            return withContext(Dispatchers.IO) {
                val movie = Movie()
                movie.apply {
                    name = "John Wick"
                    rating = 7.5F
                }
                Log.e("tag fetchMovie", "${movie.name} & ${movie.rating}")
                movie
            }
        }



    }

 */