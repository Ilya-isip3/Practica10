package com.example.myprapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myprapp.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = nextId++,
            author = "Студент БТПИТ $nextId++",
            content = "- Штирлиц, на вас поступил донос от соседей. Пишут, что вы вчера пили, буянили и ругались по-русски!\n" +
                    "Штирлиц молча берёт лист бумаги и пишет ответный донос:\n" +
                    "\"Группенфюреру СС Генриху Мюллеру. Мои соседи знают русский язык и, что особенно подозрительно, разбираются в ненормативной русской лексике!\".",
            published = "21 мая в 18:36",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            author = "Студент БТПИТ",
            content = "Штирлиц вёл двойную жизнь и очень надеялся, что хоть одна из них сложится удачно.",
            published = "18 сентября в 10:12",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            author = "Питерский Студент",
            content = "H2O девиз не наш! Наш C2H5OH!",
            published = "19 сентября в 10:24",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            author = "Мне хочется спать by SтYеNт",
            content = "Большая афиша мероприятий осени: Мероприятий не будет! Мероприятия под санкциями! Играйте в нарды или будите их делать",
            published = "19 сентября в 14:12",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            author = "Дональд Дак",
            content = "Диджитал давно стал частью нашей жизни: мы общаемся в социальных сетях и что дальше? типа где текст?",
            published = "20 сентября в 10:14",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            author = "Я очень устал by Студент",
            content = "Мем 1, Мем2, Мем-3, Мем - 4. Автор Студент",
            published = "21 сентября в 10:12",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            author = "Студент Курской Магнитной Аномалии",
            content = "Ходить на руках трудно... поэтому я предпочитаю ездить с помощью - РЕКЛАМА ЗАБЛОКИРОВАНА ПОЛЬЗОВАТЕЛЕМ - АДМИНИСТРАТОР",
            published = "22 сентября в 10:12",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            author = "Преподаватель по всем предметам",
            content = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \uD83D\uDE34\n",
            published = "22 сентября в 10:14",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            author = "Администратор",
            content = "Текст (от лат. textus — ткань; сплетение, сочетание) — зафиксированная на каком-либо материальном носителе человеческая мысль; в общем плане связная и полная последовательность символов.",
            published = "23 сентября в 10:12",
            likedByMe = false
        ),
    ).reversed()

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post) {
        if (post.id == 0L) {
            // TODO: remove hardcoded author & published
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }
}