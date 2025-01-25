package com.rahullohra.instagram

import com.rahullohra.instagram.auth.AuthTable
import com.rahullohra.instagram.comment.CommentsTable
import com.rahullohra.instagram.feed.FeedsTable
import com.rahullohra.instagram.follower.FollowersTable
import com.rahullohra.instagram.like.LikesTable
import com.rahullohra.instagram.media.Media
import com.rahullohra.instagram.media.MediaTable
import com.rahullohra.instagram.post.Post
import com.rahullohra.instagram.post.PostsTable
import com.rahullohra.instagram.sampledata.Csv
import com.rahullohra.instagram.sampledata.MediaDownloader
import com.rahullohra.instagram.user.UsersTable
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

/**
 * Tutorial: https://jetbrains.github.io/Exposed/getting-started-with-exposed.html#run-in-intellij-idea
 */

object IgDatabase {
    val database: Database
    val h2InMemoryUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
    val h2DiskUrl = "jdbc:h2:~/IdeaProjects/InstagramClone/db/h2_db"

    fun getDbUrl() = h2DiskUrl

    init {
        /**
         * DB_CLOSE_DELAY=-1 is to keep the connection open for H2 database otherwise the second transaction
         * won't be able to find the changes in first transaction
         */

        database = Database.connect(getDbUrl(), user = "sa", driver = "org.h2.Driver")
    }

    fun setup() {
        transaction(database) {
            addLogger(StdOutSqlLogger)
            createTables()
            insertSampleData()
            runBlocking {
                MediaDownloader().downloadImages()
            }
            populateMediaTableWithPosts()
        }
    }

    private fun populateMediaTableWithPosts() {
        val postIds = Post.all().map { it.id.value }.toList()

        // Fetch media items in batches of 10
        val batchSize = 10
        val mediaItems = Media.all().toList()
        val totalMedia = mediaItems.size

        // Assign Post IDs to Media in batches
        var postIndex = 0
        for (i in mediaItems.indices step batchSize) {
            val batch = mediaItems.subList(i, (i + batchSize).coerceAtMost(totalMedia))

            // Get the current Post ID
            val currentPostId = postIds[postIndex]
            batch.forEach { media ->
                media.post = Post.findById(currentPostId)
            }

            // Move to the next post ID, loop back if all post IDs are used
            postIndex = (postIndex + 1) % postIds.size
        }

    }

    fun insertSampleData() {
        Csv().populateDatabaseFromCsv()
    }

    fun createTables() {
        try {
            SchemaUtils.dropDatabase(getDbUrl())
        }catch (ex:Exception){
            //Ignore
        }

        SchemaUtils.create(UsersTable)
        SchemaUtils.create(AuthTable)
        SchemaUtils.create(FollowersTable)
        SchemaUtils.create(PostsTable)
        SchemaUtils.create(FeedsTable)
        SchemaUtils.create(LikesTable)
        SchemaUtils.create(CommentsTable)
        SchemaUtils.create(MediaTable)
    }

    fun insertTasks() {

        val taskId = Tasks.insert {
            it[title] = "Learn Exposed"
            it[description] = "Go through the Get started with Exposed tutorial"
        } get Tasks.id

        val secondTaskId = Tasks.insert {
            it[title] = "Read The Hobbit"
            it[description] = "Read the first two chapters of The Hobbit"
            it[isCompleted] = true
        } get Tasks.id

        println("Created new tasks with ids $taskId and $secondTaskId.")

        Tasks.select(Tasks.id.count(), Tasks.isCompleted).groupBy(Tasks.isCompleted).forEach {
            println("${it[Tasks.isCompleted]}: ${it[Tasks.id.count()]} ")
        }

        // Update a task
        Tasks.update({ Tasks.id eq taskId }) {
            it[isCompleted] = true
        }

        val updatedTask = Tasks.select(Tasks.isCompleted).where(Tasks.id eq taskId).single()

        println("Updated task details: $updatedTask")

        // Delete a task
        Tasks.deleteWhere { id eq secondTaskId }

        println("Remaining tasks: ${Tasks.selectAll().toList()}")
    }
}