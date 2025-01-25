package com.rahullohra.instagram.sampledata

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.rahullohra.instagram.IgDatabase
import com.rahullohra.instagram.auth.Auth
import com.rahullohra.instagram.comment.Comment
import com.rahullohra.instagram.feed.Feed
import com.rahullohra.instagram.follower.Follower
import com.rahullohra.instagram.like.Like
import com.rahullohra.instagram.post.Post
import com.rahullohra.instagram.post.Visibility
import com.rahullohra.instagram.user.User
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class Csv {
    private val sampleDataDir = "sample_data"

    private fun getResource(fileName: String) = object {}.javaClass.getResource("/$sampleDataDir/$fileName")
        ?: throw IllegalArgumentException("Resource not found: /$sampleDataDir/$fileName")

    private fun readCsv(fileName: String) =
        csvReader().readAllWithHeader(getResource(fileName).openStream())

    fun insertUsers() {
        val usersCsv = readCsv("users.csv")
        usersCsv.forEach { row ->
            User.new(UUID.fromString(row["id"])) {
                username = row["username"]!!
                email = row["email"]!!
                createdAt = Instant.parse(row["created_at"]!!).toLocalDateTime(TimeZone.UTC)
            }
        }
    }

    fun insertAuth() {
        val authCsv = readCsv("Auth.csv")
        authCsv.forEach { row ->
            Auth.new(UUID.fromString(row["id"])) {
                user = User.findById(UUID.fromString(row["user_id"]))!!
                token = row["token"]!!
                refreshToken = row["refresh_token"]!!
                createdAt = Instant.parse(row["created_at"]!!).toLocalDateTime(TimeZone.UTC)
            }
        }
    }

    private fun insertPosts() {
        val postsCsv = readCsv("posts.csv")
        postsCsv.forEach { row ->
            Post.new(UUID.fromString(row["id"])) {
                user = User.findById(UUID.fromString(row["user_id"]))!!
                caption = row["caption"]
                tags = row["tags"]
                createdAt = Instant.parse(row["created_at"]!!).toLocalDateTime(TimeZone.UTC)
                visibility = Visibility.valueOf(row["visibility"]!!)
                isActive = row["is_active"]!!.toBoolean()
            }
        }
    }

    private fun insertFollowers() {
        val followersCsv = readCsv("followers.csv")
        followersCsv.forEach { row ->
            Follower.new(UUID.fromString(row["id"])) {
                user = User.findById(UUID.fromString(row["user_id"]))!!
                follower = User.findById(UUID.fromString(row["follower_user_id"]))!!
                createdAt = Instant.parse(row["created_at"]!!).toLocalDateTime(TimeZone.UTC)
            }
        }
    }

    private fun insertLikes() {
        val likesCsv = readCsv("likes.csv")
        likesCsv.forEach { row ->
            Like.new(UUID.fromString(row["id"])) {
                user = User.findById(UUID.fromString(row["user_id"]))!!
                post = Post.findById(UUID.fromString(row["post_id"]))!!
                createdAt = Instant.parse(row["created_at"]!!).toLocalDateTime(TimeZone.UTC)
            }
        }
    }

    private fun insertComments() {
        val commentsCsv = readCsv("comments.csv")
        commentsCsv.forEach { row ->
            Comment.new(UUID.fromString(row["id"])) {
                user = User.findById(UUID.fromString(row["user_id"]))!!
                post = Post.findById(UUID.fromString(row["post_id"]))!!
                commentText = row["comment_text"]!!
                createdAt = Instant.parse(row["created_at"]!!).toLocalDateTime(TimeZone.UTC)
            }
        }
    }

    private fun insertFeeds() {
        val feedsCsv = readCsv("feeds.csv")
        feedsCsv.forEach { row ->
            Feed.new(UUID.fromString(row["id"])) {
                user = User.findById(UUID.fromString(row["user_id"]))!!
                post = Post.findById(UUID.fromString(row["post_id"]))!!
                rankingScore = row["ranking_score"]!!.toFloat()
                createdAt = Instant.parse(row["created_at"]!!).toLocalDateTime(TimeZone.UTC)
            }
        }
    }

    fun populateDatabaseFromCsv() {
        transaction(IgDatabase.database) {
            insertUsers()
            insertAuth()
            insertFollowers()
            insertPosts()
            insertFeeds()
            insertLikes()
            insertComments()
        }

        println("Database successfully populated from CSV files.")
    }
}

