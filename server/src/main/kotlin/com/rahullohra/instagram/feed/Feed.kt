package com.rahullohra.instagram.feed

import com.rahullohra.instagram.MAX_VARCHAR_LENGTH
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.UUID

object Posts : Table("posts") {
    val postId = uuid("id") // Unique identifier
    val userId = uuid("user_id").references(Users.id) // FK to Users table
    val contentType = enumerationByName("content_type", 50, ContentType::class) // ENUM (image, video, reel)
    val contentUrl = text("content_url") // CDN URL
    val caption = text("caption").nullable() // Caption
    val tags = text("tags").nullable() // JSON/Array of tags
    val createdAt = datetime("created_at") // Timestamp
    val visibility = enumerationByName("visibility", 10, Visibility::class) // ENUM (public, private)
    val isActive = bool("is_active").default(true) // Soft delete flag
}

object PostsTable : UUIDTable("posts") {
    val userId = reference("user_id", UsersTable) // FK to Users table
    val contentType = enumerationByName("content_type", 50, ContentType::class) // ENUM (image, video, reel)
    val contentUrl = text("content_url") // CDN URL
    val caption = text("caption").nullable() // Caption
    val tags = text("tags").nullable() // JSON/Array of tags
    val createdAt = datetime("created_at") // Timestamp
    val visibility = enumerationByName("visibility", 10, Visibility::class) // ENUM (public, private)
    val isActive = bool("is_active").default(true) // Soft delete flag
}

class Post(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Post>(PostsTable)

    var user by User referencedOn PostsTable.userId // Reference to the User who created the post
    var contentType by PostsTable.contentType // ENUM type of the post (image, video, reel)
    var contentUrl by PostsTable.contentUrl // URL of the post content
    var caption by PostsTable.caption // Caption for the post
    var tags by PostsTable.tags // Tags associated with the post
    var createdAt by PostsTable.createdAt // Timestamp of when the post was created
    var visibility by PostsTable.visibility // ENUM for visibility (public, private)
    var isActive by PostsTable.isActive // Soft delete flag
}


object Feeds : Table("feeds") {
    val feedId = uuid("id") // Unique identifier
    val userId = uuid("user_id").references(Users.id) // FK to Users table
    val postId = uuid("post_id").references(Posts.postId) // FK to Posts table
    val rankingScore = float("ranking_score").default(0.0f) // Ranking score
    val createdAt = datetime("created_at") // Timestamp
}
class Feed(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Feed>(FeedsTable)

    var user by User referencedOn FeedsTable.userId // Reference to the User
    var post by Post referencedOn FeedsTable.postId // Reference to the Post
    var rankingScore by FeedsTable.rankingScore // Ranking score for the feed
    var createdAt by FeedsTable.createdAt // Creation timestamp
}


object FeedsTable : UUIDTable("feeds") {
    val userId = reference("user_id", UsersTable) // FK to Users table
    val postId = reference("post_id", PostsTable) // FK to Posts table
    val rankingScore = float("ranking_score").default(0.0f) // Ranking score
    val createdAt = datetime("created_at") // Timestamp
}

class Like(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Like>(LikesTable)

    var user by User referencedOn LikesTable.userId // User who liked the post
    var post by Post referencedOn LikesTable.postId // Post that was liked
    var createdAt by LikesTable.createdAt // Creation timestamp
}


object LikesTable : UUIDTable("likes") {
    val userId = reference("user_id", UsersTable) // FK to Users table
    val postId = reference("post_id", PostsTable) // FK to Posts table
    val createdAt = datetime("created_at") // Timestamp
}



object Likes : Table("likes") {
    val likeId = uuid("id") // Unique identifier
    val userId = uuid("user_id").references(Users.id) // FK to Users table
    val postId = uuid("post_id").references(Posts.postId) // FK to Posts table
    val createdAt = datetime("created_at") // Timestamp
}

object CommentsTable : UUIDTable("comments") {
    val postId = reference("post_id", PostsTable) // FK to Posts table
    val userId = reference("user_id", UsersTable) // FK to Users table
    val commentText = text("comment_text") // Content of the comment
    val createdAt = datetime("created_at") // Timestamp
}

class Comment(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Comment>(CommentsTable)

    var post by Post referencedOn CommentsTable.postId // Post the comment is associated with
    var user by User referencedOn CommentsTable.userId // User who made the comment
    var commentText by CommentsTable.commentText // Content of the comment
    var createdAt by CommentsTable.createdAt // Creation timestamp
}


object Comments : Table("comments") {
    val commentId = uuid("id") // Unique identifier
    val postId = uuid("post_id").references(Posts.postId) // FK to Posts table
    val userId = uuid("user_id").references(Users.id) // FK to Users table
    val commentText = text("comment_text") // Content of the comment
    val createdAt = datetime("created_at") // Timestamp
}

/**
 * Followers
 */
object Followers : Table("followers") {
    val followerId = uuid("id") // Unique identifier
    val userId = uuid("user_id").references(Users.id) // User being followed
    val followerUserId = uuid("follower_user_id").references(Users.id) // User who is following
    val createdAt = datetime("created_at") // Timestamp
}
object FollowersTable : UUIDTable("followers") {
    val userId = reference("user_id", UsersTable) // User being followed
    val followerUserId = reference("follower_user_id", UsersTable) // User who is following
    val createdAt = datetime("created_at") // Timestamp
}
class Follower(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Follower>(FollowersTable)

    var user by User referencedOn FollowersTable.userId // The user being followed
    var follower by User referencedOn FollowersTable.followerUserId // The user who is following
    var createdAt by FollowersTable.createdAt
}


/**
 * Users
 */
object UsersTable : UUIDTable("users") {
    val username = varchar("username", 50).uniqueIndex() // Username
    val email = varchar("email", 255).uniqueIndex() // Email
    val createdAt = datetime("created_at") // Timestamp
}



class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(UsersTable)

    var username by UsersTable.username
    var email by UsersTable.email
    var createdAt by UsersTable.createdAt

    // Relationship: List of followers for this user
    val followers by Follower referrersOn FollowersTable.userId

    // Relationship: List of users this user is following
    val following by Follower referrersOn FollowersTable.followerUserId
}

object Users : Table("users") {
    val id = uuid("id") // Unique identifier
    val username = varchar("username", 50).uniqueIndex() // Username
    val email = varchar("email", 255).uniqueIndex() // Email
    val createdAt = datetime("created_at") // Timestamp
}

enum class ContentType {
    IMAGE, VIDEO, REEL
}

enum class Visibility {
    PUBLIC, PRIVATE
}



object Media: Table("media") {
    val id = integer("id").autoIncrement()
    val mimeType = varchar("mimetype", MAX_VARCHAR_LENGTH) //jpeg/ png/ mp4, ....
    val mediaType = varchar("mediaType", MAX_VARCHAR_LENGTH) //photo or video
    val url = varchar("url", MAX_VARCHAR_LENGTH)
    val postId = integer("postId")
}

class FeedDao {
    fun insert() {}
    fun get() {}
}

class MediaDao {
    fun insert() {}
    fun get() {}
}